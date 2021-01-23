package expression;

import expression.myEcxeptions.IllegalOperationException;
import expression.myEcxeptions.OverflowException;

public class CheckedDivide extends AbstractBinaryOperator {
    public CheckedDivide(TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    protected void check(int x, int y) throws IllegalOperationException, OverflowException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    protected int apply(int x, int y) throws IllegalOperationException, OverflowException {
        check(x, y);
        return x / y;
    }
}