package expression.myEcxeptions;

public class OverflowException extends EvaluatingException {
    public OverflowException() {
        super("overflow");
    }
}