import java.util.Scanner;

public class C {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        long[] a = new long[k];
        long[] c = new long[k + 1];
        long[] q = new long[k + 1];
        for (int i = 0; i < k; i++) {
            a[i] = in.nextLong();
        }
        for (int i = 1; i <= k; i++) {
            c[i] = in.nextLong();
        }
        q[0] = 1;
        for (int i = 1; i <= k; i++) {
            q[i] = -c[i];
        }
        long[] p = multiply(a, q, k);
        print(p);
        print(q);

    }

    private static long[] multiply(long[] p, long[] q, int k) {
        long[] result = new long[p.length + q.length + 1];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j <= i; j++) {
                long MODULE = 1000000000000L;
                result[i] = (result[i] + ((at(p, j) * at(q, i - j)) % MODULE)) % MODULE;
            }
        }

        int degree = result.length - 1 >= k ? k - 1 : result.length - 1;
        while (degree > 0 && result[degree] == 0) {
            degree--;
        }

        long[] newRes = new long[degree + 1];
        System.arraycopy(result, 0, newRes, 0, degree + 1);
        return newRes;
    }

    private static long at(long[] array, int ind) {
        if (array.length <= ind) {
            return 0;
        }
        return array[ind];
    }


    private static void print(long[] a) {
        System.out.println(a.length - 1);
        for (long value : a) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
