package model;

/**
 * Represents a Scrabble tile.
 * Holds letter and point value.
 */
public class Tile {
    private char letter;
    private int value;

    public Tile(char letter) {
        this.letter = letter;
        this.value = 1; // Basic value for Milestone 1
    }

    public char getLetter() { return letter; }
    public int getValue() { return value; }
}
