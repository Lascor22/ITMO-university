import java.io.*;
import java.util.*;

public class ReverseMin {
    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);

        List<ArrayList<Integer>> num = new ArrayList<>();
        List<Integer> lMin = new ArrayList<>();
        List<Integer> cMin = new ArrayList<>();

        String strTemp;
        while ((strTemp = in.nextLine()) != null) {
            num.add(new ArrayList<>());
            String[] arrayTemp = strTemp.split(" ");
            lMin.add(Integer.MAX_VALUE);
            for (int i = 0; i < arrayTemp.length; i++) {
                if (arrayTemp[i].length() > 0) {
                    int temp = Integer.parseInt(arrayTemp[i]);
                    num.get(num.size() - 1).add(temp);
                    if (temp < lMin.get(lMin.size() - 1)) {
                        lMin.set(lMin.size() - 1, Math.min(lMin.get(lMin.size() - 1), temp));
                    }
                    if (i >= cMin.size()) {
                        cMin.add(Integer.MAX_VALUE);
                    }
                    cMin.set(i, Math.min(cMin.get(i), temp));
                }
            }
        }
        for (int i = 0; i < num.size(); i++) {
            for (int j = 0; j < num.get(i).size(); j++) {
                System.out.print(Math.min(cMin.get(j), lMin.get(i)) + " ");
            }
            System.out.print("\n");
        }
    }
}

class FastScanner {
    private BufferedReader in;

    public FastScanner(InputStream stream) {
        in = new BufferedReader(new InputStreamReader(stream));
    }

    String nextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
