package expression;

import expression.exceptions.EvaluatingException;

public class High extends AbstractUnaryOperator {
    public High(TripleExpression x) {
        super(x);
    }

    @Override
    protected int apply(final int x) throws EvaluatingException {
        return Integer.highestOneBit(x);
    }

    @Override
    protected void check(int x) throws EvaluatingException {

    }
}
