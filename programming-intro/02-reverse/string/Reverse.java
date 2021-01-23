import java.util.Scanner;
import java.util.ArrayList;

public class Reverse {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> numbers = new ArrayList<String>();
        while (scan.hasNextLine()) {
            numbers.add(scan.nextLine());
        }
        for (int i = numbers.size() - 1; i >= 0; i--) {
            String[] arrayTemp = numbers.get(i).split(" ");
            for (int j = arrayTemp.length - 1; j >= 0; j--) {
                System.out.print(arrayTemp[j] + " ");
            }
            System.out.println();
        }
    }
}
/* 
    scan.hasNextLine()
    numbers.size() != 3
*/