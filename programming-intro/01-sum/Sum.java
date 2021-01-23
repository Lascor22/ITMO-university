public class Sum {
    public static void main(String[] args) {
        int sumNumbers = 0, tempNum = 0, tempNumb = 0, sign = 1;
        for (int i = 0; i < args.length; i++) {
            int tempSum = 0;
            for (int j = 0; j < args[i].length(); j++) {
                char tempChar = args[i].charAt(j);
                if (tempChar == '-') {
                    sign = -1;
                }
                if (Character.isDigit(tempChar)) {
                    tempNum = Character.getNumericValue(tempChar);
                    if (tempNumb == 0) tempNumb += tempNum;
                    else tempNumb = 10 * tempNumb + tempNum;
                }
                if ((tempNumb != 0 && Character.isWhitespace(tempChar)) || (tempNumb != 0 && j == args[i].length() - 1)) {
                    tempSum += tempNumb * sign;
                    sign = 1;
                    tempNumb = 0;
                }
            }
            sumNumbers += tempSum;
            tempSum = 0;
        }
        System.out.println(sumNumbers);
    }
}