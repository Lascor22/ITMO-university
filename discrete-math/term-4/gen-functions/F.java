import java.util.Scanner;

public class F {
    private static final long MODULE = 1000000007;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        int m = in.nextInt();

        long[] a = new long[k];
        long[] result = new long[m + 1];
        long[] sum = new long[m + 1];
        result[0] = 1;
        sum[0] = 1;

        for (int i = 0; i < k; i++) {
            a[i] = in.nextInt();
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 0; j < k; j++) {
                if (i >= a[j]) {
                    result[i] = (result[i] + sum[(int) (i - a[j])]) % MODULE;
                }
            }

            for (int j = 0; j <= i; j++) {
                sum[i] = (sum[i] + ((result[j] * result[i - j]) % MODULE)) % MODULE;
            }
        }
        for (int i = 1; i <= m; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
