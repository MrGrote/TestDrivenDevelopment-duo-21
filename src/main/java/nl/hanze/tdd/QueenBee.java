package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;

public final class QueenBee  extends GamePiece {

     /** The Queen Bee's tile.   */
    private Tile tile = Tile.QUEEN_BEE;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.
     */
    public QueenBee(final Player colour) {
        super(colour);
        this.setTile(this.tile);
    }

    @Override
    public void move(final Point from, final Point to) {

    }
}
