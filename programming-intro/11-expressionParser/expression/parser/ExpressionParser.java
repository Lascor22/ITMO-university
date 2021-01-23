package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    private String expression;
    private int ind = 0;

    private enum Token {
        NUMBER,
        VARIABLE,
        NOT,
        ADD,
        SUB,
        DIV,
        MUL,
        LEFT_SHIFT,
        RIGHT_SHIFT,
        OPEN_BRACE,
        CLOSE_BRACE,
        END,
        MISTAKE,
    }

    private Token curToken = Token.MISTAKE;

    private int value;
    private char name;

    private void skipWhiteSpace() {
        while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
            ind++;
        }
    }

    private void nextToken() {
        skipWhiteSpace();
        if (ind >= expression.length()) {
            curToken = Token.END;
            return;
        }
        char ch = expression.charAt(ind);
        switch (ch) {
            case '-':
                if (curToken == Token.NUMBER || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACE) {
                    curToken = Token.SUB;
                } else {
                    curToken = Token.NOT;
                }
                break;
            case '+':
                curToken = Token.ADD;
                break;
            case '*':
                curToken = Token.MUL;
                break;
            case '/':
                curToken = Token.DIV;
                break;
            case '(':
                curToken = Token.OPEN_BRACE;
                break;
            case ')':
                curToken = Token.CLOSE_BRACE;
                break;
            default:
                if (Character.isDigit(ch)) {
                    int l = ind;
                    while (ind < expression.length() && Character.isDigit(expression.charAt(ind))) {
                        ind++;
                    }
                    int r = ind;
                    value = Integer.parseUnsignedInt(expression.substring(l, r));
                    curToken = Token.NUMBER;
                    ind--;
                } else if (ch == 'x' || ch == 'y' || ch == 'z') {
                    name = ch;
                    curToken = Token.VARIABLE;
                } else if (expression.substring(ind, ind + 2).equals("<<")) {
                    curToken = Token.LEFT_SHIFT;
                    ind++;
                } else if (expression.substring(ind, ind + 2).equals(">>")) {
                    curToken = Token.RIGHT_SHIFT;
                    ind++;
                } else {
                    curToken = Token.MISTAKE;
                }
        }
        ind++;
    }

    private TripleExpression unary() {
        nextToken();
        TripleExpression res;
        switch (curToken) {
            case NUMBER:
                res = new Const(value);
                nextToken();
                break;
            case VARIABLE:
                res = new Variable(name);
                nextToken();
                break;
            case NOT:
                res = new Not(unary());
                break;
            case OPEN_BRACE:
                res = shifts();
                nextToken();
                break;
            default:
                return new Const(0);
        }
        return res;
    }

    private TripleExpression mulDiv() {
        TripleExpression res = unary();
        do {
            switch (curToken) {
                case MUL:
                    res = new Multiply(res, unary());
                    break;
                case DIV:
                    res = new Divide(res, unary());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression addSub() {
        TripleExpression res = mulDiv();
        do {
            switch (curToken) {
                case ADD:
                    res = new Add(res, mulDiv());
                    break;
                case SUB:
                    res = new Subtract(res, mulDiv());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression shifts() {
        TripleExpression res = addSub();
        do {
            switch (curToken) {
                case LEFT_SHIFT:
                    res = new LeftShift(res, addSub());
                    break;
                case RIGHT_SHIFT:
                    res = new RightShift(res, addSub());
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    public TripleExpression parse(final String s) {
        ind = 0;
        expression = s;
        curToken = Token.MISTAKE;
        return shifts();
    }
}