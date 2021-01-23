import java.util.*;

public class D {
    private static int currentTime = 0;
    private static HashSet<Integer> current = new HashSet<>();
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> graph = new ArrayList<>();
    private static ArrayList<Integer> times = new ArrayList<>();
    private static ArrayList<Boolean> marked = new ArrayList<>();
    private static ArrayList<Integer> ret = new ArrayList<>();
    private static Set<Integer> bridges = new TreeSet<>();
    private static int cntVertex;
    private static int cntEdges;
    private static ArrayList<ArrayList<Integer>> graphNoBridges = new ArrayList<>();
    private static ArrayList<Integer> colorVertex = new ArrayList<>();
    private static int cntColor = 0;

    public static void main(String[] args) {
        graphInit();
        findBridges();
        deleteBridges();
        markedVertex();
        print();

    }

    private static void markedVertex() {
        for (int i = 0; i < marked.size(); i++) {
            marked.set(i, false);
        }
        for (int i = 1; i <= cntVertex; i++) {
            if (!marked.get(i)) {
                cntColor++;
                simpleDfs(i, cntColor);
            }
        }
    }

    private static void simpleDfs(int v, int color) {
        if (!marked.get(v)) {
            marked.set(v, true);
            colorVertex.set(v, color);
            for (int i = 0; i < graphNoBridges.get(v).size(); i++) {
                simpleDfs(graphNoBridges.get(v).get(i), color);
            }
        }
    }

    private static void print() {
        System.out.println(cntColor);
        for (int i = 1; i < colorVertex.size(); i++) {
            System.out.print(colorVertex.get(i) + " ");
        }
    }

    private static void findBridges() {
        for (int i = 1; i <= cntVertex; i++) {
            if (!marked.get(i)) {
                dfs(i, -1);
            }
        }
    }

    private static void deleteBridges() {
        graphNoBridges.add(new ArrayList<>());
        for (int i = 1; i < graph.size(); i++) {
            graphNoBridges.add(new ArrayList<>());
            for (int j = 0; j < graph.get(i).size(); j++) {
                if (!bridges.contains(graph.get(i).get(j).getSecond())) {
                    graphNoBridges.get(i).add(graph.get(i).get(j).getFirst());
                }
            }
        }

    }

    private static void dfs(int v, int p) {
        marked.set(v, true);
        currentTime++;
        times.set(v, currentTime);
        ret.set(v, currentTime);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i).getFirst();
            if (to == p) {
                continue;
            }
            if (marked.get(to)) {
                ret.set(v, Math.min(ret.get(v), times.get(to)));
            } else {
                dfs(to, v);
                ret.set(v, Math.min(ret.get(v), ret.get(to)));
                if (ret.get(to) > times.get(v)) {
                    bridges.add(graph.get(v).get(i).getSecond());
                }
            }
        }
    }

    private static void graphInit() {
        Scanner scanner = new Scanner(System.in);
        cntVertex = scanner.nextInt();
        cntEdges = scanner.nextInt();
        for (int i = 0; i <= cntVertex; i++) {
            ret.add(0);
            times.add(0);
            marked.add(false);
            graph.add(new ArrayList<>());
            colorVertex.add(0);
        }
        for (int i = 1; i <= cntEdges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.get(from).add(new Pair<>(to, i));
            graph.get(to).add(new Pair<>(from, i));
        }


    }

    private static class Pair<T, V> {
        private T first;
        private V second;

        Pair(T first, V second) {
            this.first = first;
            this.second = second;
        }

        T getFirst() {
            return first;
        }

        V getSecond() {
            return second;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public void setSecond(V second) {
            this.second = second;
        }
    }
}