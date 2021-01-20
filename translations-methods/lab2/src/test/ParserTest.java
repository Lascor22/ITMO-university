package test;

import org.junit.Assert;
import org.junit.Test;

import ru.itmo.translations.exceptions.ParseException;
import ru.itmo.translations.parser.Parser;
import ru.itmo.translations.parser.Tree;

import java.io.ByteArrayInputStream;
import java.util.Random;


public class ParserTest {
    private final Parser parser = new Parser();
    private final Random random = new Random();

    private void test(String expected) {
        System.out.println(expected);
        try {
            Tree tree = parser.parse(new ByteArrayInputStream(expected.getBytes()));
            Assert.assertEquals(expected.intern(), tree.toString().intern());
        } catch (ParseException e) {
            Assert.fail();
        }
    }

    @Test
    public void simpleTest() {
        test("v or v and v");
    }

    @Test
    public void simpleNotTest() {
        test("not v");
    }

    @Test
    public void notTest() {
        test("not (v or v xor v and v)");
    }

    @Test
    public void bigExpressionTest() {
        test("(v and v or v xor v and v and v and v or v) and v or v xor not v and (v xor v or v and v) and not v");
    }

    @Test
    public void bigTest() {
        test("((((((((((v) or v) xor (v)) xor ((v) and v)) xor (((v) xor v) or (v))) or ((((v) xor v) and (v)) xor ((v) xor v))) xor (((((v) xor v) xor (v)) or ((v) xor v)) xor (((v) or v) and (v)))) and ((((((v) xor v) xor (v)) xor ((v) xor v)) xor (((v) and v) or (v))) xor ((((v) and v) and (v)) and ((v) or v)))) and (((((((v) xor v) or (v)) and ((v) xor v)) xor (((v) xor v) xor (v))) xor ((((v) xor v) and (v)) xor ((v) xor v))) xor (((((v) or v) xor (v)) xor ((v) and v)) and (((v) xor v) xor (v))))) xor ((((((((v) xor v) and (v)) or ((v) xor v)) xor (((v) xor v) xor (v))) and ((((v) xor v) xor (v)) xor ((v) xor v))) and (((((v) xor v) or (v)) or ((v) or v)) xor (((v) xor v) xor (v)))) xor ((((((v) xor v) or (v)) xor ((v) xor v)) xor (((v) or v) or (v))) xor ((((v) and v) or (v)) or ((v) xor v))))) xor (((((((((v) or v) and (v)) and ((v) xor v)) or (((v) xor v) or (v))) xor ((((v) xor v) or (v)) xor ((v) and v))) or (((((v) or v) or (v)) xor ((v) or v)) xor (((v) xor v) or (v)))) or ((((((v) and v) xor (v)) or ((v) xor v)) xor (((v) or v) or (v))) xor ((((v) and v) and (v)) xor ((v) or v)))) and (((((((v) xor v) and (v)) xor ((v) or v)) and (((v) xor v) and (v))) xor ((((v) and v) xor (v)) xor ((v) and v))) and (((((v) xor v) xor (v)) xor ((v) xor v)) xor (((v) or v) and (v)))))");
    }

    @Test
    public void randomTest() {
        for (int i = 0; i < 50; i++) {
            String expression = generateExpression(30);
            test(expression);
        }
    }

    private String generateExpression(int len) {
        return generateExpr(len) + generateOp(len);
    }

    private String generateOp(int len) {
        int r = random.nextInt(4);
        if (len == 0) {
            return "";
        }
        String generate = generateExpr(len - 1);
        String op = " xor ";
        switch (r) {
            case 0:
                op = " or ";
                break;
            case 1:
                op = " and ";
                break;
        }
        return op + generate;
    }

    private String generateExpr(int len) {
        if (len == 0) {
            return "v";
        }
        int r = random.nextInt(4);
        switch (r) {
            case 0:
                return "not v";
            case 1:
                return "v";
        }
        return "(" + generateExpression(len - 1) + ")";
    }
}
