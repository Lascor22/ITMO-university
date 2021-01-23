package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends AbstractUnaryOperator {

    public CheckedNegate(final TripleExpression x) {
        super(x);
    }

    protected int apply(final int x) throws OverflowException {
        check(x);
        return -x;
    }

    @Override
    protected void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }
}