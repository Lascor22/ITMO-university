import java.util.Scanner;
import java.util.ArrayList;

public class Reverse {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
        while (scan.hasNextLine()) {
            String stTemp = scan.nextLine();
            ArrayList<Integer> numbersTemp = new ArrayList<Integer>();
            String[] arrayTemp = stTemp.split(" ");
            for (int i = 0; i < arrayTemp.length; i++) {
                if (!arrayTemp[i].equals("")) {
                    numbersTemp.add(Integer.parseInt(arrayTemp[i]));
                }
            }
            numbers.add(numbersTemp);
        }
        for (int i = numbers.size() - 1; i >= 0; i--) {
            for (int j = numbers.get(i).size() - 1; j >= 0; j--) {
                System.out.print(numbers.get(i).get(j) + " ");

            }
            System.out.println();
        }
    }
}
/* 
    scan.hasNextLine()
    numbers.size() != 3
*/