package expression;

public class Subtract extends AbstractOperator {

    public Subtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    double operator(double x, double y) {
        return x - y;
    }

    @Override
    int operator(int x, int y) {
        return x - y;
    }
}
