import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class G {
    private static List<List<List<Integer>>> lPart;
    private static List<List<List<Integer>>> sPart;

    public static void main(String[] args) {
        Scanner in = new Scanner(String.join(" ", new Scanner(System.in).nextLine().split("")));
        lPart = new ArrayList<>();
        sPart = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            lPart.add(listInit(i));
            sPart.add(setInit(i, 1));
        }
        long[] result = object(in);
        for (long i : result) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static long[] object(Scanner in) {
        char c = in.next().charAt(0);

        if (c == 'B') {
            return new long[]{0, 1, 0, 0, 0, 0, 0};
        }

        in.next();
        long[] ins = object(in);
        in.next();

        long[] result = new long[7];

        if (c == 'L') {
            result[0] = 1;
            for (int i = 1; i < 7; i++) {
                for (List<Integer> list : lPart.get(i)) {
                    long product = 1;
                    for (Integer e : list) {
                        product *= ins[e];
                    }
                    result[i] += product;
                }
            }
        }

        if (c == 'S') {
            result[0] = 1;
            for (int i = 1; i < 7; i++) {
                for (List<Integer> list : sPart.get(i)) {
                    long product = 1;
                    int[] used = new int[7];
                    for (Integer e : list) {
                        used[e]++;
                    }
                    for (int j = 1; j < 7; j++) {
                        product *= choose(ins[j], used[j]);
                    }
                    result[i] += product;
                }
            }
        }

        if (c == 'P') {
            long[] r = object(in);
            in.next();
            for (int i = 0; i < 7; ++i) {
                for (int j = 0; j <= i; ++j) {
                    result[i] += ins[j] * r[i - j];
                }
            }
        }
        return result;
    }

    private static long choose(long n, long k) {
        n = n + k - 1;
        if (k == 0) {
            return 1;
        }
        if (n < k) {
            return 0;
        }
        long result = 1;
        for (long i = n - k + 1; i <= n; i++) {
            result *= i;
        }
        for (long i = 2; i <= k; i++) {
            result /= i;
        }
        return result;
    }

    private static List<List<Integer>> listInit(int n) {
        List<List<Integer>> result = new ArrayList<>();
        for (int j = 1; j < n; j++) {
            List<List<Integer>> temp = listInit(n - j);
            for (List<Integer> list : temp) {
                list.add(j);
                result.add(list);
            }
        }
        if (n > 0) {
            List<Integer> temp = new ArrayList<>();
            temp.add(n);
            result.add(temp);
        }
        return result;
    }

    private static List<List<Integer>> setInit(int n, int min) {
        List<List<Integer>> result = new ArrayList<>();
        for (int j = min; j <= n; j++) {
            for (int k = 1; k <= n / j; k++) {
                List<List<Integer>> temp = setInit(n - j * k, j + 1);
                for (List<Integer> list : temp) {
                    for (int t = 1; t <= k; t++) {
                        list.add(j);
                    }
                    result.add(list);
                }
                if (k * j == n) {
                    List<Integer> temp2 = new ArrayList<>();
                    for (int t = 0; t < k; t++) {
                        temp2.add(j);
                    }
                    result.add(temp2);
                }
            }
        }
        return result;
    }

}
