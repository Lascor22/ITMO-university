package generator;

import antlr.GeneratorLexer;
import antlr.GeneratorParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            generate("CalculatorGrammar.gr");
//            generate("LogicGrammar.gr");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generate(String fileName) throws IOException {
        Grammar grammar = new GeneratorParser(new CommonTokenStream(
                new GeneratorLexer(CharStreams.fromFileName(fileName)))).start().g;
        grammar.createFirst();
        grammar.createFollow();
        new LexerGenerator(grammar);
        new ParserGenerator(grammar);
    }
}
