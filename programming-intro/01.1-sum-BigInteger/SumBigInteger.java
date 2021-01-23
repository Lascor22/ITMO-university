import java.math.BigInteger;

public class SumBigInteger {
    public static void main(String[] args) {
        BigInteger sumNumbers = BigInteger.ZERO;
        for (int i = 0; i < args.length; i++) {
            StringBuilder stringTemp = new StringBuilder();
            for (int j = 0; j < args[i].length(); j++) {
                char tempChar = args[i].charAt(j);
                if (Character.isDigit(tempChar) || tempChar == '-') {
                    stringTemp.append(tempChar);
                } else if (!stringTemp.toString().equals("")) {
                    sumNumbers = sumNumbers.add(new BigInteger(stringTemp.toString()));
                    stringTemp = new StringBuilder();
                }
            }
            if (!stringTemp.toString().equals("")) {
                sumNumbers = sumNumbers.add(new BigInteger(stringTemp.toString()));
            }
        }  
        System.out.println(sumNumbers);
    }
}