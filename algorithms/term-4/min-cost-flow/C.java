import java.util.*;

public class C {
    private static List<List<Edge>> graph;
    public static void main(String[] args) {
        initialize();
        System.out.println(minCostFlow(8));

    }
    private static void initialize() {
        Scanner in = new Scanner(System.in);
        int r1 = in.nextInt();
        int s1 = in.nextInt();
        int p1 = in.nextInt();
        int r2 = in.nextInt();
        int s2 = in.nextInt();
        int p2 = in.nextInt();
        graph = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            graph.add(new ArrayList<>());
        }
        addEdge(0, 1, r2, 0);
        addEdge(0, 2, s2, 0);
        addEdge(0, 3, p2, 0);
        addEdge(1, 4, Integer.MAX_VALUE, 0);
        addEdge(1, 5, Integer.MAX_VALUE, 0);
        addEdge(1, 6, Integer.MAX_VALUE, 1);
        addEdge(2, 4, Integer.MAX_VALUE, 1);
        addEdge(2, 5, Integer.MAX_VALUE, 0);
        addEdge(2, 6, Integer.MAX_VALUE, 0);
        addEdge(3, 4, Integer.MAX_VALUE, 0);
        addEdge(3, 5, Integer.MAX_VALUE, 1);
        addEdge(3, 6, Integer.MAX_VALUE, 0);
        addEdge(4, 7, r1, 0);
        addEdge(5, 7, s1, 0);
        addEdge(6, 7, p1, 0);
    }

    private static void addEdge(int from, int to, int cost, int weight) {
        Edge edge = new Edge(from, to, cost, weight, 0);
        Edge invEdge = new Edge(from, to, cost, -weight, cost);
        graph.get(from).add(edge);
        graph.get(to).add(invEdge);
        graph.get(from).get(graph.get(from).size() - 1).backEdge = graph.get(to).size() - 1;
        graph.get(to).get(graph.get(to).size() - 1).backEdge = graph.get(from).size() - 1;
    }

    private static long minCostFlow(int n) {
        long result = 0;
        while (true) {
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
        return result;
    }

    private static class Edge {
        Edge(int from, int to, long cost, long weight, long f) {
            this.from = from;
            this.to = to;
            this.cost = cost;
            this.weight = weight;
            this.f = f;
            isInv = f == cost;
        }

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
