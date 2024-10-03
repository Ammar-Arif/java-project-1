import java.util.*;
import java.io.*;

public class javaProject {
    
    // Define vowels and consonants (Y is treated as a consonant)
    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static final char[] CONSONANTS = {
        'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 
        'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Get user input for rows, columns, and minimum word length
        System.out.print("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter the number of columns: ");
        int cols = scanner.nextInt();
        System.out.print("Enter the minimum word length: ");
        int minWordLength = scanner.nextInt();

        // 2. Generate a grid of letters
        char[][] grid = generateGrid(rows, cols);
        printGrid(grid);

        // 3. Read the words from words.txt
        List<String> wordList = readWordsFromFile("words.txt");
        
        // 4. Search for words in the grid
        List<String> foundWords = findWordsInGrid(grid, wordList, minWordLength);

        // 5. Sort and display the words
        Collections.sort(foundWords, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        
        System.out.println("\nFound words:");
        for (String word : foundWords) {
            System.out.println(word);
        }

        scanner.close();
    }

    // Method to generate the grid with 30% chance of vowel and 70% consonant
    private static char[][] generateGrid(int rows, int cols) {
        Random rand = new Random();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = rand.nextDouble() < 0.3 ? randomVowel() : randomConsonant();
            }
        }

        return grid;
    }

    // Method to randomly select a vowel
    private static char randomVowel() {
        Random rand = new Random();
        return VOWELS[rand.nextInt(VOWELS.length)];
    }

    // Method to randomly select a consonant
    private static char randomConsonant() {
        Random rand = new Random();
        return CONSONANTS[rand.nextInt(CONSONANTS.length)];
    }

    // Method to print the grid
    private static void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char letter : row) {
                System.out.print(letter + " ");
            }
            System.out.println();
        }
    }

    // Method to read words from the file
    private static List<String> readWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.trim().toUpperCase());
        }

        reader.close();
        return words;
    }

    // Method to find words in the grid
    private static List<String> findWordsInGrid(char[][] grid, List<String> wordList, int minWordLength) {
        Set<String> foundWords = new HashSet<>();
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                searchWords(grid, i, j, rows, cols, wordList, foundWords, minWordLength);
            }
        }

        return new ArrayList<>(foundWords);
    }

    // Helper method to search words in all 8 directions
    private static void searchWords(char[][] grid, int row, int col, int rows, int cols, List<String> wordList, Set<String> foundWords, int minWordLength) {
        int[] rowDirections = {-1, -1, -1, 0, 1, 1, 1, 0};
        int[] colDirections = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int direction = 0; direction < 8; direction++) {
            StringBuilder word = new StringBuilder();
            int newRow = row;
            int newCol = col;

            while (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                word.append(grid[newRow][newCol]);

                if (word.length() >= minWordLength && wordList.contains(word.toString())) {
                    foundWords.add(word.toString());
                }

                newRow += rowDirections[direction];
                newCol += colDirections[direction];
            }
        }
    }
}
