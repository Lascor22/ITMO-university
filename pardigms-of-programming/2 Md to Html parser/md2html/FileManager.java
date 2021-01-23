package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileManager {
    private BufferedReader reader;
    private BufferedWriter writer;

    public FileManager(final String input, final String output) throws IOException {
        reader = new BufferedReader(new FileReader(input, StandardCharsets.UTF_8));
        writer = new BufferedWriter(new FileWriter(output, StandardCharsets.UTF_8));
    }

    public String getLine() throws IOException {
        return reader.readLine();
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
    }

    public void newLine() throws IOException {
        writer.newLine();
    }

    public void write(final String string) throws IOException {
        writer.write(string);
    }

    public void write(final char c) throws IOException {
        writer.write(c);
    }
}