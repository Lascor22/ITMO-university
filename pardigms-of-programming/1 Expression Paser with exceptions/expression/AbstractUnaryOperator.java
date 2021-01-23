package expression;

import expression.exceptions.EvaluatingException;

public abstract class AbstractUnaryOperator implements TripleExpression {
    protected final TripleExpression operand;

    protected AbstractUnaryOperator(final TripleExpression x) {
        operand = x;
    }

    protected abstract int apply(final int x) throws EvaluatingException;

    protected abstract void check(final int x) throws EvaluatingException;

    public int evaluate(final int x, final int y, final int z) throws EvaluatingException {
        return apply(operand.evaluate(x, y, z));
    }
}