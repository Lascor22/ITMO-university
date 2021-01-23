package expression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommonExpression res;
        res = new Multiply(new Subtract(new Variable("x"), new Const(1)), new Subtract(new Variable("x"), new Const(1)));
        Scanner in = new Scanner(System.in);
        double doubleValue = in.nextDouble();
        System.out.println(res.evaluate(doubleValue));
    }
}
