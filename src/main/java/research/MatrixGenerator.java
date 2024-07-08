package research;

import java.util.Random;

public class MatrixGenerator {
    public static void main(String[] args) {
        int rows = 5;
        int cols = 5;
        int[][] matrix = generateMatrix(rows, cols);
        printMatrix(matrix);
    }

    public static int[][] generateMatrix(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(100);
            }
        }
        return matrix;
    }

    public static int[][] generateMatrix(int rows, int cols, int edge_count) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];

        int count = 0; // Счетчик заполненных ячеек
        while (count < edge_count) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            if (matrix[row][col] == 0) {
                matrix[row][col] = random.nextInt(100);
                count++;
            }
        }
        return matrix;
    }


    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}