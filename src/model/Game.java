package model;

import java.util.*;

/**
 * Represents a Scrabble game with players, board, tile bag, and dictionary.
 * Handles turns, word placement, scoring, and tile drawing.
 */
public class Game {
    private List<Player> players;
    private int currentPlayerIndex;
    private Board board;
    private TileBag tileBag;
    private Dictionary dictionary;

    private static final int tilesPerTurn = 1;  // # of tiles players draw per turn

    /**
     * Initializes the game with a given number of players.
     * @param numPlayers number of players (2-4)
     */
    public Game(int numPlayers) {
        // Initialize core components
        board = new Board();
        tileBag = new TileBag();
        dictionary = new Dictionary("src/words.txt");
        players = new ArrayList<>();
        currentPlayerIndex = 0;

        // Add players
        for (int i = 1; i <= numPlayers; i++) {
            Player p = new Player("Player " + i);
            p.addTiles(tileBag.drawTiles(7)); // Initial rack
            players.add(p);
        }
    }

    /** @return the current player */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /** Moves to the next player's turn */
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /** Returns the game board */
    public Board getBoard() {
        return board;
    }

    /**
     * Player passes turn and exchanges all tiles.
     */
    public void passTurn() {
        Player current = getCurrentPlayer();
        List<Tile> oldTiles = current.getRack();
        tileBag.returnTiles(oldTiles);
        List<Tile> newTiles = tileBag.drawTiles(oldTiles.size());
        current.setRack(newTiles);
        System.out.println(current.getName() + " exchanged tiles.");
    }

    /**
     * Adds the tilesPerTurn to the current player's rack
     */
    public void newTile(){
        Player current = getCurrentPlayer();
        current.addTiles(tileBag.drawTiles(tilesPerTurn));
    }

    /**
     * Attempts to place a word on the board.
     * Word format: "H8 H WORD" (row-col, horizontal/vertical, word)
     * @param command the move string
     * @return true if move successful
     */
    public boolean playWord(String command) {
        try {
            String[] parts = command.split(" ");
            if (parts.length != 3) return false;

            int row = parseRow(parts[0]);
            int col = parseCol(parts[0]);
            boolean horizontal = parts[1].equalsIgnoreCase("H");
            String word = parts[2].toUpperCase();

            Player current = getCurrentPlayer();

            // Validate word in dictionary
            if (!dictionary.isValidWord(word)) return false;

            // Check if word fits and tiles are available
            List<Tile> neededTiles = board.getTilesNeeded(word, row, col, horizontal, current.getRack());
            if (neededTiles == null) return false;

            // Place tiles
            board.placeTiles(word, row, col, horizontal, neededTiles);
            current.removeTiles(neededTiles);

            // Refill rack
            current.addTiles(tileBag.drawTiles(neededTiles.size()));

            // Update score (basic: sum of tile values)
            current.addScore(board.calculateWordScore(word));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Converts a letter-digit position like "H8" to row index */
    private int parseRow(String pos) {
        return pos.toUpperCase().charAt(0) - 'A';
    }

    /** Converts a letter-digit position like "H8" to column index */
    private int parseCol(String pos) {
        return Integer.parseInt(pos.substring(1)) - 1;
    }

    /** Prints scores of all players */
    public void printScores() {
        System.out.println("\nScores:");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getScore());
        }
    }
}
