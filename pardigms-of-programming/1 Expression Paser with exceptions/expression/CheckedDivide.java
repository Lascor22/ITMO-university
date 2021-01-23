package expression;

import expression.exceptions.*;

public class CheckedDivide extends AbstractBinaryOperator {
    public CheckedDivide(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    @Override
    protected int apply(final int x, final int y) throws OverflowException, DivisionByZeroException {
        check(x, y);
        return x / y;
    }

    @Override
    protected void check(int x, int y) throws OverflowException, DivisionByZeroException {
        if (y == 0) {
            throw new DivisionByZeroException();
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }
}