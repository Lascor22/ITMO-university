import java.util.Scanner;
import java.util.ArrayList;

public class ReverseTranspose {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int maxSize = 0;
        ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
        while (scan.hasNextLine()) {
            String stTemp = scan.nextLine();
            ArrayList<Integer> numbersTemp = new ArrayList<Integer>();
            String[] arrayTemp = stTemp.split(" ");
            maxSize = Math.max(maxSize, arrayTemp.length);
            for (int i = 0; i < arrayTemp.length; i++) {
                if (!arrayTemp[i].equals("")) {
                    numbersTemp.add(Integer.parseInt(arrayTemp[i]));
                }
            }
            numbers.add(numbersTemp);
        }
        for (int j = 0; j < maxSize; j++) {
            for (int i = 0; i < numbers.size(); i++) {
                if (j < numbers.get(i).size()) {
                    System.out.print(numbers.get(i).get(j) + " ");
                }
            }
            System.out.println();
        }
    }
}
/* 
    scan.hasNextLine()
    numbers.size() != 3
*/