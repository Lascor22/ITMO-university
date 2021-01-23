import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.AutoCloseable;

class FastScanner implements AutoCloseable {
    private BufferedReader in;

    FastScanner(InputStreamReader stream) {
        this.in = new BufferedReader(stream);
    }

    private String nextLine() throws IOException {
        return in.readLine();
    }

    List<String> nextWordArrayList() throws IOException {
        List<String> arr = new ArrayList<>();
        String s = nextLine().toLowerCase();
        if (!s.isEmpty()) {
            Pattern pt = Pattern.compile("[\\p{L}\\p{Pd}']+");
            Matcher mt = pt.matcher(s);
            while (mt.find()) {
                String temp = mt.group(0);
                arr.add(temp);
            }
        }
        return arr;
    }

    List<Integer> nextArray() throws IOException {
        List<Integer> arr = new ArrayList<>();
        String s = nextLine();
        if (!s.isEmpty()) {
            String[] strArr = s.split("\\p{javaWhitespace}+");
            for (String temp : strArr) {
                arr.add(Integer.parseInt(temp));
            }
        }
        return arr;
    }

    boolean hasNext() throws IOException {
        return in.ready();
    }

    public void close() throws IOException {
        in.close();
    }
}
