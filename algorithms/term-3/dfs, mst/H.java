import java.io.*;
import java.util.*;

public class H {
    private static List<List<Pair<Integer, Integer>>> graph;
    private static List<List<Pair<Integer, Integer>>> reversedGraph;
    private static int cntVertex;
    private static List<Boolean> used;
    private static List<Boolean> usedReverse;
    private static int answer;

    public static void main(String[] args) throws FileNotFoundException {
        initGraph();
        binarySearch();
        PrintWriter out = new PrintWriter(new FileOutputStream(new File("avia.out")));
        out.print(answer);
        out.close();
    }

    private static void initGraph() throws FileNotFoundException {
        InputReader scanner = new InputReader(new FileInputStream(new File("avia.in")));
        cntVertex = scanner.nextInt();
        used = new ArrayList<>();
        usedReverse = new ArrayList<>();
        graph = new ArrayList<>();
        reversedGraph = new ArrayList<>();

        for (int i = 0; i < cntVertex; i++) {
            used.add(false);
            usedReverse.add(false);
            graph.add(new ArrayList<>());
            reversedGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < cntVertex; i++) {
            for (int j = 0; j < cntVertex; j++) {
                int weight = scanner.nextInt();
                graph.get(i).add(new Pair<>(j, weight));
                reversedGraph.get(j).add(new Pair<>(i, weight));
            }
        }
    }

    private static void binarySearch() {
        int l = -1, r = 1000000010;
        while (r - l > 1) {
            int m = (l + r) / 2;
            for (int i = 0; i < cntVertex; i++) {
                usedReverse.set(i, false);
                used.set(i, false);
            }
            int colorGraph = 0, colorReversedGraph = 0;
            for (int i = 0; i < cntVertex; i++) {
                if (!used.get(i)) {
                    colorGraph++;
                    dfs(i, m);
                }
                if (!usedReverse.get(i)) {
                    colorReversedGraph++;
                    dfsReverse(i, m);
                }
            }


            if (colorGraph == 1 && colorReversedGraph == 1) {
                r = m;
            } else {
                l = m;
            }

        }
        answer = r;
    }

    private static void dfs(int v, int weight) {
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (!used.get(i) && graph.get(v).get(i).getSecond() <= weight) {
                dfs(graph.get(v).get(i).getFirst(), weight);
            }
        }
    }

    private static void dfsReverse(int v, int weight) {
        usedReverse.set(v, true);
        for (int i = 0; i < reversedGraph.get(v).size(); i++) {
            if (!usedReverse.get(i) && reversedGraph.get(v).get(i).getSecond() <= weight) {
                dfsReverse(reversedGraph.get(v).get(i).getFirst(), weight);
            }
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
