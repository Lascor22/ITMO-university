package expression;

import expression.myEcxeptions.OverflowException;

public class CheckedNegate extends AbstractUnaryOperator {

    public CheckedNegate(TripleExpression x) {
        super(x);
    }

    protected void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply(int x) throws OverflowException {
        check(x);
        return -x;
    }
}