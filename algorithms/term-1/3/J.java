import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.*;
import java.io.*;

public class J {
    public static void main(String[] args) throws IOException {
        StringBuilder str = new StringBuilder();
	String input = "nice.in";
	String output = "nice.out";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                str.append(temp).append(" ");
            }
        } catch (FileNotFoundException | UnsupportedEncodingException | InputMismatchException e) {
            System.err.println("File does not exist");
        }
        String s = str.toString();
        StringBuilder stb = new StringBuilder();
        int ind = 0;
        while (Character.isDigit(s.charAt(ind))) {
            stb.append(s.charAt(ind));
            ind++;
        }
        int n = 0, m = 0;
        try {
            n = Integer.parseInt(stb.toString());
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        StringBuilder stm = new StringBuilder();
        ind++;
        while (Character.isDigit(s.charAt(ind))) {
            stm.append(s.charAt(ind));
            ind++;
        }
        try {
            m = Integer.parseInt(stm.toString());
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        if (n > m) {
            int temp = n;
            n = m;
            m = temp;
        }
        int mk = (1 << n);
        int[][] d = new int[mk][m];
        for (int i = 0; i < mk; i++) {
            d[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int prev = 0; prev < mk; prev++) {
                for (int cur = 0; cur < mk; cur++) {
                    boolean f = true;
                    for (int j = 0; j < n - 1; j++) {
                        int summ = bit(prev, j) + bit(prev, j + 1) + bit(cur, j) + bit(cur, j + 1);
                        if (summ == 0 || summ == 4) {
                            f = false;
                            break;
                        }
                    }
                    if (f) {
                        d[cur][i] += d[prev][i - 1];
                    }

                }
            }
        }
        int ans = 0;
        for (int i = 0; i < mk; i++) {
            ans += d[i][m - 1];
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8))) {
            writer.write(ans + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int bit(int mk, int index) {
        int res = 0;
        if ((mk & (1 << index)) != 0) {
            res = 1;
        }
        return res;
    }

}