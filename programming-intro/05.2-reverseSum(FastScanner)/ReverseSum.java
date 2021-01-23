import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ReverseSum {
    public static void main(String[] args) {
        try {
            FastScanner in = new FastScanner(System.in);

            List<ArrayList<Integer>> num = new ArrayList<>();
            List<Integer> lMin = new ArrayList<>();
            List<Integer> cMin = new ArrayList<>();
            int w = 0;
            while (in.hasNextLine()) {
                w++;
               num.add(in.nextArray());
                lMin.add(0);
                for (int i = 0; i < num.get(num.size() - 1).size(); i++) {
                    int temp = num.get(num.size() - 1).get(i);
                    lMin.set(lMin.size() - 1, lMin.get(lMin.size() - 1) + temp);
                    if (i >= cMin.size()) {
                        cMin.add(0);
                    }
                    cMin.set(i, cMin.get(i) + temp);
                }
            }
            for (int i = 0; i < num.size(); i++) {
                for (int j = 0; j < num.get(i).size(); j++) {
                    //System.out.print((num.get(i).get(j)) + " ");
                    System.out.print((cMin.get(j) + lMin.get(i) - num.get(i).get(j)) + " ");
                }
                System.out.print("\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Found not an integer number!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong array index!");
        } catch (IOException e) {
            System.out.println("Wrong input!");
        }
    }
}

class FastScanner {
    private BufferedReader in;

    public FastScanner(InputStream stream) {
        this.in = new BufferedReader(new InputStreamReader(stream));
    }

    String nextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    ArrayList<Integer> nextArray() throws IOException {
        ArrayList<Integer> a = new ArrayList<Integer>();
        String s = nextLine();
        if (s.length() == 0) {
            return a;
        }
        String[] splitted = s.split("\\p{javaWhitespace}+");
        for (String elem : splitted) {
            a.add(Integer.parseInt(elem));
        }
        return a;
    }
    boolean hasNextLine() {
        try {
            return in.ready();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}