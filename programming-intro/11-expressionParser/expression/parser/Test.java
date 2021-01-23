package expression.parser;

import expression.TripleExpression;

public class Test {
    public static void main(String[] args) {
        try {
            ExpressionParser ex = new ExpressionParser();
            TripleExpression dt = ex.parse("x+y+z<<3+4-y");
            System.out.println(dt.evaluate(1, 2, 3));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
