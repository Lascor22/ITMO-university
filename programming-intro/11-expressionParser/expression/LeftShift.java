package expression;

public class LeftShift extends AbstractBinaryOperator {
    public LeftShift(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    @Override
    protected int apply(final int x, final int y) {
        return x << y;
    }
}