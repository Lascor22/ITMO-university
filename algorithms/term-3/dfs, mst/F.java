import java.util.*;

public class F {
    private static List<List<Integer>> graph;
    private static List<List<Integer>> reverceGraph;
    private static int cntVertex;
    private static List<Boolean> used;
    private static List<Integer> ord;
    private static List<Integer> component;
    private static List<TreeSet<Integer>> graphCond;
    private static int cntComp;


    public static void main(String[] args) {
        initGraph();
        find();
        initGraphCond();
    }


    private static void initGraph() {
        graph = new ArrayList<>();
        reverceGraph = new ArrayList<>();
        used = new ArrayList<>();
        ord = new ArrayList<>();
        component = new ArrayList<>();
        graphCond = new ArrayList<>();
        cntComp = 0;

        Scanner scanner = new Scanner(System.in);
        cntVertex = scanner.nextInt();
        int cntEdges = scanner.nextInt();

        for (int i = 0; i <= cntVertex; i++) {
            graph.add(new ArrayList<>());
            reverceGraph.add(new ArrayList<>());
            used.add(false);
            component.add(-1);
        }

        for (int i = 0; i < cntEdges; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.get(from).add(to);
            reverceGraph.get(to).add(from);
        }
    }

    private static void find() {
        for (int i = 1; i <= cntVertex; i++) {
            if (!used.get(i)) {
                dfs1(i);
            }
        }
        Collections.reverse(ord);
        for (int i = 0; i < ord.size(); i++) {
            if (component.get(ord.get(i)) == -1) {
                dfs2(ord.get(i));
                cntComp++;
            }
        }
    }

    private static void dfs1(int v) {
        used.set(v, true);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (!used.get(to)) {
                dfs1(to);
            }
        }
        ord.add(v);
    }

    private static void dfs2(int v) {
        component.set(v, cntComp);
        for (int i = 0; i < reverceGraph.get(v).size(); i++) {
            if (component.get(reverceGraph.get(v).get(i)) == -1) {
                dfs2(reverceGraph.get(v).get(i));
            }
        }
    }

    private static void initGraphCond() {
        for (int i = 0; i < cntComp; i++) {
            graphCond.add(new TreeSet<>());
        }
        for (int i = 1; i < component.size(); i++) {
            int currComp = component.get(i);
            for (int j = 0; j < graph.get(i).size(); j++) {
                int to = graph.get(i).get(j);
                if (component.get(to) != currComp) {
                    graphCond.get(currComp).add(component.get(to));
                }
            }
        }
        int cnt = 0;
        for (int i = 0; i < graphCond.size(); i++) {
            cnt += graphCond.get(i).size();
        }
        System.out.println(cnt);
    }

}