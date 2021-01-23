package expression.exceptions;

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException(String s, int ind) {
        super("Unknown symbol '" + s.charAt(ind) + "' at position " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}