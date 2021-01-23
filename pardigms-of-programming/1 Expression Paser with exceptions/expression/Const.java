package expression;

public class Const implements TripleExpression {
    private final int value;

    public Const(final int x) {
        value = x;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return value;
    }
}