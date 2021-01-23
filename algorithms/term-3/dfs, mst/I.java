import java.util.*;

public class I {
    private static int cntVertex;
    private static List<Point> graph;

    public static void main(String[] args) {
        initGraph();
        MST();
    }

    private static void initGraph() {
        graph = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        cntVertex = scanner.nextInt();
        for (int i = 0; i < cntVertex; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            graph.add(new Point(x, y));
        }
    }

    private static void MST() {
        double weight = 0;
        ArrayList<Integer> closest = new ArrayList<>();
        Set<Integer> result = new TreeSet<>();
        Set<Integer> other = new TreeSet<>();
        result.add(0);
        closest.add(0);
        for (int i = 1; i < cntVertex; i++) {
            other.add(i);
            closest.add(0);
        }
        while (result.size() != cntVertex) {
            int currentMinVertex = -1;
            double currentMinEdge = Double.MAX_VALUE;
            for (int item : other) {
                if (Point.distance(graph.get(item), graph.get(closest.get(item))) < currentMinEdge) {
                    currentMinEdge = Point.distance(graph.get(item), graph.get(closest.get(item)));
                    currentMinVertex = item;
                }
            }
            other.remove(currentMinVertex);
            result.add(currentMinVertex);
            weight += currentMinEdge;
            for (int item : other) {
                if (Point.distance(graph.get(item), graph.get(closest.get(item))) >
                        Point.distance(graph.get(item), graph.get(currentMinVertex))) {
                    closest.set(item, currentMinVertex);
                }
            }
        }
        System.out.println(weight);
    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public static double distance(Point first, Point second) {
            return Math.sqrt((first.getX() - second.getX()) * (first.getX() - second.getX()) +
                    (first.getY() - second.getY()) * (first.getY() - second.getY()));
        }

    }

}
