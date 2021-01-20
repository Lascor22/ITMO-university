package generator;

import generator.domain.Rule;
import generator.domain.State;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class ParserGenerator {
    private final Grammar grammar;

    ParserGenerator(Grammar grammar) throws IOException {
        this.grammar = grammar;
        Path pathDir = Paths.get(".", "gen", grammar.getName().toLowerCase());
        Files.createDirectories(pathDir);
        String fileName = grammar.getName() + "Parser.java";
        try (BufferedWriter writer = Files.newBufferedWriter(pathDir.resolve(fileName))) {
            writer.write(fileText().toString());
        }
    }

    private StringBuilder fileText() {
        StringBuilder sb = new StringBuilder();
        String name = grammar.getName();

        sb.append(String.format("""
                package %s;

                import java.util.ArrayList;
                import java.util.List;
                """, name.toLowerCase()));

        for (String imp : grammar.getImports()) {
            sb.append(String.format("import %s;\n", imp));
        }
        sb.append("\n");

        sb.append(String.format("""
                public class %sParser {
                    public Node tree;
                    
                    public static class Node {
                        private final String name;
                        private final List<Node> children = new ArrayList<>();

                        Node(String name) {
                            this.name = name;
                        }

                        public void addChild(Node node) {
                            children.add(node);
                        }

                        public String getName() {
                            return name;
                        }

                        StringBuilder treeToString(ArrayList<Boolean> mask) {
                            StringBuilder sb = new StringBuilder();
                            if (!mask.isEmpty()) {
                                sb.append("|__");
                            }
                            sb.append("'").append(name).append("'").append("\\n");
                            for (int curChild = 0; curChild < children.size(); ++curChild) {
                                for (boolean b : mask) {
                                    if (b) {
                                        sb.append("|  ");
                                    } else {
                                        sb.append("   ");
                                    }
                                }
                                mask.add(curChild != children.size() - 1);
                                sb.append(children.get(curChild).treeToString(mask));
                                mask.remove(mask.size() - 1);
                            }
                          return sb;
                        }
                    }
                                
                """, name));

        for (State state : grammar.getStates().values()) {
            sb.append(printStateNode(state));
            sb.append("\n");
        }

        sb.append(String.format("\tprivate final %sLexer lexer;\n", name));

        sb.append(String.format(
                """
                            public %sParser(%sLexer lexer) throws Exception {
                                this.lexer = lexer;
                                buildTree();
                            }
                                                
                        """, name, name));

        sb.append(String.format("""
                    private void buildTree() throws Exception {
                        tree = _%s();
                        if (lexer.getCurrentToken() != %sToken._END) {
                            throw new Exception("Cur token is " + lexer.getCurrentToken().toString() + " but expected END.");
                        }
                    }
                                
                """, grammar.getStartState(), name));

        sb.append("""

                    public void printTree() {
                        System.out.println(tree.treeToString(new ArrayList<>()));
                    }
                                
                """);


        for (String returnStr : grammar.getStates().get(grammar.getStartState()).getReturns()) {
            String[] arg = returnStr.split(" ");
            sb.append(String.format("""
                        public %s get%s() {
                            return ((Node_E)tree).%s;
                        }
                                        
                    """, arg[0], arg[1], arg[1]));
        }

        sb.append(String.format("""
                    private void consume(%sToken token) throws Exception {
                        if (lexer.getCurrentToken() != token) {
                            throw new Exception("Expected another token.");
                        }
                    }

                """, name));

        for (State s : grammar.getStates().values()) {
            sb.append(printState(s));
            sb.append("\n");
        }

        sb.append("}\n");

        return sb;
    }

    private StringBuilder printStateNode(State s) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
                    public class Node_%s extends Node {
                        Node_%s() {
                            super("%s");
                        }
                """, s.getName(), s.getName(), s.getName()
        ));
        for (String str : s.getReturns()) {
            sb.append(String.format("\t\tpublic %s;\n", str));
        }
        sb.append("\t}\n");
        return sb;
    }

    private StringBuilder printState(State s) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("""
                            private Node_%s _%s(%s) throws Exception {
                                Node_%s res = new Node_%s();
                                switch (lexer.getCurrentToken()) {
                        """,
                s.getName(), s.getName(), String.join(", ", s.getParameters()),
                s.getName(), s.getName()));

        for (Rule rule : s.getRules()) {
            sb.append(printRule(rule, s));
        }

        return sb.append("""
                            default: 
                                throw new Exception("Unexpected token.");
                        }
                    }
                """);
    }

    private StringBuilder printFollowCase(State s, String action) {
        StringBuilder sb = new StringBuilder();
        for (String item : s.getFollow()) {
            sb.append(String.format("\t\t\tcase %s:\n", item));
        }
        if (sb.isEmpty()) {
            return sb;
        }
        sb.append(String.format("""
                            {
                                %s
                                return res;
                            }
                """, action));
        return sb;
    }

    private StringBuilder printRule(Rule rule, State s) {
        StringBuilder sb = new StringBuilder();
        HashSet<String> first = grammar.firstForRule(rule);
        boolean containsEps = false;
        for (String token : first) {
            if (!token.equals("EPS")) {
                sb.append(String.format("\t\t\tcase %s:\n", token));
            } else {
                containsEps = true;
                assert rule.getActions().size() == 1;
                sb.append(printFollowCase(s, rule.getActions().get(0)));
            }
        }
        if (sb.isEmpty() || containsEps) {
            return sb;
        }

        sb.append("\t\t\t{\n");

        int index = 0;
        for (int i = 0; i < rule.getItems().size(); i++) {
            String item = rule.getItems().get(i);
            if (grammar.getTokenItems().containsKey(item)) {
                sb.append(String.format("""
                                        consume(%sToken.%s);
                                        res.addChild(new Node("%s"));
                                        %s
                                        lexer.getNextToken();
                        """, grammar.getName(), item, item, rule.getActions().get(i)));
            } else if (grammar.getStates().containsKey(item)) {
                sb.append(String.format("""
                                        Node_%s n%d = _%s(%s);
                                        res.addChild(n%d);
                                        %s
                        """, item, index, item, rule.getParameters().get(i), index, rule.getActions().get(i)));
                ++index;
            } else {
                System.err.println("Not in token and states. " + item);
                System.exit(-1);
            }
        }
        sb.append("""
                                return res;
                            }
                """);
        return sb;
    }
}
