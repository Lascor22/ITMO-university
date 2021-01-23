package ru.ifmo.rain.karimov.walk;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class HashCalcFileVisitor implements FileVisitor<Path> {
    private static final int INITIAL_HASHCODE = 0x811c9dc5;
    private static final int BUFFER_SIZE = 1024;
    private final BufferedWriter bufferedWriter;

    HashCalcFileVisitor(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        calcFileHash(file, bufferedWriter);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        writeHashAndPath(bufferedWriter, 0, file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    void calcFileHash(Path filePath, BufferedWriter bufferedWriter) throws IOException {
        int hashCode = INITIAL_HASHCODE;
        byte[] buffer = new byte[BUFFER_SIZE];
        int countBytes;
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            while ((countBytes = inputStream.read(buffer)) >= 0) {
                hashCode = hash(hashCode, buffer, countBytes);
            }
        } catch (IOException e) {
            hashCode = 0;
        }
        writeHashAndPath(bufferedWriter, hashCode, filePath.toString());
    }


    void writeHashAndPath(BufferedWriter bufferedWriter, int hashCode, String pathName) throws IOException {
        bufferedWriter.write(String.format("%08x", hashCode) + " " + pathName);
        bufferedWriter.newLine();
    }

    private static int hash(final int prevHash, final byte[] bytes, int size) {
        int h = prevHash;
        for (int i = 0; i < size; i++) {
            h = (h * 0x01000193) ^ (bytes[i] & 0xff);
        }
        return h;
    }
}
