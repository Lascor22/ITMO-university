package ru.ifmo.rain.karimov.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Implementor implements Impler {
    private static final String SOURCE_CODE_SUFFIX = "Impl.java";

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null || root == null) {
            throw new ImplerException("Arguments must not be null");
        }

        int modifiers = token.getModifiers();
        if (token.isPrimitive() || token.isArray() || token == Enum.class || Modifier.isFinal(modifiers)
                || Modifier.isPrivate(modifiers)) {
            throw new ImplerException("Unsupported token given");
        }

        Path sourcePath = createPath(token, root);
        try (BufferedWriter writer = Files.newBufferedWriter(sourcePath)) {
            writer.write(encode(CodeGenerator.generate(token)));
        } catch (IOException e) {
            throw new ImplerException("I/O error occurred", e);
        }
    }

    private Path createPath(Class<?> token, Path root) throws ImplerException {
        Path path;
        try {
            String sourcePath = String.join(File.separator,
                    token.getPackageName().split("\\.")) + File.separator + token.getSimpleName() + SOURCE_CODE_SUFFIX;
            path = Path.of(root.toString(), sourcePath);

        } catch (InvalidPathException e) {
            throw new ImplerException("Invalid path", e);
        }

        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new ImplerException("Can't create parent folder:" + e.getMessage());
            }
        }
        return path;
    }

    private static String encode(String arg) {
        StringBuilder builder = new StringBuilder();
        for (char c : arg.toCharArray()) {
            builder.append(c < 128 ? String.valueOf(c) : String.format("\\u%04x", (int) c));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: Implementor className path");
        }

        Class<?> token;
        try {
            token = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found by name");
            return;
        }

        Path root;
        try {
            root = Paths.get(args[1]);
        } catch (InvalidPathException e) {
            System.err.println("Error: Invalid root directory");
            return;
        }

        try {
            new Implementor().implement(token, root);
        } catch (ImplerException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
