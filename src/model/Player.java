package model;

import java.util.*;

/**
 * Represents a Scrabble player.
 * Stores name, score, and current rack of tiles.
 */
public class Player {
    private String name;
    private int score;
    private List<Tile> rack;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.rack = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addScore(int points) { score += points; }

    public List<Tile> getRack() { return rack; }
    public void setRack(List<Tile> tiles) { rack = tiles; }

    /** Adds tiles to the player's rack */
    public void addTiles(List<Tile> tiles) {
        rack.addAll(tiles);
    }

    /** Removes specific tiles from the rack */
    public void removeTiles(List<Tile> tiles) {
        rack.removeAll(tiles);
    }

    /** Returns a string representation of the rack */
    public String rackString() {
        StringBuilder sb = new StringBuilder();
        for (Tile t : rack) sb.append(t.getLetter()).append(' ');
        return sb.toString();
    }
}
