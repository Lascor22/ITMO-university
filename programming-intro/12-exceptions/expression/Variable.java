package expression;


public class Variable implements TripleExpression {
    private char name;

    public Variable(char x) {
        name = x;
    }

    public Variable(String x) {
        name = x.charAt(0);
    }

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            default:
                return 0;
        }
    }
}