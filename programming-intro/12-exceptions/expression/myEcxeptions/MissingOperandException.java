package expression.myEcxeptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(String s, int ind) {
        super("Missing operand before position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}