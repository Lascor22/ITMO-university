import java.util.ArrayList;
import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<>();
        int p = 0;
        while (scan.hasNextInt()) {
            arr.add(scan.nextInt());
            p++;
        }
        int[] a = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            a[i] = arr.get(i);
        }

        countSort(a);
    }

    private static void countSort(int[] a) {
        int[] c = new int[101];
        for (int i = 0; i < c.length; i++) {
            c[i] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            c[a[i]]++;
        }

        int pos = 0;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i]; j++) {
                System.out.print(i + " ");
            }
        }
    }
}
