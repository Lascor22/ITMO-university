package ru.ifmo.rain.karimov.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RecursiveWalk {

    public static void main(String[] args) {
        try {
            if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
                throw new IllegalArgumentException("expected 2 arguments");
            }
            Path inputPath, outputPath;
            try {
                inputPath = Paths.get(args[0]);
                outputPath = Paths.get(args[1]);
                if (outputPath.getParent() != null && !Files.exists(outputPath.getParent())) {
                    Files.createDirectory(outputPath.getParent());
                }
                WalkFiles(inputPath, outputPath);
            } catch (InvalidPathException | IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void WalkFiles(Path inputPath, Path outputPath) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(inputPath)) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(outputPath)) {
                String pathName;
                HashCalcFileVisitor myVisitor = new HashCalcFileVisitor(bufferedWriter);
                while ((pathName = bufferedReader.readLine()) != null) {
                    try {
                        Path currentPath = Paths.get(pathName);
                        if (Files.isDirectory(currentPath)) {
                            Files.walkFileTree(currentPath, myVisitor);
                        } else {
                            myVisitor.calcFileHash(currentPath, bufferedWriter);
                        }
                    } catch (InvalidPathException e) {
                        myVisitor.writeHashAndPath(bufferedWriter, 0, pathName);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error in output file " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("error in input file " + e.getMessage());
        }
    }
}
