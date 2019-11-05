package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;
public final class Grasshopper  extends GamePiece {

     /** The Queen Bee's tile.   */
    private Tile tile = Tile.GRASSHOPPER;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.
     */
    public Grasshopper(final Player colour) {
        super(colour);
        this.setTile(this.tile);
    }
}
