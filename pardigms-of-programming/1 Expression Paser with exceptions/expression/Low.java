package expression;

import expression.exceptions.EvaluatingException;

public class Low extends AbstractUnaryOperator  {
    public Low(TripleExpression x) {
        super(x);
    }

    @Override
    protected int apply(final int x) throws EvaluatingException {
        return Integer.lowestOneBit(x);
    }

    @Override
    protected void check(int x) throws EvaluatingException {

    }
}
