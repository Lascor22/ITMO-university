import java.util.Scanner;

public class B {
    private static final long MODULE = 998_244_353;
    private static long[] p;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        p = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            p[i] = in.nextInt();
        }
        calculateSqrt(m);
        calculateExp(m);
        calculateLog(m);
    }

    private static void calculateSqrt(int m) {
        long[] result = new long[m];
        result[0] = 1;
        long[] q = new long[1];
        q[0] = 1;
        for (int i = 1; i < m; i++) {
            q = multiply(q, p, m);
            long c = choose(i);
            if (c < 0) {
                c += MODULE;
            }
            for (int j = 0; j < m; j++) {
                result[j] = (result[j] + ((c * at(q, j)) % MODULE)) % MODULE;
            }
        }
        print(result);
    }

    private static void calculateExp(int m) {
        long[] result = new long[m];
        result[0] = 1;
        long[] q = new long[1];
        q[0] = 1;
        long fact = 1;
        for (int i = 1; i < m; i++) {
            q = multiply(q, p, m);
            fact = (fact * i) % MODULE;
            for (int j = 0; j < m; j++) {
                result[j] = (result[j] + ((((invert(fact)) % MODULE) * at(q, j)) % MODULE)) % MODULE;
            }
        }
        print(result);
    }

    private static void calculateLog(int m) {
        long[] result = new long[m];
        result[0] = 0;
        long[] q = new long[1];
        q[0] = 1;
        long c = MODULE - 1;
        for (int i = 1; i < m; i++) {
            q = multiply(q, p, m);
            c = (c * (-1) + MODULE);
            for (int j = 0; j < m; j++) {
                result[j] = (result[j] + ((at(q, j) * ((c * invert(i)) % MODULE)) % MODULE)) % MODULE;
            }
        }
        print(result);
    }

    private static long[] multiply(long[] p, long[] q, int m) {
        int sizeP = p.length;
        int sizeQ = q.length;
        int size = Math.min(sizeP + sizeQ + 3, m);
        long[] result = new long[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= i; j++) {
                result[i] = (result[i] + ((at(p, j) * at(q, i - j)) % MODULE)) % MODULE;
            }
        }

        int degree = size - 1;
        while (degree > 0 && result[degree] == 0) {
            degree--;
        }
        long[] newRes = new long[degree + 1];
        System.arraycopy(result, 0, newRes, 0, degree + 1);
        return newRes;
    }

    private static long choose(long n) {
        long a = 1;
        long b = 1;
        n--;
        for (int i = 0; i <= n; i++) {
            a = (a * ((1 - 2 * i + MODULE) % MODULE)) % MODULE;
            b = (b * (((i + 1) * 2) % MODULE)) % MODULE;
        }
        return (a * invert(b)) % MODULE;
    }

    private static long reverse(long a, long mod) {
        if (mod == 1) {
            return a;
        }
        if (mod % 2 == 1) {
            return (a * reverse(a, mod - 1)) % MODULE;
        } else {
            long d = reverse(a, mod / 2);
            return (d * d) % MODULE;
        }
    }

    private static long invert(long a) {
        return reverse(a, MODULE - 2);
    }

    private static long at(long[] array, int ind) {
        if (array.length <= ind) {
            return 0;
        }
        return array[ind];
    }

    private static void print(long[] a) {
        for (long value : a) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
