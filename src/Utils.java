import java.util.ArrayList;

public class Utils {
    public static ArrayList<Integer> readFile(String filePath) {
        In input = new In(filePath);
        ArrayList<Integer> list = new ArrayList<>();
        while (!input.isEmpty()) {
            list.add(Integer.valueOf(input.readInt()));
        }
        return list;
    }

    public static ArrayList<String> readStringFile(String filePath) {
        In input = new In(filePath);
        ArrayList<String> list = new ArrayList<>();
        while (!input.isEmpty()) {
            list.add(String.valueOf(input.readString()));
        }
        return list;
    }


    public static void writeRandomIntegers(String filename, Integer count) {
        Out out = new Out(filename);
        for (int i = 0; i < count; i++) {
            int randomInteger = (int) (Math.random() * Integer.MAX_VALUE);
            out.print(randomInteger + " ");
        }
        out.close();
    }
}
