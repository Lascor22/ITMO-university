import java.util.Scanner;

public class I {
    private static final int MODULE = 104_857_601;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        long n = in.nextLong() - 1;
        long[] p = new long[2 * k];
        long[] q = new long[k + 1];

        for (int i = 0; i < k; i++) {
            p[i] = in.nextInt();
        }
        q[0] = 1;
        long temp;
        for (int i = 1; i <= k; i++) {
            temp = in.nextInt();
            q[i] = (-1 * temp + MODULE) % MODULE;
        }

        System.out.println(generate(n, k, p, q));
    }

    private static long generate(long n, int k, long[] p, long[] q) {
        while (n >= k) {
            for (int i = k; i < 2 * k; i++) {
                p[i] = 0;
                for (int j = 1; j <= k; j++) {
                    p[i] = (p[i] - ((q[j] * p[i - j]) % MODULE)) % MODULE;

                    if (p[i] < 0) {
                        p[i] += MODULE;
                    }
                }
            }
            long[] secQ = new long[k + 1];
            for (int i = 0; i <= k; i += 2) {
                secQ[i] = q[i];
            }
            for (int i = 1; i <= k; i += 2) {
                secQ[i] = (-1 * q[i] + MODULE) % MODULE;
            }
            long[] d = multiply(q, secQ);
            for (int i = 0; i <= k; i++) {
                q[i] = d[i * 2];
            }
            int begin = n % 2 == 0 ? 0 : 1;
            for (int i = begin; i < 2 * k; i += 2) {
                p[i / 2] = p[i];
            }
            n = n / 2;
        }
        return p[(int) n];
    }

    private static long[] multiply(long[] p, long[] q) {
        int sizeP = p.length;
        int sizeQ = q.length;
        int size = sizeP + sizeQ + 3;
        long[] result = new long[size];
        for (int i = 0; i < size; i += 2) {
            for (int j = 0; j <= i; j++) {
                result[i] = (result[i] + ((at(p, j) * at(q, i - j)) % MODULE)) % MODULE;
            }
        }
        return result;
    }

    private static long at(long[] array, int ind) {
        if (array.length <= ind) {
            return 0;
        }
        return array[ind];
    }
}