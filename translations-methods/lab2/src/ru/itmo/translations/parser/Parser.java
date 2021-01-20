package ru.itmo.translations.parser;

import ru.itmo.translations.exceptions.ParseException;

import java.io.InputStream;

public class Parser {
    private Lexer lexer;

    public Tree parse(InputStream is) throws ParseException {
        lexer = new Lexer(is);
        lexer.nextToken();
        return E();
    }

    private Tree E() throws ParseException {
        switch (lexer.getCurrToken()) {
            case NOT:
            case VAR:
            case LEFT_BRACKET:
                return new Tree("E", T(), E1());
            default:
                throw new ParseException("Expected not, variable or (", lexer.getCurrPos());
        }
    }

    private Tree E1() throws ParseException {
        String node;
        switch (lexer.getCurrToken()) {
            case XOR:
                node = "xor";
                break;
            case OR:
                node = "or";
                break;
            case RIGHT_BRACKET:
            case END:
                return new Tree("E1");
            default:
                throw new ParseException("Expected xor, or tokens", lexer.getCurrPos());
        }
        lexer.nextToken();
        return new Tree("E1", new Tree(node), T(), E1());
    }

    private Tree T() throws ParseException {
        switch (lexer.getCurrToken()) {
            case NOT:
            case VAR:
            case LEFT_BRACKET:
                return new Tree("T", M(), T1());
            default:
                throw new ParseException("Expected not, variable or ( tokens", lexer.getCurrPos());
        }
    }

    private Tree T1() throws ParseException {
        String node;
        switch (lexer.getCurrToken()) {
            case AND:
                node = "and";
                break;
            case XOR:
            case OR:
            case RIGHT_BRACKET:
            case END:
                return new Tree("T1");
            default:
                throw new ParseException("Expected and token", lexer.getCurrPos());
        }
        lexer.nextToken();
        return new Tree("T1", new Tree(node), M(), T1());
    }

    private Tree M() throws ParseException {
        switch (lexer.getCurrToken()) {
            case NOT:
                lexer.nextToken();
                return new Tree("M", new Tree("not"), M1());
            case VAR:
            case LEFT_BRACKET:
                return new Tree("M", M1(), N());
            default:
                throw new ParseException("Expected not token", lexer.getCurrPos());
        }
    }

    private Tree N() throws ParseException {
        switch (lexer.getCurrToken()) {
            case AND:
            case XOR:
            case OR:
            case RIGHT_BRACKET:
            case END:
                return new Tree("N");
            default:
                throw new ParseException("Expected: operation token or )", lexer.getCurrPos());
        }
    }

    private Tree M1() throws ParseException {
        switch (lexer.getCurrToken()) {
            case VAR:
                lexer.nextToken();
                return new Tree("M", new Tree("v"));
            case LEFT_BRACKET:
                lexer.nextToken();
                Tree res = E();
                if (lexer.getCurrToken() != Token.RIGHT_BRACKET) {
                    throw new ParseException("", lexer.getCurrPos());
                }
                lexer.nextToken();
                return new Tree("M", new Tree("("), res, new Tree(")"));
            default:
                throw new ParseException("Expected: variable or ( tokens", lexer.getCurrPos());
        }
    }
}

