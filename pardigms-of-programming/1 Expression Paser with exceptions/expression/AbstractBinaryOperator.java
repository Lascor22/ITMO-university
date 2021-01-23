package expression;

import expression.exceptions.EvaluatingException;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private final TripleExpression first;
    private final TripleExpression second;

    protected AbstractBinaryOperator(final TripleExpression x, final TripleExpression y) {
        first = x;
        second = y;
    }

    protected abstract int apply(final int x, final int y) throws EvaluatingException;

    protected abstract void check(final int x, final int y) throws EvaluatingException;

    public int evaluate(final int x, final int y, final int z) throws EvaluatingException {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

}