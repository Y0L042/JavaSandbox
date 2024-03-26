import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class MazeGenerator {
    static MazeGenerator app;
    public static void main(String[] args) {
        app = new MazeGenerator();
        app.run(args);
    }

    int size = 15;

    String CHAR_SPACE = " ";
    String spriteCross = "+";
    String spriteSquare = "\u25A0";
    
    Random rand;
    int sizeSquare = size * size;
    int[] maze = new int[sizeSquare];
    int[] visited = new int[sizeSquare];

    void run(String[] args) {
        long seed = System.currentTimeMillis();
        rand = new Random(seed);

        EasyRaylibJ.setReady(this::ready);
        EasyRaylibJ.setUpdate(this::update);
        EasyRaylibJ.setDraw(this::draw);

        EasyRaylibJ.run();
    }

    void ready() {
        fillMazes();
        generateMaze();
    }

    void update(float delta) {

    }

    void draw(float delta) {

    }

    void fillMazes() {
        Arrays.fill(maze, 1);
        Arrays.fill(visited, -1);
    }

    void generateMaze() {
        int stopCount = 1000000;

        int startingIdx = 0;
        int endIdx = maze.length - 1;
        
        int activeIdx = startingIdx;
        int activeVisitedIdx = 0;
        visited[activeVisitedIdx++] = startingIdx;
        while (activeIdx != endIdx && stopCount > 0) {
            maze[activeIdx] = 0;

            List<Integer> neighbors = getNeighbors(activeIdx, maze, size, size);
            int randneighborIdx = rand.nextInt(neighbors.size());
            int randIdx = neighbors.get(randneighborIdx);
            while (arrayContains(visited, randIdx)) {
                randIdx = (randIdx + 1) % neighbors.size() - 1;
                stopCount--;
                if (stopCount <= 1) { break; }
            } 
            activeIdx = randIdx;
            visited[activeVisitedIdx++] = randIdx;

            if (stopCount <= 1) { System.out.println("StopCounter reached 0!"); }
            stopCount--;
        }
    }

    void display() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int idx = row * size + column;
                String tileChar = returnCharOnTile(idx, maze);
                System.out.print(tileChar);
                System.out.print(CHAR_SPACE + CHAR_SPACE);
            }
            System.out.print("\n");
        }
    }

    String returnCharOnTile(int idx, int[] maze) {
        if (maze[idx] == 0) {
            return spriteCross;
        }

        if (maze[idx] == 1) {
            return spriteSquare;
        }

        return " ";
    }

    public static List<Integer> getNeighbors(int idx, int[] array, int width, int height) {
        List<Integer> neighbors = new ArrayList<>();
        int row = idx / width;
        int col = idx % width;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                    neighbors.add(array[newRow * width + newCol]);
                }
            }
        }
        
        return neighbors;
    }

    public static boolean arrayContains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }

        return false;
    }
}
