public class Main {

    public static void main(String[] args) {
        String str;
//        str = "= a 0 = b 0 if == a 0 print 5 print 3";
        str = "= a 0 = b input if == a 0 print 5 print 3 print + a input if == a b print + - a b * c d";
//        str = "= a 0 = b 0 if == a 0 print 3 if == b 0 print 3";
//        str = "if > 2 input print -5";
        System.out.println(Parser.parse(str).val);
    }


}