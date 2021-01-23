package ru.ifmo.rain.karimov.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Walk {
    private static final int BUFFER_SIZE = 1024;
    private static final int INITIAL_HASHCODE = 0x811c9dc5;

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(args[0]))) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(createPath(args[1]))) {
                String pathName;
                while ((pathName = bufferedReader.readLine()) != null) {
                    Path currentPath = Paths.get(pathName);
                    int hashCode = INITIAL_HASHCODE;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int countBytes = 0;
                    try (InputStream inputStream = Files.newInputStream(currentPath)) {
                        while ((countBytes = inputStream.read(buffer)) >= 0) {
                            hashCode = hash(hashCode, buffer, countBytes);
                        }
                    } catch (IOException e) {
                        //ignored
                    }
                    bufferedWriter.write(String.format("%08x", hashCode) + " " + pathName);
                    bufferedWriter.newLine();
                }
            } catch (IOException ee) {
                System.out.println("Error in output file");
            }
        } catch (IOException e) {
            System.out.println("error in input file");
        }
    }

    private static Path createPath(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path);
        }
        return path;
    }

    private static int hash(final int prevHash, final byte[] bytes, int size) {
        int h = prevHash;
        for (int i = 0; i < size; i++) {
            h = (h * 0x01000193) ^ (bytes[i] & 0xff);
        }
        return h;
    }
}
