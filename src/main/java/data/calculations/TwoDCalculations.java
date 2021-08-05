package data.calculations;

public class TwoDCalculations {
    /**
     * Calculates single ID of an element in a 2D array.
     * @param row of the element
     * @param col of the element
     * @param n, or size of one row of a 2D array
     * @return int
     */
    public static int getID(int row, int col, int n) {
        return (row * n) + col;
    }

    /**
     * Calculates row of an element in a 2D array.
     * @param id of the element
     * @param n, or size of one row of a 2D array
     * @return int
     */
    public static int getRow(int id, int n) {
        return id / n;
    }

    /**
     * Calculates column of an element in a 2D array.
     * @param id of the element
     * @param row the element is located in
     * @param n, or size of one row of a 2D array
     * @return int
     */
    public static int getCol(int id, int row, int n) {
        return id - (row * n);
    }
}
