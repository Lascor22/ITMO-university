package expression;

import expression.myEcxeptions.IllegalOperationException;

public class CheckedSqrt extends AbstractUnaryOperator {
    public CheckedSqrt(TripleExpression x) {
        super(x);
    }

    protected void check(int x) throws IllegalOperationException {
        if (x < 0) {
            throw new IllegalOperationException("Sqrt from negative");
        }
    }

    protected int apply(int x) throws IllegalOperationException {
        check(x);
        int l = 0;
        int r = 46341;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (m * m <= x) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }
}
