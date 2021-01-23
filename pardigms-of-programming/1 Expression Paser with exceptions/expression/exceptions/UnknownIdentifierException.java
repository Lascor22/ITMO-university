package expression.exceptions;

public class UnknownIdentifierException extends ParsingException {
    public UnknownIdentifierException(String id, String s, int ind) {
        super("Unknown identifier '" + id + "' at position: " + ind + "\n" + s + "\n" + getPlace(ind, id.length()));
    }
}