package nl.hanze.tdd;

import java.util.HashMap;
import nl.hanze.hive.Hive.Tile;
class Player {
    /** An array of gamepieces avaiable to that player. */
    private HashMap<Tile, Integer> pieces;

     Player(final nl.hanze.hive.Hive.Player colour) {
        this.pieces = new HashMap<>();
        this.pieces.put(Tile.QUEEN_BEE, 1);
        this.pieces.put(Tile.SOLDIER_ANT, 3);
        this.pieces.put(Tile.SPIDER, 2);
        this.pieces.put(Tile.BEETLE, 2);
        this.pieces.put(Tile.GRASSHOPPER, 3);
    }

    /**
     * Get an array of all avaiable gamepieces.
     * @return an array of all avaiable pieces
     */
    public HashMap<Tile, Integer> getPieces() {
        return this.pieces;
    }
}
