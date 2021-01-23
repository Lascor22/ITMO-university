import java.util.*;

public class J {
    private static ArrayList<Pair<Integer, Pair<Integer, Integer>>> edges;
    private static ArrayList<Integer> dsuParents;

    public static void main(String[] args) {
        initGraph();
        System.out.println(mstWeight());
    }

    private static void initGraph() {
        Scanner scanner = new Scanner(System.in);

        edges = new ArrayList<>();
        dsuParents = new ArrayList<>();

        int cntVertex = scanner.nextInt();
        int cntEdges = scanner.nextInt();

        for (int i = 0; i <= cntVertex; i++) {
            dsuParents.add(i);
        }

        for (int i = 0; i < cntEdges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int weight = scanner.nextInt();
            edges.add(new Pair<>(weight, new Pair<>(from, to)));
        }
    }

    private static long mstWeight() {
        long answer = 0;
        edges.sort(Comparator.comparing(Pair::getFirst));
        for (Pair<Integer, Pair<Integer, Integer>> edge : edges) {
            int v = edge.getSecond().getFirst();
            int u = edge.getSecond().getSecond();
            int weight = edge.getFirst();
            if (dsuGet(v) != (dsuGet(u))) {
                answer += weight;
                dsuUnite(v, u);
            }
        }
        return answer;
    }

    private static int dsuGet(int v) {
        if (v == dsuParents.get(v)) {
            return v;
        }
        int temp = dsuGet(dsuParents.get(v));
        dsuParents.set(v, temp);
        return temp;
    }

    private static void dsuUnite(int v, int u) {
        v = dsuGet(v);
        u = dsuGet(u);
        if (v != u) {
            dsuParents.set(v, u);
        }
    }

    private static class Pair<T, V> {
        private T first;
        private V second;

        Pair(T first, V second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(first, pair.first) &&
                    Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
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
