package ru.itmo.translations.exceptions;

public class ParseException extends Exception {
    public ParseException(String message, int position) {
        super(message + " at position " + position);
    }
}
