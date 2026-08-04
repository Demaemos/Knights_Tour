/**
 * Author: Tyler Andrews
 * Date: Wed 07/01/2026  - Fri 07/10/2026
 * Course: CS421 Algorithms
 * Project: Programming Project 1: Knight's Tour
 *
 * Description: Driver class for the Knight's Tour problem.
 * Using the command-line arguments to specify the parameters.
 * Then displays the results of the search and number of moves.
 */
public class KnightTour {
    public static void main(String[] args) {
        // validate argument count
        if (args.length != 4) {
            System.out.println("Invalid number of arguments.");
            System.out.println("Usage: java KnightTour <strategy> <board_size> <start_row> <start_col>");
            return;
        }
    
        // parse integers
        int strategy;
        int boardSize;
        int startRow;
        int startCol;
        try {
            strategy = Integer.parseInt(args[0]);
            boardSize = Integer.parseInt(args[1]);
            startRow = Integer.parseInt(args[2]);
            startCol = Integer.parseInt(args[3]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integer values.");
            return;
        }

        // validate values
        //// strategy
        if (strategy < 0 || strategy > 2) {
            System.out.println("Invalid strategy. Please enter 0, 1, or 2.");
            return;
        }

        //// board size
        if (boardSize <= 0) {
            System.out.println("Invalid board size. Please enter a positive integer.");
            return;
        }

        //// starting position
        if (startRow < 0 || startRow >= boardSize || startCol < 0 || startCol >= boardSize) {
            System.out.println("Invalid starting position.");
            return;
        }

        // create KnightBoard
        KnightBoard board = new KnightBoard(boardSize);
        
        // solve
        boolean solved = board.solve(strategy, startRow, startCol);
    
        // print results
        if (solved) {
            board.printBoard();
        }
        else {
            System.out.println("No solution found.");
        }        
        System.out.println("The total number of moves is " + board.getMovesTried());
    }
}
