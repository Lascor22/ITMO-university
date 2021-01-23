import java.util.Arrays;
import java.util.Scanner;

public class D {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        long[] cost = new long[n];
        for (int i = 0; i < n; i++) {
            cost[i] = in.nextLong();
        }
        long[][] graph1 = new long[n][n];
        long[][] g = new long[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(graph1[i], Integer.MAX_VALUE);
            Arrays.fill(g[i + 1], Integer.MAX_VALUE);
        }
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            graph1[from][to] = in.nextLong();
        }
        long[][] d = floid(graph1, n);

        for (int i = 0; i < n; i++) {
            g[i + 1][0] = 0;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    g[i + 1][j + 1] = cost[i];
                } else {
                    g[i + 1][j + 1] = d[i][j];
                }
            }
        }
        System.out.println(venger(n, g));
    }

    private static long venger(int n, long[][] graph) {
        long[] u = new long[n + 1];
        long[] v = new long[n + 1];
        int[] p = new int[n + 1];
        int[] way = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            p[0] = i;
            int j0 = 0;
            long[] minv = new long[n + 1];
            boolean[] used = new boolean[n + 1];
            Arrays.fill(minv, Integer.MAX_VALUE);
            Arrays.fill(used, false);
            do {
                used[j0] = true;
                int i0 = p[j0];
                long delta = Integer.MAX_VALUE;
                int j1 = 0;
                for (int j = 1; j <= n; j++) {
                    if (!used[j]) {
                        long cur = graph[i0][j] - u[i0] - v[j];
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
        long result = 0;
        for (int i = 1; i <= n; i++) {
            result += graph[i][ans[i]];
        }
        return result;
    }

    private static long[][] floid(long[][] graph, int n) {
        long[][] d = new long[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(graph[i], 0, d[i], 0, n);
        }

        for (int i = 0; i < n; i++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (d[u][i] < d[u][v] - d[i][v]) {
                        d[u][v] = d[u][i] + d[i][v];
                    }
                }
            }
        }
        return d;
    }
}
