import antlr.PrefixExpressionLexer;
import antlr.PrefixExpressionParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Parser {
    public static PrefixExpressionParser.ExpressionContext parse(String input) {
        return new PrefixExpressionParser(new CommonTokenStream(
                new PrefixExpressionLexer(CharStreams.fromString(input)))).expression();
    }
}
