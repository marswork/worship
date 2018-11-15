
import java.util.Arrays;

public class RotateImage {
    /**
     * clockwise rotateClockwise
     * first reverseByRow up to down, then swap the symmetry
     * 1 2 3     7 8 9     7 4 1
     * 4 5 6  => 4 5 6  => 8 5 2
     * 7 8 9     1 2 3     9 6 3
     */
    public static void rotateClockwise(int[][] matrix) {
        reverseByRow(matrix);
        reverseDiagonally(matrix);
    }

    /**
     * CounterClockwise
     * first reverseByRow left to right, then swap the symmetry
     * 1 2 3     3 2 1     3 6 9
     * 4 5 6  => 6 5 4  => 2 5 8
     * 7 8 9     9 8 7     1 4 7
     */
    public static void rotateCounterClockwise(int[][] matrix) {
        reverseByCol(matrix);
        reverseDiagonally(matrix);
    }

    private static void reverseDiagonally(int[][] matrix) {
        final int rows = matrix.length, cols = matrix[0].length;
        for (int i = 0; i < rows; ++i) {
            for (int j = i + 1; j < cols; ++j) {
                swap(matrix, i, j, j, i);
            }
        }
    }

    /**
     * reverseByRow rows of a matrix
     *
     * @param m
     */
    private static void reverseByRow(int[][] m) {
        int first = 0, last = m.length - 1;
        while (first < last) {
            int[] t = m[first];
            m[first] = m[last];
            m[last] = t;
            first++;
            last--;
        }
    }

    private static void reverseByCol(int[][] m) {
        for (int[] row : m) {
            int first = 0, last = row.length - 1;
            while (first < last) {
                swap(row, first, last);
                first++;
                last--;
            }
        }
    }

    private static void swap(int[][] matrix, int r1, int c1, int r2, int c2) {
        int t = matrix[r1][c1];
        matrix[r1][c1] = matrix[r2][c2];
        matrix[r2][c2] = t;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[][] m = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("before rotateClockwise:");
        System.out.println(Arrays.deepToString(m));

        System.out.println("after rotateClockwise:");
        rotateClockwise(m);
        System.out.println(Arrays.deepToString(m));


        int[][] m2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("before rotateCounterClockwise:");
        System.out.println(Arrays.deepToString(m2));

        System.out.println("after rotateCounterClockwise:");
        rotateCounterClockwise(m2);
        System.out.println(Arrays.deepToString(m2));


    }
}
