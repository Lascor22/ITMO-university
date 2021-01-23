import java.io.*;
import java.util.Scanner;

public class SumHexFile {
    public static void main(String args[]) {
        try {
            int ans = 0;
            try {
                Scanner scan = new Scanner(new File(args[0]), "UTF-8");
                while (scan.hasNext()) {
                    String temp = scan.next().toLowerCase();
                    boolean isHex = false;
                    if (temp.length() > 2 && temp.charAt(0) == '0' && temp.charAt(1) == 'x') {
                        temp = temp.replaceFirst("0x", "");
                        isHex = true;
                    }
                    try {
                        ans += isHex ? Integer.parseUnsignedInt(temp, 16) : Integer.parseInt(temp);
                    } catch (NumberFormatException e) {
                        System.out.println(temp + " Isn't a number!");
                    }
                }
                scan.close();
            } catch (IOException e) {
                System.out.println("File does not exist");
            }
            try {
                PrintStream out = new PrintStream(new FileOutputStream(args[1]));
                out.println(ans);
                out.close();
            } catch (IOException e) {
                System.out.println("No output file");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong input data");
        }
    }
}
