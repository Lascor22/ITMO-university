package ru.itmo.translations.parser;

import ru.itmo.translations.exceptions.ParseException;

import java.io.IOException;
import java.io.InputStream;

public class Lexer {
    private final InputStream is;

    private int currPos;
    private int currChar;
    private String currWord;
    private Token currToken = Token.START;

    Lexer(InputStream is) throws ParseException {
        this.is = is;
        currPos = 0;
        nextChar();
    }

    void nextToken() throws ParseException {
        next();
        switch (currWord) {
            case "(":
                currToken = Token.LEFT_BRACKET;
                break;
            case ")":
                currToken = Token.RIGHT_BRACKET;
                break;
            case "and":
                currToken = Token.AND;
                break;
            case "not":
                currToken = Token.NOT;
                break;
            case "or":
                currToken = Token.OR;
                break;
            case "xor":
                currToken = Token.XOR;
                break;
            default:
                if (currWord.length() == 1 && Character.isLetter(currWord.charAt(0))) {
                    currToken = Token.VAR;
                    return;
                }
                if (currWord.isEmpty()) {
                    currToken = Token.END;
                    return;
                }
                throw new ParseException("Illegal character " + currWord.charAt(0), currPos);
        }
    }

    Token getCurrToken() {
        return currToken;
    }

    int getCurrPos() {
        return currPos;
    }

    private void next() throws ParseException {
        while (Character.isWhitespace(currChar)) {
            nextChar();
        }
        currWord = currChar == -1 ? "" : String.valueOf((char) currChar);
        if (Character.isLetter(currChar)) {
            StringBuilder sb = new StringBuilder();
            while (Character.isLetter(currChar)) {
                sb.append((char) currChar);
                nextChar();
            }
            currWord = sb.toString();
        } else {
            nextChar();
        }
    }

    private void nextChar() throws ParseException {
        currPos++;
        try {
            currChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), currPos);
        }
    }
}

