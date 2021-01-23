package expression.parser;

import expression.exceptions.*;

import java.util.*;

public class Tokenizer {

    public enum Token {
        NUMBER,
        VARIABLE,
        MINUS,
        LOW,
        HIGH,
        ADD,
        SUB,
        DIV,
        MUL,
        OPEN_BRACE,
        CLOSE_BRACE,
        END,
        BEGIN,
    }

    private String expression;
    private int index;
    private Token currentToken;
    private static Set<Token> operations = EnumSet.of(Token.HIGH, Token.MINUS, Token.LOW, Token.ADD, Token.SUB, Token.MUL, Token.DIV);
    private static Set<Token> BINARY_OPERATIONS = EnumSet.of(Token.ADD, Token.SUB, Token.MUL, Token.DIV);
    private static Map<String, Token> IDENTIFIERS = new HashMap<>();

    private int value;
    private char name;
    private int balance;

    public Tokenizer(final String s) {
        IDENTIFIERS.put("high", Token.HIGH);
        IDENTIFIERS.put("low", Token.LOW);
        IDENTIFIERS.put("x", Token.VARIABLE);
        IDENTIFIERS.put("y", Token.VARIABLE);
        IDENTIFIERS.put("z", Token.VARIABLE);
        expression = s;
        index = 0;
        currentToken = Token.BEGIN;
        balance = 0;
    }

    public Token getNextToken() throws ParsingException {
        nextToken();
        return currentToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public int getValue() {
        return value;
    }

    public char getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public String getExpression() {
        return expression;
    }

    private void skipWhiteSpace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            ++index;
        }
    }

    private void checkForOperand(int pos) throws MissingOperandException {
        if (operations.contains(currentToken) || currentToken == Token.OPEN_BRACE || currentToken == Token.BEGIN) {
            throw new MissingOperandException(expression, pos);
        }
    }

    private void checkForOperation(int pos) throws MissingOperationException {
        if (currentToken == Token.CLOSE_BRACE || currentToken == Token.VARIABLE || currentToken == Token.NUMBER) {
            throw new MissingOperationException(expression, pos);
        }
    }

    private String getNumber() {
        int left = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            ++index;
        }
        int right = index;
        --index;
        return expression.substring(left, right);
    }

    private boolean isPartOfIdentifier(char x) {
        return Character.isLetterOrDigit(x) || x == '_';
    }

    private String getIdentifier() throws UnknownSymbolException {
        if (!Character.isLetter(expression.charAt(index))) {
            throw new UnknownSymbolException(expression, index);
        }
        int left = index;
        while (index < expression.length() && isPartOfIdentifier(expression.charAt(index))) {
            ++index;
        }
        int right = index;
        --index;
        return expression.substring(left, right);
    }

    private void nextToken() throws ParsingException {
        skipWhiteSpace();
        if (index >= expression.length()) {
            checkForOperand(index);
            currentToken = Token.END;
            return;
        }
        switch (expression.charAt(index)) {
            case '-':
                if (currentToken == Token.NUMBER || currentToken == Token.VARIABLE || currentToken == Token.CLOSE_BRACE) {
                    currentToken = Token.SUB;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, index + 1);
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        ++index;
                        String temp = getNumber();
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException NFE) {
                            throw new IllegalConstantException("-" + temp, expression, index - temp.length());
                        }
                        currentToken = Token.NUMBER;
                    } else {
                        currentToken = Token.MINUS;
                    }
                }
                break;
            case '+':
                checkForOperand(index);
                currentToken = Token.ADD;
                break;
            case '*':
                checkForOperand(index);
                currentToken = Token.MUL;
                break;
            case '/':
                checkForOperand(index);
                currentToken = Token.DIV;
                break;
            case '(':
                checkForOperation(index);
                balance++;
                currentToken = Token.OPEN_BRACE;
                break;
            case ')':
                if (operations.contains(currentToken) || currentToken == Token.OPEN_BRACE) {
                    throw new MissingOperandException(expression, index);
                }
                --balance;
                if (balance < 0) {
                    throw new OddClosingParenthesisException(expression, index);
                }
                currentToken = Token.CLOSE_BRACE;
                break;
            default:
                if (Character.isDigit(expression.charAt(index))) {
                    checkForOperation(index);
                    String temp = getNumber();
                    try {
                        value = Integer.parseInt(temp);
                    } catch (NumberFormatException NFE) {
                        throw new IllegalConstantException(temp, expression, index - temp.length() + 1);
                    }
                    currentToken = Token.NUMBER;
                } else {
                    String curInd = getIdentifier();
                    if (!IDENTIFIERS.containsKey(curInd)) {
                        throw new UnknownIdentifierException(curInd, expression, index - curInd.length() + 1);
                    }
                    if (BINARY_OPERATIONS.contains(IDENTIFIERS.get(curInd))) {
                        checkForOperand(index - curInd.length() + 1);
                    } else {
                        checkForOperation(index - curInd.length() + 1);
                    }
                    currentToken = IDENTIFIERS.get(curInd);
                    if (currentToken == Token.VARIABLE) {
                        name = curInd.charAt(0);
                    }
                }
        }
        ++index;
    }
}
