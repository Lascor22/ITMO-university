package md2html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MdParser {
    private List<String> paragraph;
    private FileManager fileManager;

    public MdParser(FileManager fileManager) {
        this.fileManager = fileManager;
        paragraph = new ArrayList<>();
    }

    public void parse() throws IOException {
        String line;
        while ((line = fileManager.getLine()) != null) {
            if (line.isEmpty()) {
                FirstEntry();
                paragraph.clear();
            } else {
                paragraph.add(line);
            }
        }
        FirstEntry();
        fileManager.close();
    }

    private void FirstEntry() throws IOException {
        if (paragraph.isEmpty()) {
            return;
        }
        int headerLevel = getHeaderLevel(paragraph.get(0));
        if (headerLevel > 0)
            paragraph.set(0, new StringBuilder(paragraph.get(0)).delete(0, headerLevel + 1).toString());
        printBorders(headerLevel);
        fileManager.newLine();
    }

    private int getHeaderLevel(String line) {
        int lvl = 0;
        while (line.charAt(lvl) == '#') {
            ++lvl;
        }
        return lvl * Boolean.compare(Character.isWhitespace(line.charAt(lvl)), false);
    }

    private void printBorders(int lvl) throws IOException {
        fileManager.write(lvl > 0 ? "<h" + lvl + ">" : "<p>");
        printParagraph();
        fileManager.write(lvl > 0 ? "</h" + lvl + ">" : "</p>");
    }

    private void printParagraph() throws IOException {
        Map<Character, Integer> tags = new HashMap<>();
        tags.put('*', 0);
        tags.put('_', 0);
        tags.put('-', 0);
        tags.put('+', 0);
        tags.put('`', 0);
        tags.put('\\', 0);
        tags.put('^', 0);
        tags.put(')', 0);
        tags.put('~', 0);
        for (String line : paragraph) {
            for (int j = 0; j < line.length(); ++j) {
                char ch = line.charAt(j);
                if (tags.get(ch) != null) {
                    if (ch == '*' || ch == '_') {
                        if (j + 1 < line.length() && ch == line.charAt(j + 1)) {
                            ++j;
                            tags.put(ch, tags.get(ch) + 1);
                        } else {
                            tags.put(--ch, tags.get(ch) + 1);
                        }
                    } else if (ch == '-' || ch == '+' || ch == '~') {
                        ++j;
                        tags.put(ch, tags.get(ch) + 1);
                    } else if (ch == '`') {
                        tags.put(ch, tags.get(ch) + 1);
                    } else if (ch == '\\') {
                        ++j;
                    }
                }
            }
        }
        for (Map.Entry<Character, Integer> pair : tags.entrySet()) {
            tags.put(pair.getKey(), pair.getValue() - pair.getValue() % 2);
        }
        Map<Character, String> specials = new HashMap<>();
        specials.put('<', "&lt;");
        specials.put('>', "&gt;");
        specials.put('&', "&amp;");
        for (int i = 0; i < paragraph.size(); ++i) {
            if (i > 0) fileManager.newLine();
            String line = paragraph.get(i);
            for (int j = 0; j < line.length(); ++j) {
                char c = line.charAt(j);
                if (c == '*' || c == '_') {
                    if (j + 1 < line.length() && c == line.charAt(j + 1) && tags.get(c) > 0) {
                        if (tags.get(c) % 2 == 0) {
                            fileManager.write("<strong>");
                        } else {
                            fileManager.write("</strong>");
                        }
                        ++j;
                        tags.put(c, tags.get(c) - 1);
                    } else {
                        --c;
                        if (tags.get(c) > 0) {
                            if (tags.get(c) % 2 == 0)
                                fileManager.write("<em>");
                            else
                                fileManager.write("</em>");
                            tags.put(c, tags.get(c) - 1);
                        } else fileManager.write(line.charAt(j));
                    }
                } else if (c == '-' || c == '+') {
                    if (j + 1 < line.length() && c == line.charAt(j + 1) && tags.get(c) > 0) {
                        if (tags.get(c) % 2 == 0) {
                            fileManager.write(c == '+' ? "<u>" : "<s>");
                        } else {
                            fileManager.write(c == '+' ? "</u>" : "</s>");
                        }
                        ++j;
                        tags.put(c, tags.get(c) - 1);
                    } else fileManager.write(line.charAt(j));
                } else if (c == '~') {
                    if (tags.get(c) > 0) {
                        if (tags.get(c) % 2 == 0) {
                            fileManager.write("<mark>");
                        } else {
                            fileManager.write("</mark>");
                        }
                        tags.put(c, tags.get(c) - 1);
                    }
                } else if (c == '`') {
                    if (tags.get(c) > 0) {
                        if (tags.get(c) % 2 == 0) {
                            fileManager.write("<code>");
                        } else {
                            fileManager.write("</code>");
                        }
                        tags.put(c, tags.get(c) - 1);
                    }
                } else if (specials.containsKey(c)) {
                    fileManager.write(specials.get(c));
                } else if (c != '\\') {
                    fileManager.write(line.charAt(j));
                }
            }
        }
    }
}