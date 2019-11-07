package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;

public final class Spider extends GamePiece {

    /** The Spider Tile. */
    private final Tile tile = Tile.SPIDER;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.
     */
    public Spider(final Player colour) {
        super(colour);
        this.setTile(this.tile);
    }

    @Override
    public void move(final Point from, final Point to) {

    }
}
