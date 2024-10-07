import java.util.*;
import java.io.*;

public class wordSearch {

    private static final char[] VOWELS = { 'A', 'E', 'I', 'O', 'U' };
    private static final char[] CONSONANTS = {
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("How many rows: ");
        int rows = scanner.nextInt();
        System.out.print("How many columns: ");
        int cols = scanner.nextInt();
        System.out.print("Enter the minimum words of length: ");
        int words = scanner.nextInt();

        ArrayList<String> wordList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("words.txt"));

            String line;

            // Proceed with reading from the file...
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.length() == words) {
                        wordList.add(line);
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        char[][] grid = generateGrid(rows, cols, wordList);

    }

    public static char[][] generateGrid(int rows, int cols, ArrayList<String> wordList) {
        char grid[][] = new char[rows][cols];
        Random rand = new Random();

        // Generate the grid with random vowels and consonants
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rand.nextDouble() > 0.3) {
                    grid[i][j] = CONSONANTS[rand.nextInt(CONSONANTS.length)];
                } else {
                    grid[i][j] = VOWELS[rand.nextInt(VOWELS.length)];
                }
            }
        }

        // Print the generated grid
        System.out.println("Generated Grid:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        // Define the 8 directions: (row movement, column movement)
        int[][] directions = {
                { 0, 1 }, // right
                { 0, -1 }, // left
                { 1, 0 }, // down
                { -1, 0 }, // up
                { 1, 1 }, // down-right diagonal
                { -1, -1 }, // up-left diagonal
                { 1, -1 }, // down-left diagonal
                { -1, 1 } // up-right diagonal
        };

        // Check for words in the wordList
        System.out.println("Words Found:");
        for (String word : wordList) {
            boolean wordFound = false; // Flag to ensure we print each word only once

            // Search in the grid
            for (int i = 0; i < rows && !wordFound; i++) {
                for (int j = 0; j < cols && !wordFound; j++) {
                    if (grid[i][j] == word.charAt(0)) {
                        // Check all directions from the current starting point
                        for (int[] dir : directions) {
                            if (searchWord(grid, word, i, j, dir, rows, cols)) {
                                System.out.println(word);
                                wordFound = true; // Stop searching once the word is found
                                break;
                            }
                        }
                    }
                }
            }
        }
        return grid;
    }

    // Helper method to search for a word starting from a specific point (row, col)
    // in a given direction
    public static boolean searchWord(char[][] grid, String word, int row, int col, int[] dir, int rows, int cols) {
        int wordLen = word.length();
        int currentRow = row;
        int currentCol = col;

        // Check each character of the word in the specified direction
        for (int k = 0; k < wordLen; k++) {
            // Check if current position is out of bounds
            if (currentRow < 0 || currentRow >= rows || currentCol < 0 || currentCol >= cols) {
                return false; // out of bounds
            }
            // Check if the character matches
            if (grid[currentRow][currentCol] != word.charAt(k)) {
                return false; // character mismatch
            }
            // Move to the next character in the current direction
            currentRow += dir[0];
            currentCol += dir[1];
        }

        // If we matched all characters, return true
        return true;
    }

}
