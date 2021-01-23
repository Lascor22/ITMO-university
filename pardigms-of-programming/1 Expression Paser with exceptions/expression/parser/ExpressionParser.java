package expression.parser;

import expression.*;
import expression.exceptions.*;

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;

    public TripleExpression parse(final String expression) throws ParsingException {
        tokenizer = new Tokenizer(expression);
        return addSubtract();
    }

    private TripleExpression addSubtract() throws ParsingException {
        TripleExpression result = multiplyDivide();
        do {
            switch (tokenizer.getCurrentToken()) {
                case ADD:
                    result = new CheckedAdd(result, multiplyDivide());
                    break;
                case SUB:
                    result = new CheckedSubtract(result, multiplyDivide());
                    break;
                default:
                    return result;
            }
        } while (true);
    }

    private TripleExpression multiplyDivide() throws ParsingException {
        TripleExpression result = unary();
        do {
            switch (tokenizer.getCurrentToken()) {
                case MUL:
                    result = new CheckedMultiply(result, unary());
                    break;
                case DIV:
                    result = new CheckedDivide(result, unary());
                    break;
                default:
                    return result;
            }
        } while (true);
    }

    private TripleExpression unary() throws ParsingException {
        TripleExpression result;
        switch (tokenizer.getNextToken()) {
            case NUMBER:
                result = new Const(tokenizer.getValue());
                tokenizer.getNextToken();
                break;
            case VARIABLE:
                result = new Variable(tokenizer.getName());
                tokenizer.getNextToken();
                break;
            case MINUS:
                result = new CheckedNegate(unary());
                break;
            case HIGH:
                result = new High(unary());
                break;
            case LOW:
                result = new Low(unary());
                break;
            case OPEN_BRACE:
                int position = tokenizer.getIndex();
                result = addSubtract();
                if (tokenizer.getCurrentToken() != Tokenizer.Token.CLOSE_BRACE) {
                    throw new MissingClosingParenthesisException(tokenizer.getExpression(), position - 1);
                }
                tokenizer.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + tokenizer.getExpression());
        }
        return result;
    }

}