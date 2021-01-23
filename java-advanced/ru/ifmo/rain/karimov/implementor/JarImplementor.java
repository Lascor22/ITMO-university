package ru.ifmo.rain.karimov.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class JarImplementor implements JarImpler {
    /**
     * Suffix for filename implementation
     */
    private static final String IMPL = "Impl";
    /**
     * Filename extension for compiled java files
     */
    private final static String CLASS = ".class";
    /**
     * Filename extension for source java files
     */
    private final static String JAVA = ".java";
    /**
     * Filename suffix extension for source java files
     */
    private static final String SOURCE_CODE_SUFFIX = IMPL + JAVA;
    /**
     * Static instance of {@link Cleaner} used inside {@link #clean(Path)}
     */
    private final static Cleaner DELETER = new Cleaner();

    /**
     * Validate token
     * <p>
     * Validate token for {@link Class#isPrimitive()}, equals on {@link Enum#getClass()},
     * {@link Modifier#isPrivate} or {@link Modifier#isFinal}
     *
     * @param token token which validate
     * @throws ImplerException if not validated
     */
    private void validateToken(Class<?> token) throws ImplerException {
        int modifiers = token.getModifiers();
        if (token.isPrimitive() || token.isArray() || token == Enum.class || Modifier.isFinal(modifiers)
                || Modifier.isPrivate(modifiers)) {
            throw new ImplerException("Unsupported token given");
        }
    }

    /**
     * Prepare path and create directories
     *
     * @param token type token for create package
     * @param root  path of root directory
     * @throws ImplerException if error occurred during creation or invalid path
     */
    private Path createPath(Class<?> token, Path root) throws ImplerException {
        Path path;
        try {
            Path sourcePath = getFilePath(Path.of(""), token);
            path = Path.of(root.toString(), sourcePath.toString());

        } catch (InvalidPathException e) {
            throw new ImplerException("Invalid path", e);
        }

        createDirectories(path);
        return path;
    }

    /**
     * Creates parent directory for file represented by <tt>file</tt>
     *
     * @param path file to create parent directory
     * @throws ImplerException if error occurred during creation
     */
    private static void createDirectories(Path path) throws ImplerException {
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new ImplerException("Unable to create directories for output file", e);
            }
        }
    }

    /**
     * Return path to file, containing implementation of given class, with specific file extension
     * located in directory represented by <tt>path</tt>
     *
     * @param path  path to parent directory of class
     * @param token class to get name from
     * @return {@link Path} representing path to certain file
     */
    private static Path getFilePath(Path path, Class<?> token) {
        return path.resolve(token.getPackageName()
                .replace('.', File.separatorChar))
                .resolve(token.getSimpleName() + SOURCE_CODE_SUFFIX);
    }

    /**
     * Recursively deletes directory represented by <tt>path</tt>
     *
     * @param path directory to be recursively deleted
     * @throws IOException if error occurred during deleting
     */
    private static void clean(Path path) throws IOException {
        Files.walkFileTree(path, DELETER);
    }

    /**
     * Unicode encoder for resulting <code>.java</code> file.
     * Escapes all unicode characters in <code>\\u</code> notation.
     *
     * @param arg a {@link String} instance to be encoded
     * @return a {@link String} representing unicode-escaped {@code arg}
     */
    private static String encode(String arg) {
        StringBuilder builder = new StringBuilder();
        for (char c : arg.toCharArray()) {
            builder.append(c < 128 ? String.valueOf(c) : String.format("\\u%04x", (int) c));
        }
        return builder.toString();
    }

    /**
     * @throws ImplerException if the given class cannot be generated for one of such reasons:
     *                         <ul>
     *                         <li> Some arguments are <tt>null</tt></li>
     *                         <li> Given <tt>class</tt> is primitive or array. </li>
     *                         <li> Given <tt>class</tt> is final class or {@link Enum}. </li>
     *                         <li> <tt>class</tt> isn't an interface and contains only private constructors. </li>
     *                         <li> The problems with I/O occurred during implementation. </li>
     *                         </ul>
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null || root == null) {
            throw new ImplerException("Arguments must not be null");
        }
        validateToken(token);

        Path sourcePath = createPath(token, root);
        try (BufferedWriter writer = Files.newBufferedWriter(sourcePath)) {
            writer.write(encode(CodeGenerator.generate(token)));
        } catch (IOException e) {
            throw new ImplerException("I/O error occurred", e);
        }
    }

    /**
     * Produces <tt>.jar</tt> file implementing class or interface specified by provided <tt>token</tt>.
     * <p>
     * Generated class full name should be same as full name of the type token with <tt>Impl</tt> suffix
     * added.
     * <p>
     * During implementation creates temporary folder to store temporary <tt>.java</tt> and <tt>.class</tt> files.
     * If program fails to delete temporary folder, it informs user about it.
     *
     * @throws ImplerException if the given class cannot be generated for one of such reasons:
     *                         <ul>
     *                         <li> Some arguments are <tt>null</tt></li>
     *                         <li> Error occurs during implementation via {@link #implement(Class, Path)} </li>
     *                         <li> {@link JavaCompiler} failed to compile implemented class </li>
     *                         <li> The problems with I/O occurred during implementation. </li>
     *                         </ul>
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        if (token == null || jarFile == null) {
            throw new ImplerException("Invalid (null) argument given");
        }
        createDirectories(jarFile.toAbsolutePath());
        Path tempDir;
        try {
            tempDir = Files.createTempDirectory(jarFile.toAbsolutePath().getParent(), "tmp");
        } catch (IOException e) {
            throw new ImplerException(String.format("Unable to create temporary directory: %s", e.getMessage()));
        }
        try {
            implement(token, tempDir);
            String superPath;
            try {
                superPath = Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
            } catch (URISyntaxException e) {
                throw new ImplerException("Failed to generate valid classpath", e);
            }
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new ImplerException("No java compiler found in the system.");
            }
            String className = token.getSimpleName() + IMPL;
            Path pathToPackage = token.getPackage() == null ? tempDir :
                    tempDir.resolve(token.getPackage().getName().replace(".", File.separator));
            String[] args = {
                    "--patch-module",
                    token.getModule().getName() + "=" + tempDir.toString(),
                    "-classpath",
                    String.join(File.pathSeparator, superPath, tempDir.toString(), System.getProperty("java.class.path")),
                    pathToPackage.resolve(className + ".java").toString()
            };
            args = Arrays.stream(args).skip(2).toArray(String[]::new);
            int resCode = compiler.run(null, null, OutputStream.nullOutputStream(), args);
            if (resCode != 0) {
                throw new ImplerException("Can not compile the class, compiler's return code is " + resCode + ".");
            }
            Path classFile = pathToPackage.resolve(className + CLASS);
            Manifest manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
            try (JarOutputStream out = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
                String sourceClassName = Paths.get("").resolve(token.getPackageName()
                        .replace('.', File.separatorChar))
                        .resolve(token.getSimpleName() + IMPL + CLASS).toString().replace("\\", "/");
                out.putNextEntry(new ZipEntry(sourceClassName));
                Files.copy(classFile, out);
                out.closeEntry();
            } catch (IOException | SecurityException e) {
                throw new ImplerException("Can not create jar-file: " + e.getMessage());
            }
        } finally {
            try {
                clean(tempDir);
            } catch (IOException e) {
                System.out.println("Unable to delete temp directory: " + e.getMessage());
            }
        }
    }

    /**
     * This function is used to choose which way of implementation to execute.
     * Runs {@link Implementor} in two possible ways:
     * <ul>
     * <li><tt>-jar className jarPath</tt> - runs {@link #implementJar(Class, Path)} with two second arguments</li>
     * </ul>
     * If arguments are incorrect or an error occurs during implementation returns message with information about error
     *
     * @param args arguments for running an application
     */
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Arguments expected");
            return;
        }
        for (String arg : args) {
            if (arg == null) {
                System.out.println("All arguments must be non-null");
            }
        }
        JarImpler implementor = new JarImplementor();
        try {
            implementor.implementJar(Class.forName(args[2]), Path.of(args[3]));
        } catch (InvalidPathException e) {
            System.out.println("Incorrect path to root: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Incorrect class name: " + e.getMessage());
        } catch (ImplerException e) {
            System.out.println("An error occurred during implementation: " + e.getMessage());
        }
    }

    /**
     * Static class used for recursive deleting of folders
     */
    private static class Cleaner extends SimpleFileVisitor<Path> {

        /**
         * Creates new instance if {@link Cleaner}
         */
        Cleaner() {
            super();
        }

        /**
         * Deletes file represented by <tt>file</tt>
         *
         * @param file  current file in fileTree
         * @param attrs attributes of file
         * @return {@link FileVisitResult#CONTINUE}
         * @throws IOException if error occurred during deleting of file
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        /**
         * Deletes directory represented by <tt>dir</tt>
         *
         * @param dir current visited directory in fileTree
         * @param exc <tt>null</tt> if the iteration of the directory completes without an error;
         *            otherwise the I/O exception that caused the iteration of the directory to complete prematurely
         * @return {@link FileVisitResult#CONTINUE}
         * @throws IOException if error occurred during deleting of directory
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
