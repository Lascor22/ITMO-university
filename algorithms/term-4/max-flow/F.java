import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class F {
    private static int n;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        List<List<Edge>> graph = new ArrayList<>();
        List<List<Character>> f = new ArrayList<>();
        List<Integer> s = new ArrayList<>(Collections.nCopies(n, 0));
        initializeAndRead(in, graph, f, s);
        solveTask(graph, f);
    }

    private static void initializeAndRead(Scanner in, List<List<Edge>> graph,
                                          List<List<Character>> f, List<Integer> s) {
        for (int i = 0; i < n * n + n + 2; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            f.add(new ArrayList<>(Collections.nCopies(n, (char) 0)));
        }


        for (int i = 0; i < n; i++) {
            char[] chars = in.next().toCharArray();
            for (int j = 0; j < n; j++) {
                f.get(i).set(j, chars[j]);
                if (i == j) {
                    continue;
                }
                if (f.get(i).get(j) == 'W') {
                    s.set(i, s.get(i) - 3);
                }
                if (f.get(i).get(j) == 'w') {
                    s.set(i, s.get(i) - 2);
                }
                if (f.get(i).get(j) == 'l') {
                    s.set(i, s.get(i) - 1);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            s.set(i, s.get(i) + in.nextInt());
        }

        for (int i = 1; i <= n * n; i++) {
            addEdges(graph, 0, i, 3);
        }

        for (int i = n * n + 1; i <= n * n + n; i++) {
            addEdges(graph, i, n * n + n + 1, s.get(i - n * n - 1));
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (f.get(i).get(j) != '.') {
                    continue;
                }
                addEdges(graph, i * n + j + 1, n * n + i + 1, 3);
                addEdges(graph, i * n + j + 1, n * n + j + 1, 3);
            }
        }
    }

    private static void addEdges(List<? extends List<Edge>> graph, int from, int to, int c) {
        Edge e = new Edge(from, to, c, 0);
        Edge eInv = new Edge(from, to, c, c);
        graph.get(e.from).add(e);
        graph.get(e.to).add(eInv);
        graph.get(e.from).get(graph.get(e.from).size() - 1).backEdge = graph.get(e.to).size() - 1;
        graph.get(e.to).get(graph.get(e.to).size() - 1).backEdge = graph.get(e.from).size() - 1;
    }

    private static void solveTask(List<List<Edge>> graph, List<List<Character>> f) {
        List<Boolean> used = new ArrayList<>(Collections.nCopies(n * n + n + 2, false));
        while (findFlow(0, Integer.MAX_VALUE, graph, used) > 0) {
            used = new ArrayList<>(Collections.nCopies(n * n + n + 2, false));
        }

        for (int i = 1; i <= n * n; ++i) {
            for (int j = 0; j < graph.get(i).size(); ++j) {
                int x = (i - 1) / n;
                int y = (i - 1) % n;
                if (graph.get(i).get(j).to != n * n + x + 1) {
                    continue;
                }
                if (graph.get(i).get(j).f == 0) {
                    f.get(x).set(y, 'L');
                    f.get(y).set(x, 'W');
                }
                if (graph.get(i).get(j).f == 1) {
                    f.get(x).set(y, 'l');
                    f.get(y).set(x, 'w');
                }
                if (graph.get(i).get(j).f == 2) {
                    f.get(x).set(y, 'w');
                    f.get(y).set(x, 'l');
                }
                if (graph.get(i).get(j).f == 3) {
                    f.get(x).set(y, 'W');
                    f.get(y).set(x, 'L');
                }
            }

        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(f.get(i).get(j));
            }
            System.out.println();
        }
    }

    private static int findFlow(int v, int curr, List<List<Edge>> graph, List<Boolean> used) {
        if (v == n * n + n + 1) {
            return curr;
        }
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            Edge e = graph.get(v).get(i);
            int to = (e.to == v) ? e.from : e.to;
            if (!used.get(to) && e.f < e.c) {
                int delta = findFlow(to, Math.min(curr, e.c - e.f), graph, used);
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

}
