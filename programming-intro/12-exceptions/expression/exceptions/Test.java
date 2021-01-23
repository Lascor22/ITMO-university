package expression.exceptions;

import expression.myEcxeptions.EvaluatingException;
import expression.myEcxeptions.ParsingException;
import expression.parser.ExpressionParser;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String st = "x+y+z+t*3";
        int x = 1;
        int y = 2;
        int z = 3;
        try {
            int res = new ExpressionParser().parse(st).evaluate(x, y, z);
            System.out.println(res);
        } catch (ParsingException | EvaluatingException e) {
            System.out.println(e.getMessage());
        }
    }
}
