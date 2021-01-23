import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.regex.*;

public class WordStatInput {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        String str = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("1.txt"), "UTF-8"));
        try {
            String temp;
            while ((temp = reader.readLine()) != null) {
                str += temp + " ";
            }
        } catch (FileNotFoundException | UnsupportedEncodingException | InputMismatchException e) {
            System.err.println("File does not exist");
        } finally {
            reader.close();
        }
        Pattern pt = Pattern.compile("[\\p{L}\\p{Pd}\\']+");
        Matcher mt = pt.matcher(str);
        while (mt.find()) {
            String temp = mt.group(0);
            if (temp.length() > 0) {
                temp = temp.toLowerCase();
                map.put(temp, (map.containsKey(temp) ? map.get(temp) + 1 : 1));
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("2.txt"), "utf8"));
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
