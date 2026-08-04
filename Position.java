/**
 * Author: Tyler Andrews
 * Date: Wed 07/01/2026  -
 * Course: CS421 Algorithms
 * Project: Programming Project 1: Knight's Tour
 * 
 * Description: Represents a position on a chessboard.
 */

public class Position {

    private int row;
    private int col;

    /**
     * Constructs a Position object with the specified row and column.
     *
     * @param row the row of the position
     * @param col the column of the position
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of the position.
     *
     * @return the row of the position
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the position.
     *
     * @return the column of the position
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns a string representation of the position.
     * @return a string representation of the position
     * For debugging purposes.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

