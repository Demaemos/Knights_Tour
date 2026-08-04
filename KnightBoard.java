import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Author: Tyler Andrews
 * Date: Wed 07/01/2026  - Fri 07/10/2026
 * Course: CS421 Algorithms
 * Project: Programming Project 1: Knight's Tour
 *
 * Description: Handles the logic for the Knight's Tour problem.
 */
public class KnightBoard {

    private int size;
    private int[][] board;
    private long movesTried;

    // Possible moves a knight can make
    private static final int[] ROW_MOVES = {-2, -1, 1, 2, 2, 1, -1, -2};
    private static final int[] COL_MOVES = {1, 2, 2, 1, -1, -2, -2, -1};

    /**
     * Constructor for the KnightBoard class.
     *
     * @param size The size of the chessboard (size x size).
     */
    public KnightBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.movesTried = 0;
    }

    // ======== Public Methods ========

    /**
     * Solves the Knight's Tour problem using the specified strategy.
     *
     * @param strategy The strategy to use
     *         0 = Basic Search
     *         1 = Heuristic I
     *         2 = Heuristic II
     * @param startRow The starting row for the knight.
     * @param startCol The starting column for the knight.
     * @return True if a solution is found, false otherwise.
     */
    public boolean solve(int strategy, int startRow, int startCol) {
        if (!inBounds(startRow, startCol)) {
            return false; // Invalid starting position
        }

        clearBoard();

        movesTried = 0;

        Position start = new Position(startRow, startCol);

        return solveRecursive(start, 1, strategy);
    }

    /**
     * Prints the current state of the board.
     */
    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%3d ", board[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Gets the number of moves tried during the search.
     *
     * @return The number of moves tried.
     */
    public long getMovesTried() {
        return movesTried;
    }

    // ======== Private Helper Methods ========

    /**
     * Checks if the given position is within the bounds of the board.
     *
     * @param row The row index to check.
     * @param col The column index to check.
     * @return True if the position is within bounds, false otherwise.
     */
    private boolean inBounds(int row, int col) {
        return row >= 0 &&
               row < size &&
               col >= 0 &&
               col < size;
    }

    /**
     * Determines if a move to the specified row and column is legal 
     * (within bounds and not visited).
     * @param row The row index to check.
     * @param col The column index to check.
     * @return True if the move is legal, false otherwise.
     */
    private boolean isLegalMove(int row, int col) {
        return inBounds(row, col) && board[row][col] == 0;
    }

    /**
     * Marks a position on the board as visited.
     * @param p The position to mark.
     * @param moveNumber The move number to assign to the position.
     */
    private void makeMove(Position p, int moveNumber) {
        board[p.getRow()][p.getCol()] = moveNumber;
    }

    /**
     * Undoes a move by marking the position as unvisited.
     * @param p The position to unmark.
     */
    private void undoMove(Position p) {
        board[p.getRow()][p.getCol()] = 0;
    }

    /**
     * Gets a list of legal moves for the knight from the current position.
     *
     * @param current  The current position of the knight.
     * @param strategy The strategy to use for move selection.
     * @return A list of legal moves for the knight.
     */
    private List<Position> getLegalMoves(Position current, int strategy) {
        // create a list to hold legal moves
        List<Position> legalMoves = new ArrayList<>();

        int row = current.getRow();
        int col = current.getCol();

        for (int i = 0; i < ROW_MOVES.length; i++) {
            int newRow = row + ROW_MOVES[i];
            int newCol = col + COL_MOVES[i];

            if (isLegalMove(newRow, newCol)) {
                legalMoves.add(new Position(newRow, newCol));
            }
        }
        // Apply heuristic I; border-distance order
        if (strategy == 1) {
            legalMoves.sort(Comparator.comparingInt(this::borderDistance));
        }
        // Apply heuristic II; onward-moves count
        else if (strategy == 2) {
            legalMoves.sort(Comparator.comparingInt(this::countOnwardMoves));
        }
        return legalMoves;
    }

    /**
     * Computes the border distance used by Heuristic I.
     * The distance is defined as the minimum vertical distance
     * to a board edge plus the minimum horizontal distance to a
     * board edge.
     *
     * @param p The position to check.
     * @return The border distance for the position.
     */
    private int borderDistance(Position p) {
        int row = p.getRow();
        int col = p.getCol();

        int top = row;
        int bottom = size - 1 - top;
        
        int left = col;
        int right = size - 1 - left;

        return Math.min(top,bottom) + Math.min(left, right);
    }

    /**
     * Counts the number of onward moves from the given position.
     *
     * @param p The position to check.
     * @return The number of onward moves.
     */
    private int countOnwardMoves(Position p) {
        int count = 0;
        int currentRow = p.getRow();
        int currentCol = p.getCol();
        for (int i = 0; i < ROW_MOVES.length; i++) {
            int newRow = currentRow + ROW_MOVES[i];
            int newCol = currentCol + COL_MOVES[i];
            if (isLegalMove(newRow, newCol)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Clears the board by resetting all positions to unvisited.
     */
    private void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * Attempts to solve the knight's tour problem using backtracking.
     */
    private boolean solveRecursive(Position current, int moveNumber, int strategy) {
        movesTried++;

        makeMove(current, moveNumber);

        if (moveNumber == size * size) {
            return true;
        }

        List<Position> moves = getLegalMoves(current, strategy);

        for (Position next : moves) {
            if (solveRecursive(next, moveNumber + 1, strategy)) {
                return true;
            }    
        }
        undoMove(current);
        return false;        
    }
    
}
