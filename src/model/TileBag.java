package model;

import java.util.*;

/**
 * Represents the bag of tiles to draw from.
 */
public class TileBag {
    private List<Tile> tiles;

    public TileBag() {
        tiles = new ArrayList<>();
        initializeTiles();
        shuffle();
    }

    /** Populates the bag with basic letters */
    private void initializeTiles() {
        for (char c = 'A'; c <= 'Z'; c++) {
            for (int i = 0; i < 2; i++) { // 2 of each letter for simplicity
                tiles.add(new Tile(c));
            }
        }
    }

    /** Shuffles the tile bag */
    public void shuffle() {
        Collections.shuffle(tiles);
    }

    /** Draws up to n tiles from the bag */
    public List<Tile> drawTiles(int n) {
        List<Tile> drawn = new ArrayList<>();
        for (int i = 0; i < n && !tiles.isEmpty(); i++) {
            drawn.add(tiles.remove(tiles.size() - 1));
        }
        return drawn;
    }

    /** Returns tiles back to the bag */
    public void returnTiles(List<Tile> returned) {
        tiles.addAll(returned);
        shuffle();
    }
}
