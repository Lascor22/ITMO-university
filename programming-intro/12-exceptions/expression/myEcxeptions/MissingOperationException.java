package expression.myEcxeptions;

public class MissingOperationException extends ParsingException {
    public MissingOperationException(String s, int ind) {
        super("Missing operation before position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}