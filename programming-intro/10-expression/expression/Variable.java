package expression;

public class Variable implements CommonExpression {
    private String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }
}
