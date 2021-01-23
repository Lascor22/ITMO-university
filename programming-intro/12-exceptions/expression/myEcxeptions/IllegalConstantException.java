package expression.myEcxeptions;

public class IllegalConstantException extends ParsingException {
    public IllegalConstantException(String reason, String s, int ind) {
        super("Constant '" + reason + "' is unsuitable for int at position: " + ind + "\n" + s + "\n" + getPlace(ind, reason.length()));
    }
}