import java.util.*;
import java.io.*;

public class wordSearch {

    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static final char[] CONSONANTS = {
        'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 
        'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("How many rows");
        int rows = scanner.nextInt();
        System.out.print("How many columns");
        int cols = scanner.nextInt();
        System.out.print("Enter the minimum words of length");
        int words = scanner.nextInt();

        char grid[][] = new char[rows][cols];
        
        
        Random rand = new Random();
        // System.out.println(rand.nextDouble());

        for(int i = 0; i < rows; i++){

            // System.out.println("row number: " + i);
            for(int j = 0; j < cols; j++){

                // System.out.println("col number: " + j);

                if (rand.nextDouble() > 0.3){
                    grid[i][j] = CONSONANTS[rand.nextInt(CONSONANTS.length)];
                } else {
                    grid[i][j] = VOWELS[rand.nextInt(VOWELS.length)];

                }

                System.out.print(grid[i][j] + " ");

            }

            System.out.println(" ");


        } 
        


        
    }
}
