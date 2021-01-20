//package logic;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//public class LogicParserTest {
//
//    private static boolean parse(String input) throws Exception {
//        return new LogicParser(new LogicLexer(input)).getv();
//    }
//
//    @Test
//    public void falseTest() {
//        try {
//            boolean expected = parse("False");
//            Assert.assertFalse(expected);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void simpleOrTest() {
//        try {
//            boolean expected = parse("False or True");
//            Assert.assertTrue(expected);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void simpleXorTest() {
//        try {
//            boolean expected = parse("True xor True");
//            Assert.assertFalse(expected);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void simpleAndTest() {
//        try {
//            boolean expected = parse("False and True");
//            Assert.assertFalse(expected);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void simpleNotTest() {
//        try {
//            boolean expected = parse("not False");
//            Assert.assertTrue(expected);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void largeTest() {
//        try {
//            boolean expected = parse("(((((False) or (True))))) and ((not True) xor (True and True)) or not (False) and (False or True)");
//            Assert.assertTrue(expected);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}