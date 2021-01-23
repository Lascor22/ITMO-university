import java.util.Arrays;
import java.util.Scanner;

public class B {
    private static int[][] graph;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        graph = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                graph[i][j] = in.nextInt();
            }
        }
        int[] u = new int[n + 1];
        int[] v = new int[n + 1];
        int[] p = new int[n + 1];
        int[] way = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            p[0] = i;
            int j0 = 0;
            int[] minv = new int[n + 1];
            boolean[] used = new boolean[n + 1];
            Arrays.fill(minv, Integer.MAX_VALUE);
            Arrays.fill(used, false);
            do {
                used[j0] = true;
                int i0 = p[j0];
                int delta = Integer.MAX_VALUE;
                int j1 = 0;
                for (int j = 1; j <= n; j++) {
                    if (!used[j]) {
                        int cur = graph[i0][j] - u[i0] - v[j];
                        if (cur < minv[j]) {
                            minv[j] = cur;
                            way[j] = j0;
                        }
                        if (minv[j] < delta) {
                            delta = minv[j];
                            j1 = j;
                        }
                    }
                }
                for (int j = 0; j <= n; ++j)
                    if (used[j]) {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    } else {
                        minv[j] -= delta;
                    }
                j0 = j1;
            } while (p[j0] != 0);
            do {
                int j1 = way[j0];
                p[j0] = p[j1];
                j0 = j1;
            } while (j0 != 0);
        }
        int[] ans = new int[n + 1];
        for (int j = 1; j <= n; j++) {
            ans[p[j]] = j;
        }
        int result = 0;
        for (int i = 1; i <= n; i++) {
            result += graph[i][ans[i]];
        }
        System.out.println(result);
        for (int i = 1; i <= n; i++) {
            System.out.println(i + " " + ans[i]);
        }
    }
}
