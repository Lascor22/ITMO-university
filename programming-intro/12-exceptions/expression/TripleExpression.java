package expression;

import expression.myEcxeptions.EvaluatingException;

public interface TripleExpression {
    int evaluate(int x, int y, int z) throws EvaluatingException;
}