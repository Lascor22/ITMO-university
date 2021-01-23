package expression;

import expression.myEcxeptions.EvaluatingException;

public abstract class AbstractUnaryOperator implements TripleExpression {
    private final TripleExpression operand;

    protected AbstractUnaryOperator(TripleExpression x) {
        operand = x;
    }

    protected abstract void check(int x) throws EvaluatingException;

    protected abstract int apply(int x) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(operand.evaluate(x, y, z));
    }
}