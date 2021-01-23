package expression;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private final TripleExpression first;
    private final TripleExpression second;

    protected AbstractBinaryOperator(final TripleExpression x, final TripleExpression y) {
        first = x;
        second = y;
    }

    protected abstract int apply(final int x, final int y);

    public int evaluate(final int x, final int y, final int z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

}