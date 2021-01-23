package expression;

public class Const implements CommonExpression {
    private double value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return (int) value;
    }

    @Override
    public double evaluate(double x) {
        return value;
    }
}
