import java.util.*;

public class E {
    private static int n;
    private static int k;
    private static int s;
    private static int t;
    private static int result;
    private static List<List<Edge>> graph;
    private static List<List<Integer>> adjGraph;
    private final static int inf = 100_000;

    public static void main(String[] args) {
        initializeAndRead();
        solveTask();
        createAndPrintAnswer();
    }

    private static void initializeAndRead() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int m = in.nextInt();
        k = in.nextInt();
        s = in.nextInt() - 1;
        t = in.nextInt() - 1;
        adjGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            adjGraph.get(from).add(to);
            adjGraph.get(to).add(from);
        }
    }

    private static void solveTask() {
        int length = bfs();
        result = length - 1;
        int size = n * (length + 1 + k);

        graph = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < length + k; i++) {
            for (int v = 0; v < n; v++) {
                if (v != s || v != t) {
                    addEdges(v + i * n, v + (i + 1) * n, inf);
                    for (int j = 0; j < adjGraph.get(v).size(); j++) {
                        addEdges(v + i * n, adjGraph.get(v).get(j) + (i + 1) * n, 1);
                    }
                }
            }
        }
        int curr = 0;
        while (k > flow(t + (length + curr) * n, size)) {
            result = length + curr;
            curr++;
        }
        result++;

        List<Boolean> used = new ArrayList<>(Collections.nCopies(size, false));
        while (findFlow(0, inf, t + result * n, used) > 0) {
            used = new ArrayList<>(Collections.nCopies(size, false));
        }
    }

    private static void createAndPrintAnswer() {
        int index = k;
        List<List<Pair>> answer = new ArrayList<>();
        for (int i = 0; i < result; i++) {
            answer.add(new ArrayList<>());
        }

        List<List<Integer>> sheep = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            sheep.add(new ArrayList<>());
        }

        System.out.println(result);
        for (int l = 0; l < result; l++) {
            List<List<Integer>> nSheep = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                nSheep.add(new ArrayList<>());
            }
            for (int i = 0; i < n; i++) {
                int x = i + l * n;
                for (int j = 0; j < graph.get(x).size(); j++) {
                    List<Integer> currList = sheep.get(i);
                    Edge e = graph.get(x).get(j);
                    while (e.f > 0 && e.to > x) {
                        if (!currList.isEmpty()) {
                            e.f = e.f - 1;
                            if ((e.to % n) != i) {
                                answer.get(l).add(new Pair(currList.get(currList.size() - 1), (e.to % n) + 1));
                            }
                            nSheep.get(e.to % n).add(currList.get(currList.size() - 1));
                            currList.remove(currList.size() - 1);
                        } else {
                            e.f = e.f - 1;
                            if (index <= 0) {
                                continue;
                            }
                            nSheep.get(e.to % n).add(index--);
                            if ((e.to % n) != i) {
                                answer.get(l).add(new Pair(index + 1, (e.to % n) + 1));
                            }
                        }
                    }
                }
            }
            sheep = nSheep;
        }

        for (List<Pair> pairs : answer) {
            System.out.print(pairs.size());
            for (Pair pair : pairs) {
                System.out.print("  " + pair.fst + " " + pair.snd);
            }
            System.out.println();
        }

    }

    private static int bfs() {
        List<Boolean> flags = new ArrayList<>(Collections.nCopies(n, false));
        Queue<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(s, 0));
        while (!queue.isEmpty()) {
            Pair p = queue.remove();
            flags.set(p.fst, true);
            if (p.fst == t) {
                return p.snd;
            }
            for (int i = 0; i < adjGraph.get(p.fst).size(); i++) {
                if (!flags.get(adjGraph.get(p.fst).get(i))) {
                    queue.add(new Pair(adjGraph.get(p.fst).get(i), p.snd + 1));
                }
            }
        }
        return 0;
    }

    private static void addEdges(int from, int to, int c) {
        Edge e = new Edge(from, to, c, 0);
        Edge eInv = new Edge(from, to, c, c);
        graph.get(e.from).add(e);
        graph.get(e.to).add(eInv);
        graph.get(e.from).get(graph.get(e.from).size() - 1).backEdge = graph.get(e.to).size() - 1;
        graph.get(e.to).get(graph.get(e.to).size() - 1).backEdge = graph.get(e.from).size() - 1;
    }

    private static int flow(int terminal, int size) {
        List<Boolean> used = new ArrayList<>(Collections.nCopies(size, false));
        List<List<Edge>> copy = new ArrayList<>(Collections.nCopies(size, null));
        Collections.copy(copy, graph);
        int ans = 0;
        while (findFlow(s, inf, terminal, used) > 0) {
            used = new ArrayList<>(Collections.nCopies(size, false));
        }

        for (int i = 0; i < graph.get(s).size(); i++) {
            ans += graph.get(s).get(i).f;
        }
        graph = null;
        graph = copy;
        return ans;
    }

    private static int findFlow(int v, int curr, int terminal, List<Boolean> used) {
        if (v == terminal) {
            return curr;
        }

        if (v / n >= terminal / n) {
            return 0;
        }
        used.set(v, true);

        for (Edge e : graph.get(v)) {
            int to = (e.to == v) ? e.from : e.to;
            if (!used.get(to) && e.f < e.c) {
                int delta = findFlow(to, Math.min(curr, e.c - e.f), terminal, used);
                if (delta > 0) {
                    e.f += delta;
                    graph.get(to).get(e.backEdge).f -= delta;
                    return delta;
                }
            }
        }
        return 0;
    }

    private static class Edge {
        int from;
        int to;
        int c;
        int f;
        int backEdge;

        Edge(int from, int to, int c, int f) {
            this.from = from;
            this.to = to;
            this.f = f;
            this.c = c;
        }
    }

    private static class Pair {
        int fst;
        int snd;

        Pair(int fst, int snd) {
            this.fst = fst;
            this.snd = snd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair pair = (Pair) o;
            return fst == pair.fst &&
                    snd == pair.snd;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fst, snd);
        }
    }

}
