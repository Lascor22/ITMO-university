package expression;

import expression.exceptions.OverflowException;

public class CheckedAdd extends AbstractBinaryOperator {
    public CheckedAdd(final TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    protected int apply(final int x, final int y) throws OverflowException {
        check(x, y);
        return x + y;
    }

    @Override
    protected void check(int x, int y) throws OverflowException {
        if (x > 0 && y > Integer.MAX_VALUE - x) {
            throw new OverflowException();
        }
        if (x < 0 && y < Integer.MIN_VALUE - x) {
            throw new OverflowException();
        }
    }

}