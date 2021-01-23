import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class H {
    private static final long MODULE = 998_244_353;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        int n = in.nextInt();
        List<Long> left = new ArrayList<>();
        List<Long> right = new ArrayList<>();
        long[][] chooses = new long[k + 1][k + 1];
        for (int i = 0; i <= k; i++) {
            chooses[i][0] = 1;
            chooses[i][i] = 1;
            for (int j = 1; j < i; j++) {
                chooses[i][j] = (chooses[i - 1][j - 1] + chooses[i - 1][j]) % MODULE;
                if (chooses[i][j] < 0) {
                    chooses[i][j] += MODULE;
                }
            }
        }
        for (int i = 0; 2 * i < k - 1; i++) {
            left.add(chooses[k - i - 2][i] * (i % 2 == 0 ? 1 : -1));
        }
        for (int i = 0; 2 * i < k; i++) {
            right.add(chooses[k - i - 1][i] * (i % 2 == 0 ? 1 : -1));
        }

        long[] b = new long[n + 1];
        b[0] = (1 / at(right, 0));
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                b[i] = (b[i] - ((b[j] * at(right, i - j)) % MODULE)) % MODULE;
                if (b[i] < 0) {
                    b[i] += MODULE;
                }
            }
            b[i] /= at(right, 0);
        }

        for (int i = 0; i < n; i++) {
            long result = 0;
            for (int j = 0; j <= i; j++) {
                result = (result + ((at(left, j) * at(b, i - j)) % MODULE)) % MODULE;
                if (result < 0) {
                    result += MODULE;
                }
            }
            System.out.println(result);
        }
    }

    private static long at(List<Long> list, int ind) {
        if (list.size() <= ind) {
            return 0;
        }
        return list.get(ind);
    }

    private static long at(long[] array, int ind) {
        if (array.length <= ind) {
            return 0;
        }
        return array[ind];
    }
}
