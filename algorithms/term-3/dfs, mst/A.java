import java.util.*;

public class A {
    private static int currentTime = 0;
    private static HashSet<Integer> current = new HashSet<>();
    private static boolean hasCycle = false;
    private static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static ArrayList<Pair<Integer, Integer>> times = new ArrayList<>();
    private static ArrayList<Boolean> marked = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        for (int i = 0; i <= n; i++) {
            times.add(new Pair<>(0, i));
            marked.add(false);
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.get(from).add(to);
        }
        for (int i = 1; i <= n; i++) {
            dfs(i);
        }

        times.sort(Comparator.comparing(Pair::getFirst));
        Collections.reverse(times);

        if (hasCycle) {
            System.out.println(-1);
        } else {
            for (Pair<Integer, Integer> time : times) {
                if (time.getSecond() == 0) {
                    continue;
                }
                System.out.print(time.getSecond() + " ");
            }
        }
    }

    private static void dfs(int v) {
        if (!marked.get(v)) {
            current.add(v);
            marked.set(v, true);
            for (int i = 0; i < graph.get(v).size(); i++) {
                if (current.contains(graph.get(v).get(i))) {
                    hasCycle = true;
                }
                dfs(graph.get(v).get(i));
            }
            times.set(v, new Pair<>(currentTime, v));
            currentTime++;
            current.remove(v);
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