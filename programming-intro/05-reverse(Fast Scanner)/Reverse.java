import java.io.*;
import java.util.ArrayList;

public class Reverse {
    public static void main(String[] args) {
        FastScanner in = new FastScanner(System.in);
        ArrayList<String> numbers = new ArrayList<>();
        String s;
        while ((s = in.nextLine()) != null) {
            numbers.add(s);
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


class FastScanner {
    private BufferedReader in;

    FastScanner(InputStream stream) {
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
