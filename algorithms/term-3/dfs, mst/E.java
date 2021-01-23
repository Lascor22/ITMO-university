import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class E {
    private static int timer;
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> graph;
    private static int[] times;
    private static int[] colorEdges;
    private static boolean[] usedEdges;
    private static int cntVertex;
    private static int cntEdges;
    private static Stack<Integer> stack;
    private static int maxColor;
    private static Map<Integer, Integer> multiply;

    public static void main(String[] args) {
        graphInit();
        solve();
        print();
    }

    private static void graphInit() {

        timer = 0;
        maxColor = 0;
        graph = new ArrayList<>();

        stack = new Stack<>();
        multiply = new LinkedHashMap<>();
        Map<Pair<Integer, Integer>, Integer> edgesId = new LinkedHashMap<>();
        InputReader scanner = new InputReader(System.in);
//        Scanner scanner = new Scanner(System.in);
        cntVertex = scanner.nextInt();
        cntEdges = scanner.nextInt();

        times = new int[cntVertex + 1];
        colorEdges = new int[cntEdges + 1];
        usedEdges = new boolean[cntEdges + 1];

        for (int i = 0; i < cntVertex; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < cntEdges; i++) {
            int u = scanner.nextInt() - 1;
            int v = scanner.nextInt() - 1;
            if (!edgesId.containsKey(new Pair<>(u, v))) {
                edgesId.put(new Pair<>(u, v), i);
                edgesId.put(new Pair<>(v, u), i);
            } else {
                multiply.put(i, edgesId.get(new Pair<>(v, u)));
            }
            graph.get(u).add(new Pair<>(v, i));
            graph.get(v).add(new Pair<>(u, i));
        }
    }

    private static void solve() {
        for (int i = 0; i < cntVertex; i++) {
            if (times[i] == 0) {
                dfs(i, -1);
            }
        }
    }

    private static int dfs(int v, int parent) {
        timer++;
        times[v] = timer;
        int minTime = timer;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int u = graph.get(v).get(i).getFirst();
            int id = graph.get(v).get(i).getSecond();

            if (u != parent) {
                int t, currentSize = stack.size();
                if (!usedEdges[id]) {
                    stack.add(id);
                    usedEdges[id] = true;
                }
                if (times[u] == 0) {
                    t = dfs(u, v);
                    if (t >= times[v]) {
                        maxColor++;
                        while (stack.size() != currentSize) {
                            int edge = stack.peek();
                            colorEdges[edge] = maxColor;
                            stack.pop();
                        }
                    }
                } else {
                    t = times[u];
                }
                minTime = Math.min(minTime, t);
            }
        }
        return minTime;
    }

    private static void print() {
        System.out.println(maxColor);
        if (multiply.size() != 0) {
            for (int i = 0; i < cntEdges; i++) {
                int curr = colorEdges[i];
                if (multiply.containsKey(i)) {
                    curr = colorEdges[multiply.get(i)];
                }
                System.out.print(curr + " ");
            }
        } else {
            for (int i = 0; i < cntEdges; i++) {
                System.out.print(colorEdges[i] + " ");
            }
        }
    }

    private static class Pair<T, V> {
        private final T first;
        private final V second;

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
    }

    private static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;

        InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}