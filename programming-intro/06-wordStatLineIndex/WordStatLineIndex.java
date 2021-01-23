import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.*;
import java.io.*;

public class WordStatLineIndex {
    public static void main(String[] args) throws IOException {
        if (isValid(args)) {
            Map<String, List<String>> map = new TreeMap<>();
            try (FastScanner in = new FastScanner(new InputStreamReader(
                    new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
                int countLine = 1;
                while (in.hasNext()) {
                    try {
                        List<String> arrayTemp = in.nextWordArrayList();
                        for (int i = 0; i < arrayTemp.size(); i++) {
                            if (arrayTemp.get(i).length() > 0) {
                                if (map.containsKey(arrayTemp.get(i))) {
                                    map.get(arrayTemp.get(i)).add(countLine + ":" + (i + 1));
                                } else {
                                    map.put(arrayTemp.get(i), new ArrayList<>());
                                    map.get(arrayTemp.get(i)).add(countLine + ":" + (i + 1));
                                }
                            }
                        }
                        countLine++;
                    } catch (IOException e) {
                        System.out.println("Something went wrong " + e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File " + args[0] + " not found");
            }
            try (BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    out.write(entry.getKey() + " " + entry.getValue().size());
                    for (String strTemp : entry.getValue()) {
                        out.write(" " + strTemp);
                    }
                    out.write("\n");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Can`t create an output file");
            }
        } else {
            System.out.println("Wrong input data");
        }
    }
    private static boolean isValid(String[] strTemp) {
        return strTemp.length > 1;
    }
}