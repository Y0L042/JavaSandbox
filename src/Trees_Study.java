import java.util.ArrayList;

public class Trees_Study {
    static Trees_Study app;
    public static void main(String[] args) {
        app = new Trees_Study();
        app.run(args);
    }

    ArrayList<Integer> array = new ArrayList<>();
    Timer timer = new Timer();
    ArrayList<TestResult> results = new ArrayList<>();

    Integer searchValue = 5000;
    Integer valueCount = 100000;

    void run(String[] args) {
        timer.start();
        Utils.writeRandomIntegers("in/bst.txt", valueCount);
        timer.stop();
        System.out.println("Generated random number input file: " + timer.getMilliTime() + " ms");
        timer.start();
        array = Utils.readFile(args[0]);
        timer.stop();
        System.out.println("Read input file: " + timer.getMilliTime() + " ms");
        
        testLinearSearchBenchmark(array);
        testBinarySearchTree(array);
        testRedBlackTree(array);
        
        System.out.println("-------------------------------------------------------");
        
        printResultsTable();

        System.out.println("-------------------------------------------------------");
    }

    void testLinearSearchBenchmark(ArrayList<Integer> array) {
        boolean found = false;

        timer.start();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == searchValue) {
                found = true;
                break;
            }
        }
        timer.stop();
        results.add(new TestResult("Linear Search", searchValue, valueCount, timer.getMilliTime(), found));
    }

    void testBinarySearchTree(ArrayList<Integer> array) {
        BinarySearchST<Integer, Integer> bst = new BinarySearchST<>(valueCount);
        timer.start();
        for (int i = 0; i < array.size(); i++) {
            bst.put(array.get(i), array.get(i) * 10);
        }        
        timer.stop();
        System.out.println("Created BST: " + timer.getMilliTime() + " ms");

        boolean found = false;
        timer.start();
        found = bst.contains(searchValue);
        timer.stop();
        results.add(new TestResult("BST", searchValue, valueCount, timer.getMilliTime(), found));
    }

    void testRedBlackTree(ArrayList<Integer> array) {
        RedBlackBST<Integer, Integer> rbt = new RedBlackBST<>();
        timer.start();
        for (int i = 0; i < array.size(); i++) {
            rbt.put(array.get(i), array.get(i) * 10);
        } 
        timer.stop();
        System.out.println("Created RBT: " + timer.getMilliTime() + " ms");

        boolean found = false;
        timer.start();
        found = rbt.contains(searchValue);
        timer.stop();
        results.add(new TestResult("RBT", searchValue, valueCount, timer.getMilliTime(), found));
    }

    void printResultsTable() {
        System.out.printf("%-15s %-6s %-10s %-10s %-15s\n", "Search Type", "Value", "ValueCount", "Time (ms)", "Contains Value");
        for (TestResult result : results) {
            System.out.printf("%-15s %-6d %-10d %-10.4f %-15s\n", result.testType, result.value, result.valueCount, result.time, result.containsValue);
        }
    }


}

class TestResult {
    String testType;
    int value;
    int valueCount;
    double time;
    boolean containsValue;

    public TestResult(String testType, int value, int valueCount, double time, boolean containsValue) {
        this.testType = testType;
        this.value = value;
        this.valueCount = valueCount;
        this.time = time;
        this.containsValue = containsValue;
    }
}