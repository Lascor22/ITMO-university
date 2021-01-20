package calculator;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorParserTest {
    private static int parse(String input) throws Exception {
        return new CalculatorParser(new CalculatorLexer(input)).getv();
    }

    @Test
    public void testShiftL() {
        try {
            int expected = parse("1 - 2 -3");
            Assert.assertEquals(1 - 2 -3, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShiftR() {
        try {
            int expected = parse("2 >> 100");
            Assert.assertEquals(2 >> 100, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShiftR2() {
        try {
            int expected = parse("2 >> 5 >> 10 >> 300");
            Assert.assertEquals(2 >> 5 >> 10 >> 300, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShiftL2() {
        try {
            int expected = parse("1 << 2 << 3");
            System.out.println(expected);
            Assert.assertEquals(1 << (2 << 3), expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTwoPlusFive() {
        try {
            int expected = parse("2 + 5");
            Assert.assertEquals(7, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTwoMulFive() {
        try {
            int expected = parse("2 * 5");
            Assert.assertEquals(10, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnaryMinusSeven() {
        try {
            int expected = parse("-7");
            Assert.assertEquals(-7, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFiveMinusFive() {
        try {
            int expected = parse("5 - 5");
            Assert.assertEquals(0, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFiveMulZero() {
        try {
            int expected = parse("5 * 0");
            Assert.assertEquals(0, expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllRules() {
        try {
            int expected = parse("(-3*(7-4)+2)");
            Assert.assertEquals((-3 * (7 - 4) + 2), expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllRulesWithSpaces() {
        try {
            int expected = parse("(-3\n*\r\r(7    -  \r\n  4)+\n2)     ");
            Assert.assertEquals((-3 * (7 - 4) + 2), expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void testLexicalAnalyzerException() throws Exception {
        new CalculatorLexer("1 + a * 178");
    }

    @Test(expected = Exception.class)
    public void testParserExceptionNotEnd() throws Exception {
        new CalculatorParser(new CalculatorLexer("1 + 5 * "));
    }

    @Test(expected = Exception.class)
    public void testParserExceptionDoubleMul() throws Exception {
        new CalculatorParser(new CalculatorLexer("1 + 5 * * 10"));
    }
}
