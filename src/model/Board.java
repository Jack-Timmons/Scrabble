package model;

import java.util.*;

/**
 * Represents the Scrabble board (15x15 grid).
 * Handles placement of tiles and basic scoring.
 */
public class Board {
    private static final int SIZE = 15;
    private Tile[][] grid;

    public Board() {
        grid = new Tile[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                grid[i][j] = null; // Empty tile
    }

    /** Prints the board with proper alignment for columns 1–15 */
    public void printBoard() {
        System.out.println();

        // Print column headers
        System.out.print("   "); // extra space for row labels
        for (int c = 0; c < SIZE; c++) {
            System.out.printf("%2d ", c + 1); // numbers 1–15 with spacing
        }
        System.out.println();

        // Print each row
        for (int r = 0; r < SIZE; r++) {
            System.out.print((char)('A' + r) + "  "); // row label (A–O)
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] == null) {
                    System.out.print(".  "); // empty cell
                } else {
                    System.out.print(grid[r][c].getLetter() + "  "); // letter tile
                }
            }
            System.out.println();
        }
    }


    /**
     * Returns the list of tiles a player needs from their rack to place a word.
     * Null if placement invalid.
     */
    public List<Tile> getTilesNeeded(String word, int row, int col, boolean horizontal, List<Tile> rack) {
        List<Tile> needed = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            if (r >= SIZE || c >= SIZE) return null; // Out of bounds
            Tile existing = grid[r][c];
            if (existing == null) {
                // Need tile from rack
                boolean found = false;
                for (Tile t : rack) {
                    if (t.getLetter() == word.charAt(i)) {
                        needed.add(t);
                        found = true;
                        break;
                    }
                }
                if (!found) return null; // Tile not in rack
            } else {
                if (existing.getLetter() != word.charAt(i)) return null; // Conflict
            }
        }
        return needed;
    }

    /** Places tiles on board */
    public void placeTiles(String word, int row, int col, boolean horizontal, List<Tile> tiles) {
        int idx = 0;
        for (int i = 0; i < word.length(); i++) {
            int r = row + (horizontal ? 0 : i);
            int c = col + (horizontal ? i : 0);
            if (grid[r][c] == null) {
                grid[r][c] = tiles.get(idx++);
            }
        }
    }

    /** Calculates simple score (sum of tile values) */
    public int calculateWordScore(String word) {
        return word.length(); // Placeholder: each tile = 1 point
    }
}
