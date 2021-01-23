import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt() - 1;
        int t = in.nextInt() - 1;

        List<List<Edge>> graph = new ArrayList<>();
        List<Boolean> used = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            used.add(false);

        }

        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            Edge e = new Edge(from, to, 0);
            Edge eInv = new Edge(from, to, 1);
            graph.get(from).add(e);
            graph.get(to).add(eInv);
            graph.get(from).get(graph.get(from).size() - 1).backEdge = graph.get(to).size() - 1;
            graph.get(to).get(graph.get(to).size() - 1).backEdge = graph.get(from).size() - 1;
        }

        while (findFlow(s, Integer.MAX_VALUE, t, graph, used) > 0) {
            used = used.stream().map(b -> false).collect(Collectors.toList());
        }
        used = used.stream().map(b -> false).collect(Collectors.toList());
        List<Integer> way1 = new ArrayList<>();
        List<Integer> way2 = new ArrayList<>();
        boolean f1 = findWay(s, t, graph, used, way1);
        used = used.stream().map(b -> false).collect(Collectors.toList());
        boolean f2 = findWay(s, t, graph, used, way2);
        if (f1 && f2) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
            return;
        }
        System.out.println(way1.stream().map(v -> v + 1).map(Objects::toString).collect(Collectors.joining(" ")));
        System.out.println(way2.stream().map(v -> v + 1).map(Objects::toString).collect(Collectors.joining(" ")));


    }

    private static int findFlow(int v, int maxFlow, int t, List<List<Edge>> graph, List<Boolean> used) {
        if (v == t) {
            return maxFlow;
        }
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            Edge e = graph.get(v).get(i);
            int to = -1;
            if (e.to == v) {
                to = e.from;
            } else {
                to = e.to;
            }
            if (!used.get(to) && e.f < 1) {
                int delta = findFlow(to, Math.min(maxFlow, 1 - e.f), t, graph, used);
                if (delta > 0) {
                    e.f += delta;
                    graph.get(to).get(e.backEdge).f -= delta;
                    return delta;
                }
            }
        }
        return 0;
    }

    private static boolean findWay(int v, int t, List<List<Edge>> graph, List<Boolean> used, List<Integer> result) {
        result.add(v);
        if (v == t) {
            return true;
        }
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (graph.get(v).get(i).to == v) {
                continue;
            }
            if (!used.get(graph.get(v).get(i).to) && (graph.get(v).get(i).f == 1) && findWay(graph.get(v).get(i).to, t, graph, used, result)) {
                graph.get(v).get(i).f = 0;
                return true;
            }
        }
        result.remove(result.get(result.size() - 1));
        return false;
    }

    private static class Edge {
        int from;
        int to;
        int f;
        int backEdge;

        Edge(int from, int to, int f) {
            this.from = from;
            this.to = to;
            this.f = f;
        }
    }

}
