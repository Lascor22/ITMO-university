import java.math.BigInteger;
import java.util.Scanner;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        for (int i = 0; i < n; i++) {
            if (new BigInteger(in.nextLine()).isProbablePrime(70)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}
