import java.util.Scanner;

public class A {
    private static int[][] graph;
    private static int[][] answer;
    private static int cntVertex;

    public static void main(String[] args) {
        initGraph();
        floid();
        out();
    }

    private static void out() {
        for (int i = 0; i < cntVertex; i++) {
            for (int j = 0; j < cntVertex; j++) {
                System.out.print(answer[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void floid() {
        for (int k = 0; k < cntVertex; k++) {
            for (int i = 0; i < cntVertex; i++) {
                for (int j = 0; j < cntVertex; j++) {
                    answer[i][j] = Math.min(answer[i][j], answer[i][k] + answer[k][j]);
                }
            }
        }
    }

    private static void initGraph() {
        Scanner scanner = new Scanner(System.in);
        cntVertex = scanner.nextInt();
        graph = new int[cntVertex][cntVertex];
        answer = new int[cntVertex][cntVertex];
        for (int i = 0; i < cntVertex; i++) {
            for (int j = 0; j < cntVertex; j++) {
                graph[i][j] = scanner.nextInt();
                answer[i][j] = graph[i][j];
            }
        }
    }
}
