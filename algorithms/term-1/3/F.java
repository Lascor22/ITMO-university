import java.util.Scanner;
import java.util.Stack;

import static java.lang.Integer.min;

public class F {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        n = in.nextInt();
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = in.nextInt();
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int i = 0; i < n + 1; ++i) {
            for (int j = 0; j < n + 1; ++j) {
                dp[i][j] = 923401;
            }
        }
        dp[0][0] = 0;

        for (int i = 1; i < n + 1; ++i) {
            for (int j = 0; j < n + 1; ++j) {
                if (c[i - 1] > 100) {
                    if (j > 0 && j < n)
                        dp[i][j] = min(dp[i - 1][j - 1] + c[i - 1], dp[i - 1][j + 1]);
                    else if (j < n)
                        dp[i][j] = dp[i - 1][j + 1];
                    else
                        dp[i][j] = dp[i - 1][j - 1] + c[i - 1];
                } else {
                    if (j < n)
                        dp[i][j] = min(dp[i - 1][j] + c[i - 1], dp[i - 1][j + 1]);
                    else
                        dp[i][j] = dp[i - 1][j] + c[i - 1];
                }
            }
        }

        int us = 923401;
        int left = -1;
        for (int j = 0; j < n + 1; ++j) {
            if (dp[n][j] <= us) {
                us = dp[n][j];
                left = j;
            }
        }
        Stack<Integer> res = new Stack<>();
        int j = left;
        int i = n;
        System.out.println(us);
        System.out.print(left + " ");
        while (i != 0) {
            if (dp[i][j] == dp[i - 1][j + 1]) {
                res.push(i);
                i--;
                j++;
            } else if (c[i - 1] > 100) {
                i--;
                j--;
            } else {
                i--;
            }
        }
        System.out.println(res.size());
        while (!res.empty()) {
            System.out.println(res.peek());
            res.pop();
        }
    }
}