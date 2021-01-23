package expression;

public class Add extends AbstractBinaryOperator {
    public Add(final TripleExpression x, TripleExpression y) {
        super(x, y);
    }

    @Override
    protected int apply(final int x, final int y) {
        return x + y;
    }
}