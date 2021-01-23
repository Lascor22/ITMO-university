import java.util.*;
import java.util.stream.Collectors;

public class E {
    private static List<List<Edge>> graph;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        graph = new ArrayList<>();
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            long weight = in.nextLong();
            addEdge(i, from, to, weight);
            addEdge(i, to, from, weight);
        }

        long res = minCostFlow(n, k);

        if (res == -1) {
            System.out.println(-1);
            return;
        }

        System.out.println(String.format("%.5f", ((double) res) / k));

        boolean[] used = new boolean[m];
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            dfs(0, used, ans);
        }
    }

    private static void addEdge(int i, int from, int to, long weight) {
        Edge edge = new Edge(i, from, to, 1, weight, 0);
        Edge invEdge = new Edge(i, from, to, 1, -weight, 1);
        graph.get(from).add(edge);
        graph.get(to).add(invEdge);
        graph.get(from).get(graph.get(from).size() - 1).backEdge = graph.get(to).size() - 1;
        graph.get(to).get(graph.get(to).size() - 1).backEdge = graph.get(from).size() - 1;
    }

    private static boolean dfs(int v, boolean[] used, List<Integer> ans) {
        if (v == graph.size() - 1) {
            print(ans);
            return true;
        }
        for (Edge e : graph.get(v)) {
            if (!used[e.ind] && e.f == 1 && !e.isInv) {
                used[e.ind] = true;
                ans.add(e.ind);
                return dfs(e.to, used, ans);
            }
        }
        return false;
    }

    private static void print(List<Integer> ans) {
        System.out.print(ans.size() + " ");
        System.out.println(ans.stream().map(i -> Integer.toString(i + 1)).collect(Collectors.joining(" ")));
        ans.clear();
    }

    private static long minCostFlow(int n, int k) {
        long result = 0;
        int flow = 0;
        for (; flow < k; flow++) {
            PriorityQueue<Pair> q = new PriorityQueue<>();
            long[] d = new long[n];
            Edge[] es = new Edge[n];
            Arrays.fill(d, Long.MAX_VALUE);
            d[0] = 0;
            q.add(new Pair(0, 0));
            while (!q.isEmpty()) {
                int v = q.poll().y;
                for (int i = 0; i < graph.get(v).size(); i++) {
                    Edge e = graph.get(v).get(i);
                    int u = (e.isInv) ? e.from : e.to;
                    if ((d[v] + e.weight < d[u]) && (e.cost - e.f > 0)) {
                        q.remove(new Pair(d[u], u));
                        d[u] = d[v] + e.weight;
                        es[u] = e;
                        q.add(new Pair(d[u], u));
                    }
                }
            }
            if (d[n - 1] == Long.MAX_VALUE) {
                break;
            }
            List<Edge> ans = new ArrayList<>();
            long maxFlow = Long.MAX_VALUE;
            int curr = n - 1;
            while (curr != 0) {
                Edge e = es[curr];
                maxFlow = Math.min(e.cost - e.f, maxFlow);
                ans.add(e);
                curr = e.isInv ? e.to : e.from;
            }
            for (Edge e : ans) {
                e.f += maxFlow;
                graph.get(e.isInv ? e.from : e.to).get(e.backEdge).f -= maxFlow;
            }
            result += d[n - 1] * maxFlow;
        }
        if (flow >= k) {
            return result;
        }
        return -1;
    }

    private static class Edge {
        Edge(int ind, int from, int to, long cost, long weight, long f) {
            this.ind = ind;
            this.from = from;
            this.to = to;
            this.cost = cost;
            this.weight = weight;
            this.f = f;
            isInv = f == cost;
        }

        int ind;
        int from;
        int to;
        long cost;
        long weight;
        long f;
        int backEdge;
        boolean isInv;
    }

    private static class Pair implements Comparable<Pair> {
        long x;
        int y;

        Pair(long x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return x == pair.x &&
                    y == pair.y;
        }

        @Override
        public int compareTo(Pair p) {
            if (equals(p)) {
                return 0;
            }
            if (x < p.x && y < p.y) {
                return -1;
            }
            if (x == p.x) {
                if (y < p.y) {
                    return -1;
                }
            }
            return 1;
        }
    }
}
