import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class D {
    private static int n, m, s, t;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        List<List<Edge>> graph1 = new ArrayList<>();
        List<List<Integer>> graph2 = new ArrayList<>();
        List<List<Character>> p = new ArrayList<>();
        initializeAndRead(in, graph1, graph2, p);
        prepareGraph(graph1, graph2, p);
        solveTask(graph1, p);
    }

    private static void initializeAndRead(Scanner in, List<List<Edge>> graph1, List<List<Integer>> graph2, List<List<Character>> p) {
        for (int i = 0; i < 2 * n * m; i++) {
            graph1.add(new ArrayList<>());
        }
        for (int i = 0; i < n * m; i++) {
            graph2.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            p.add(new ArrayList<>(Collections.nCopies(m, null)));
        }
        for (int i = 0; i < n; i++) {
            char[] chars = in.next().toCharArray();
            for (int j = 0; j < m; j++) {
                p.get(i).set(j, chars[j]);
                if (p.get(i).get(j) == 'A') {
                    s = (i * m + j) * 2 + 1;
                }
                if (p.get(i).get(j) == 'B') {
                    t = (i * m + j) * 2;
                }
                if (p.get(i).get(j) == '.') {
                    addEdges(graph1, 2 * (i * m + j), 2 * (i * m + j) + 1, 1, 1);
                }
                if (p.get(i).get(j) == '-' || p.get(i).get(j) == 'A' || p.get(i).get(j) == 'B') {
                    addEdges(graph1, 2 * (i * m + j), 2 * (i * m + j) + 1, 10000, 0);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                add(graph2, p, i, j, i + 1, j);
                add(graph2, p, i, j, i, j + 1);
                add(graph2, p, i, j, i, j - 1);
                add(graph2, p, i, j, i - 1, j);
            }
        }
    }

    private static boolean ch(int x, int y) {
        return x < 0 || y < 0 || x >= n || y >= m;
    }

    private static void add(List<List<Integer>> graph2, List<List<Character>> p, int x, int y, int x1, int y1) {
        if (ch(x, y) || ch(x1, y1) || p.get(x).get(y) == '#') {
            return;
        }
        graph2.get(x * m + y).add(x1 * m + y1);
    }

    private static void prepareGraph(List<List<Edge>> graph1, List<List<Integer>> graph2, List<List<Character>> p) {
        for (int i = 0; i < graph2.size(); ++i) {
            for (int j = 0; j < graph2.get(i).size(); ++j) {
                if (p.get(i / m).get(i % m) == '#') {
                    continue;
                }
                if (p.get(i / m).get(i % m) == '-' || p.get(i / m).get(i % m) == 'A' || p.get(i / m).get(i % m) == 'B') {
                    addEdges(graph1, i * 2 + 1, graph2.get(i).get(j) * 2, 10000, 10000);
                }

                if (p.get(i / m).get(i % m) == '.') {
                    addEdges(graph1, i * 2 + 1, graph2.get(i).get(j) * 2, 1, 1);
                }
            }
        }
    }

    private static void addEdges(List<List<Edge>> graph1, int from, int to, int c, int fInv) {
        Edge e = new Edge(from, to, c, 0);
        Edge eInv = new Edge(from, to, c, fInv);
        graph1.get(e.from).add(e);
        graph1.get(e.to).add(eInv);
        graph1.get(e.from).get(graph1.get(e.from).size() - 1).backEdge = graph1.get(e.to).size() - 1;
        graph1.get(e.to).get(graph1.get(e.to).size() - 1).backEdge = graph1.get(e.from).size() - 1;
    }

    private static void solveTask(List<List<Edge>> graph1, List<List<Character>> p) {
        List<Boolean> used = new ArrayList<>(Collections.nCopies(n * m * 2, false));
        while (findFlow(s, Integer.MAX_VALUE, graph1, used) > 0) {
            used = used.stream().map(b -> false).collect(Collectors.toList());
        }
        int r = 0;
        for (int i = 0; i < graph1.get(s).size(); i++) {
            if (graph1.get(s).get(i).to != s) {
                r += graph1.get(s).get(i).f;
            }
        }
        if (r >= 10000) {
            System.out.println(-1);
            return;
        }

        int a = 0;

        for (int i = 0; i < graph1.size(); i++) {
            if (used.get(i)) {
                for (int j = 0; j < graph1.get(i).size(); j++) {
                    if (graph1.get(i).get(j).from % 2 == 0 &&
                            graph1.get(i).get(j).from + 1 == graph1.get(i).get(j).to &&
                            (Math.abs(graph1.get(i).get(j).f) == graph1.get(i).get(j).c) &&
                            (used.get(graph1.get(i).get(j).from) ^ used.get(graph1.get(i).get(j).to))) {
                        p.get(i / (2 * m)).set((i / 2) % m, '+');
                        a++;
                    }
                }
            }
        }
        System.out.println(a);
        for (List<Character> lc : p) {
            for (Character c : lc) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private static int findFlow(int v, int curr, List<List<Edge>> graph1, List<Boolean> used) {
        if (v == t) {
            return curr;
        }
        used.set(v, true);
        for (int i = 0; i < graph1.get(v).size(); i++) {
            Edge e = graph1.get(v).get(i);
            int to = (e.to == v) ? e.from : e.to;
            if (!used.get(to) && e.f < e.c) {
                int delta = findFlow(to, Math.min(curr, e.c - e.f), graph1, used);
                if (delta > 0) {
                    e.f += delta;
                    graph1.get(to).get(e.backEdge).f -= delta;
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

}
