package calculations;

public class GridCalculations {
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

    public static double getEuclidDistance(int id1, int id2, int n) {
        // retrieves the coordinates for first area
        int x1 = getRow(id1, n);
        int y1 = getCol(id1, x1, n);

        // retrieves the coordinates for second area
        int x2 = getRow(id2, n);
        int y2 = getCol(id2, x2, n);

        // squaring a number
        int pow = 2;

        // calculates and returns euclidean distance of two
        // coordinates
        return Math.sqrt(Math.pow((x1 - x2), pow) +
                Math.pow((y1 - y2), pow));
    }
}
