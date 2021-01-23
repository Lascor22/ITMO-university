package expression;

import expression.myEcxeptions.EvaluatingException;

public abstract class AbstractBinaryOperator implements TripleExpression {
    private TripleExpression firstOperand;
    private TripleExpression secondOperand;

    protected AbstractBinaryOperator(TripleExpression x, TripleExpression y) {
        firstOperand = x;
        secondOperand = y;
    }

    protected abstract void check(int x, int y) throws EvaluatingException;

    protected abstract int apply(int x, int y) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return apply(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}