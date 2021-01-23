package expression;

import expression.myEcxeptions.OverflowException;

public class CheckedAbs extends AbstractUnaryOperator {

    public CheckedAbs(TripleExpression x) {
        super(x);
    }

    protected void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int apply(int x) throws OverflowException {
        check(x);
        int res = x;
        if (x < 0) {
            res = -res;
        }
        return res;
    }
}