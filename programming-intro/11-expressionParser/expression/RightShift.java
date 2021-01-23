package expression;

public class RightShift extends AbstractBinaryOperator {
    public RightShift(final TripleExpression x, final TripleExpression y) {
        super(x, y);
    }

    @Override
    protected int apply(final int x, final int y) {
        return x >> y;

    }
}