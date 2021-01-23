import java.util.*;

public class G {
    private static int cntVertex;
    private static Map<Integer, String> IdNames;
    private static List<List<Integer>> graph;
    private static List<List<Integer>> reverseGraph;
    private static List<Boolean> used;
    private static List<Integer> timesOrder;
    private static List<Integer> component;
    private static int countComponent;
    private static boolean noSolution;


    public static void main(String[] args) {
        initGraph();
        find();
        solve2Sat();
    }

    private static void initGraph() {
        Scanner scanner = new Scanner(System.in);

        {
            graph = new ArrayList<>();
            reverseGraph = new ArrayList<>();
            IdNames = new HashMap<>();
            used = new ArrayList<>();
            timesOrder = new ArrayList<>();
            component = new ArrayList<>();
            countComponent = 0;
            noSolution = false;
        }

        cntVertex = scanner.nextInt();
        int cntEdges = scanner.nextInt();

        int id = 0;
        Map<String, Integer> namesId = new HashMap<>();

        for (int i = 0; i < cntVertex; i++) {
            for (int j = 0; j < 2; j++) {
                graph.add(new ArrayList<>());
                reverseGraph.add(new ArrayList<>());
                used.add(false);
                component.add(-1);
            }
            String name = scanner.next();
            namesId.put("+" + name, id);
            namesId.put("-" + name, id + 1);
            IdNames.put(id, name);
            id += 2;
        }

        for (int i = 0; i < cntEdges; i++) {
            String first = scanner.next();
            scanner.next();
            String second = scanner.next();
            graph.get(namesId.get(first)).add(namesId.get(second));
            reverseGraph.get(namesId.get(second)).add(namesId.get(first));

            int tempSecond = second.contains("-") ? namesId.get(second) - 1 : namesId.get(second) + 1;
            int tempFirst = first.contains("-") ? namesId.get(first) - 1 : namesId.get(first) + 1;

            graph.get(tempSecond).add(tempFirst);
            reverseGraph.get(tempFirst).add(tempSecond);
        }
    }

    private static void find() {
        for (int i = 0; i < cntVertex * 2; i++) {
            if (!used.get(i)) {
                dfs1(i);
            }
        }
        Collections.reverse(timesOrder);
        for (int integer : timesOrder) {
            if (component.get(integer) == -1) {
                dfs2(integer);
                countComponent++;
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
        timesOrder.add(v);
    }

    private static void dfs2(int v) {
        component.set(v, countComponent);
        for (int i = 0; i < reverseGraph.get(v).size(); i++) {
            if (component.get(reverseGraph.get(v).get(i)) == -1) {
                dfs2(reverseGraph.get(v).get(i));
            }
        }
    }

    private static void solve2Sat() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 2 * cntVertex; i= i + 2) {
            if (component.get(i) > component.get(i + 1)) {
                result.add(IdNames.get(i));
            } else if (component.get(i).equals(component.get(i + 1))) {
                noSolution = true;
            }
        }

        if (!noSolution) {
            System.out.println(result.size());
            for (String name : result) {
                System.out.println(name);
            }
        } else {
            System.out.println(-1);
        }
    }
}
