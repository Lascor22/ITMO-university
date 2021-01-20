package generator;

import generator.domain.TokenItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LexerGenerator {
    private final Grammar grammar;

    LexerGenerator(Grammar grammar) throws IOException {
        this.grammar = grammar;
        Path pathDir = Paths.get(".", "gen", grammar.getName().toLowerCase());
        Files.createDirectories(pathDir);
        String enumFileName = grammar.getName() + "Token.java";
        String fileName = grammar.getName() + "Lexer.java";
        try (BufferedWriter writer = Files.newBufferedWriter(pathDir.resolve(enumFileName))) {
            writer.write(tokenClassText().toString());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(pathDir.resolve(fileName))) {
            writer.write(fileText().toString());
        }
    }

    private StringBuilder tokenClassText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
                package %s;
                                 
                public enum %sToken {
                \t""", grammar.getName().toLowerCase(), grammar.getName()));
        sb.append(String.join(",\n\t", grammar.getTokenItems().keySet()));
        sb.append("""
                ,
                    _END
                }
                """);
        return sb;
    }

    private StringBuilder fileText() {
        StringBuilder sb = new StringBuilder();
        String name = grammar.getName();

        sb.append(String.format("""
                package %s;
                                
                import java.util.ArrayList;
                import java.util.List;
                import java.util.regex.Matcher;
                import java.util.regex.Pattern;
                                
                """, name.toLowerCase()));

        sb.append(String.format("public class %sLexer {\n", name));


        sb.append(String.format("""
                    private final StringBuilder inputSB;
                    private final List<TokenItem> tokenPatterns = new ArrayList<>();
                    private final List<TokenItem> skipTokenPatterns = new ArrayList<>();
                    private final List<%sToken> tokens = new ArrayList<>();
                    private final List<String> tokensToString = new ArrayList<>();
                    private int currentPosition = 0;
                                
                """, name));

        sb.append(String.format("""
                    public %sLexer(String input) throws Exception {
                        inputSB = new StringBuilder(input);
                                
                """, name));

        for (TokenItem item : grammar.getTokens()) {
            sb.append(String.format("\t\ttokenPatterns.add(new TokenItem(%sToken.%s, %s));\n",
                    name, item.getName(), item.getPattern()));
        }

        sb.append("\n");

        for (TokenItem item : grammar.getSkipTokens()) {
            sb.append(String.format("\t\tskipTokenPatterns.add(new TokenItem(%sToken.%s, %s));\n",
                    name, item.getName(), item.getPattern()));
        }

        sb.append("""
                        getTokens();
                    }
                                
                """);

        sb.append(String.format("""
                    public %sToken getCurrentToken() {
                        return tokens.get(currentPosition);
                    }

                    public String getCurrentTokenString() {
                        return tokensToString.get(currentPosition);
                    }

                    public %sToken getNextToken() {
                        return tokens.get(++currentPosition);
                    }

                    private void getTokens() throws Exception {
                        while (inputSB.length() != 0) {
                            %sToken t = findFirstToken();
                            if (t != null) {
                                tokens.add(t);
                            } else if (!findFirstSkipToken()) {
                                throw new Exception("Not find matching with tokens.");
                            }
                        }
                        tokens.add(%sToken._END);
                    }

                    private boolean findFirstSkipToken() {
                        for (TokenItem item : skipTokenPatterns) {
                            Matcher m = item.pattern.matcher(inputSB.toString());
                            if (m.lookingAt()) {
                                inputSB.delete(0, m.end());
                                return true;
                            }
                        }
                        return false;
                    }

                    private %sToken findFirstToken() {
                        for (TokenItem item : tokenPatterns) {
                            Matcher m = item.pattern.matcher(inputSB.toString());
                            if (m.lookingAt()) {
                                tokensToString.add(inputSB.substring(0, m.end()));
                                inputSB.delete(0, m.end());
                                return item.token;
                            }
                        }
                        return null;
                    }

                    private static class TokenItem {
                        %sToken token;
                        Pattern pattern;
                        
                        TokenItem(%sToken token, String s) {
                            this.token = token;
                            pattern = Pattern.compile(s);
                        }
                    }
                }
                """, name, name, name, name, name, name, name
        ));
        return sb;
    }
}