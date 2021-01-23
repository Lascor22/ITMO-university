import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scan.nextInt();
        }
        for (int i = a.length / 2; i >= 0; i--) {
            siftDown(a, i, n);
        }
        int[] res = new int[n];
        int size = n;
        for (int i = 0; i < n; i++) {
            res[i] = extractMin(a, size);
            size--;
        }
        for (int temp : res) {
            System.out.print(temp + " ");
        }

    }

    public static void siftDown(int[] a, int i, int n) {
        while (2 * i + 1 < n) {
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int j = l;
            if (r < n && a[r] < a[l]) {
                j = r;
            }
            if (a[i] <= a[j]) {
                break;
            }
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            i = j;
        }
    }

    public static int extractMin(int[] a, int n) {
        int min = a[0];
        a[0] = a[n - 1];
        siftDown(a, 0, n - 1);
        return min;
    }
}