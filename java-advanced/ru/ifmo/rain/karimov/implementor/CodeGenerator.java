package ru.ifmo.rain.karimov.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;


class CodeGenerator {
    /**
     * Semicolon for generated classes
     */
    private static final String SC = ";";
    /**
     * WhiteSpace for generated classes
     */
    private static final String WS = " ";
    /**
     * Open curly brace for generated classes
     */
    private static final String OPEN_CB = "{";
    /**
     * Close curly brace for generated classes
     */
    private static final String CLOSE_CB = "}";
    /**
     * Open round brace for generated classes
     */
    private static final String OPEN_RB = "(";
    /**
     * Close round brace for generated classes
     */
    private static final String CLOSE_RB = ")";
    /**
     * Delimiter for function arguments in generated classes
     */
    private static final String DELIMITER = ", ";
    /**
     * Line separator for generated classes
     */
    private static final String NEW_LINE = System.lineSeparator();
    /**
     * Intended for generated classes
     */
    private static final String TAB = "    ";
    /**
     * Line separator for methods in generated classes
     */
    private static final String NEW_LINE_METHODS = NEW_LINE + TAB;
    /**
     * Suffix in names of generated classes
     */
    private static final String IMPL = "Impl";

    /**
     * Produces code implementing class or interface specified by provided <tt>token</tt>.
     * <p>
     * Generated class classes name should be same as classes name of the type token with <tt>Impl</tt> suffix
     * added.
     *
     * @param token type token to create implementation for.
     * @throws ImplerException when implementation cannot be
     *                         generated.
     */
    static String generate(Class<?> token) throws ImplerException {
        return createPackage(token) + createHead(token) + (
                token.isInterface() ? "" : createConstructors(token)) + createMethods(token) + NEW_LINE + CLOSE_CB;
    }

    /**
     * Gets package of given file. Package is empty, if class is situated in default package
     *
     * @param token class to get package
     * @return {@link String} representing package
     */
    private static String createPackage(Class<?> token) {
        if ("".equals(token.getPackageName())) {
            return NEW_LINE + NEW_LINE;
        }
        return "package" + WS + token.getPackageName() + SC + NEW_LINE + NEW_LINE;
    }

    /**
     * Returns beginning declaration of the class, containing its  name, base class or
     * implemented interface
     *
     * @param token base class or implemented interface
     * @return {@link String} representing beginning of class declaration
     */
    private static String createHead(Class<?> token) {
        return "public" + WS + "class" + WS + token.getSimpleName() + IMPL + WS +
                (token.isInterface() ? "implements" : "extends") + WS + token.getCanonicalName() +
                WS + OPEN_CB + NEW_LINE_METHODS;
    }

    /**
     * Returns code of abstract methods of given {@link Class}
     *
     * @param token given class to implement abstract methods
     * @return Code of abstract methods with default implementation
     */
    private static String createMethods(Class<?> token) {
        HashSet<SignatureComparedMethod> methodsSet = Arrays.stream(token.getMethods())
                .map(SignatureComparedMethod::new)
                .collect(Collectors.toCollection(HashSet::new));
        for (; token != null; token = token.getSuperclass()) {
            methodsSet.addAll(Arrays.stream(token.getDeclaredMethods())
                    .map(SignatureComparedMethod::new)
                    .collect(Collectors.toCollection(HashSet::new)));
        }
        return methodsSet.stream().filter(m -> Modifier.isAbstract(m.getMethod().getModifiers()))
                .map(m -> createMethod(m.getMethod())).collect(Collectors.joining(NEW_LINE + NEW_LINE_METHODS));
    }

    /**
     * Returns fully constructed {@link Method}, that returns default value of return type
     * of such {@link Method}
     *
     * @param method given {@link Method}
     * @return {@link String} representing code of such {@link Method}
     */
    private static String createMethod(Method method) {
        final int mods = method.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.NATIVE & ~Modifier.TRANSIENT;
        return Modifier.toString(mods) + (mods > 0 ? WS : "") + method.getReturnType().getCanonicalName() + WS + method.getName() + OPEN_RB
                + Arrays.stream(method.getParameters())
                .map(parameter -> parameter.getType().getCanonicalName() + WS + parameter.getName())
                .collect(Collectors.joining(DELIMITER)) + CLOSE_RB +
                createExceptions(method) + WS
                + OPEN_CB + NEW_LINE_METHODS + TAB +
                createBody(method) + NEW_LINE_METHODS + CLOSE_CB;
    }

    /**
     * Return default value of return type of such {@link Method}
     *
     * @param method given {@link Method}
     * @return {@link String} representing body, defined above
     */
    private static String createBody(Method method) {
        Class<?> token = method.getReturnType();
        String defaultValue = " null";
        if (token.equals(boolean.class)) {
            defaultValue = " false";
        } else if (token.equals(void.class)) {
            defaultValue = "";
        } else if (token.isPrimitive()) {
            defaultValue = " 0";
        }
        return "return" + defaultValue + SC;
    }

    /**
     * Returns list of exceptions, that given {@link Executable} may throw
     *
     * @param executable {@link Executable} to get exceptions from
     * @return {@link String} representing list of exceptions
     */
    private static String createExceptions(Executable executable) {
        Class<?>[] exceptions = executable.getExceptionTypes();
        return (exceptions.length > 0 ? (WS + "throws" + WS) : "") +
                Arrays.stream(exceptions).map(Class::getCanonicalName)
                        .collect(Collectors.joining(DELIMITER));
    }

    /**
     * Returns code of constructors of given {@link Class}
     *
     * @param token given class to implement consructors
     * @return Code of constructors with call constructor of superclass
     * @throws ImplerException if class doesn't have any non-private constructors
     */
    private static String createConstructors(Class<?> token) throws ImplerException {
        Constructor<?>[] constructors = Arrays.stream(token.getDeclaredConstructors())
                .filter(constructor -> !Modifier.isPrivate(constructor.getModifiers())).toArray(Constructor[]::new);
        if (constructors.length == 0) {
            throw new ImplerException("class must have at least 1 public constructor");
        }
        return Arrays.stream(constructors).map(CodeGenerator::createConstructor)
                .collect(Collectors.joining(NEW_LINE_METHODS)) + NEW_LINE_METHODS;
    }

    /**
     * Returns fully constructed {@link Constructor}, that calls constructor of super class
     *
     * @param constructor given {@link Constructor}
     * @return {@link String} representing code of such {@link Constructor}
     */
    private static String createConstructor(Constructor<?> constructor) {
        final int mods = constructor.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.NATIVE & ~Modifier.TRANSIENT;
        return Modifier.toString(mods) + (mods > 0 ? WS : "") +
                constructor.getDeclaringClass().getSimpleName() + IMPL + OPEN_RB +
                Arrays.stream(constructor.getParameters())
                        .map(parameter -> parameter.getType().getCanonicalName() + WS + parameter.getName())
                        .collect(Collectors.joining(DELIMITER)) +
                CLOSE_RB + createExceptions(constructor) + WS + OPEN_CB +
                NEW_LINE_METHODS + TAB + createBody(constructor) + NEW_LINE_METHODS +
                CLOSE_CB + NEW_LINE_METHODS;
    }

    /**
     * Calls constructor of super class
     *
     * @param constructor given {@link Constructor}
     * @return {@link String} representing body, defined above
     */
    private static String createBody(Constructor<?> constructor) {
        return "super" + OPEN_RB + Arrays.stream(constructor.getParameters())
                .map(Parameter::getName)
                .collect(Collectors.joining(DELIMITER)) + CLOSE_RB + SC;
    }

    /**
     * Static class used for correct representing {@link Method}
     */
    private static class SignatureComparedMethod {
        /**
         * Wrapped instance of {@link Method}
         */
        private Method method;
        /**
         * Base used for calculating hashcode
         */
        private static final int POW = 31;
        /**
         * Module used for calculating hashcode
         */
        private static final int MOD = 1000000007;

        /**
         * Constructs a wrapper for specified instance of {@link Method}.
         *
         * @param method instance if {@link Method} to be wrapped
         */
        SignatureComparedMethod(Method method) {
            this.method = method;
        }

        /**
         * Getter for {@link #method}.
         *
         * @return wrapped instance of {@link Method}
         */
        Method getMethod() {
            return method;
        }

        /**
         * Calculates hashcode for this wrapper via polynomial hashing
         * using hashes of name, return type and parameters' types of its {@link #method}
         *
         * @return hashcode for this wrapper
         */
        @Override
        public int hashCode() {
            int hash = method.getReturnType().hashCode() % MOD;
            hash = (hash + POW * method.getName().hashCode()) % MOD;
            hash = (hash + POW * POW * Arrays.hashCode(method.getParameterTypes()));
            return hash;
        }

        /**
         * Compares the specified object with this wrapper for equality. Wrappers are equal, if their wrapped
         * methods have equal name, return type and parameters' types.
         *
         * @param o the object to be compared for equality with this wrapper
         * @return true if specified object is equal to this wrapper
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SignatureComparedMethod that = (SignatureComparedMethod) o;
            return Objects.equals(method.getReturnType(), that.method.getReturnType()) &&
                    method.getName().equals(that.method.getName()) &&
                    Arrays.equals(method.getParameterTypes(), that.method.getParameterTypes());
        }
    }

}
