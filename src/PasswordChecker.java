import java.util.ArrayList;
import java.util.HashMap;

public class PasswordChecker {
    static PasswordChecker app;

    public static void main(String[] args) {
        app = new PasswordChecker();
        app.run(args);    
    }

    ArrayList<String> dict = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();

    private void run(String[] args) {
        String input = args[0];
        String pathName = "./in/dictionary.txt";
        dict = Utils.readStringFile(pathName);
        loadDictIntoHashtable(dict);

        boolean valid = runCheckPipeline(input);

        if (valid) {
            System.out.println("Good password");
        } else {
            System.out.println("Bad password");
        }
    }

    void loadDictIntoHashtable(ArrayList<String> dictionary) {
        for (int i = 0; i < dictionary.size(); i++) {
            map.put(dictionary.get(i), dictionary.get(i));
        }
    }

    boolean runCheckPipeline(String input) {
        boolean valid = true;

        // is at least 12 characters long
        if (input.length() < 12) {
            valid = false;
        }
        
        // is not a word in the dictionary
        if (map.containsValue(input)) {
            valid = false;
        }

        // is not a word in the dictionary followed by a digit 0-9 (e.g., hello5)
        if (map.containsValue(input.replaceAll("[0-9]",""))) { // !
            valid = false;
        }

        // is not two words separated by a digit (e.g., hello2world)
        if (
                map.containsValue(input.replaceAll("[0-9]"," ").split(" ")[0])
            &&  map.containsValue(input.replaceAll("[0-9]"," ").split(" ")[1])
         ) { // !
            valid = false;
        }

        return valid;
    }

}