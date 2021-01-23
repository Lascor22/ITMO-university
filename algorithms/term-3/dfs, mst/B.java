import java.util.*;

public class B {
    private static int currentTime = 0;
    private static HashSet<Integer> current = new HashSet<>();
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> graph = new ArrayList<>();
    private static ArrayList<Integer> times = new ArrayList<>();
    private static ArrayList<Boolean> marked = new ArrayList<>();
    private static ArrayList<Integer> ret = new ArrayList<>();
    private static Set<Integer> ans = new TreeSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        for (int i = 0; i <= n; i++) {
            ret.add(0);
            times.add(0);
            marked.add(false);
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i <= m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.get(from).add(new Pair<>(to, i));
            graph.get(to).add(new Pair<>(from, i));
        }

        for (int i = 1; i <= n; i++) {
            if (!marked.get(i)) {
                findBridges(i, -1);
            }
        }
        System.out.println(ans.size());
        for (int bridge : ans) {
            System.out.print(bridge + " ");
        }
    }

    private static void findBridges(int v, int p) {
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
                findBridges(to, v);
                ret.set(v, Math.min(ret.get(v), ret.get(to)));
                if (ret.get(to) > times.get(v)) {
                    ans.add(graph.get(v).get(i).getSecond());
                }
            }
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