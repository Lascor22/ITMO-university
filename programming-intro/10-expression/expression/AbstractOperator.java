package expression;

public abstract class AbstractOperator implements CommonExpression {
    private CommonExpression first;
    private CommonExpression second;

    public AbstractOperator(CommonExpression first, CommonExpression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int evaluate(int x) {
        return operator(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return operator(first.evaluate(x), second.evaluate(x));
    }

    abstract double operator(double x, double y);

    abstract int operator(int x, int y);
}
