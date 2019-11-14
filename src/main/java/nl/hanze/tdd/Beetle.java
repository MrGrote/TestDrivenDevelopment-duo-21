package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;

import java.awt.Point;
import java.util.Arrays;

public final class Beetle  implements GamePiece {

     /** The Queen Bee's tile.   */
    private final Tile tile = Tile.BEETLE;
    /** The colour of the Beetle. */
    private final Player colour;
    /** The board the beetle is on. */
    private final Board board;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.
     * @param board The board the  beetle is on
     */
    public Beetle(final Player colour, final Board board) {
        this.colour = colour;
        this.board = board;
    }

    @Override
    public void move(final Point from, final Point to) throws IllegalMove {
        if (!Arrays.asList(this.board.getNeigbours(from)).contains(to)) {
            throw new IllegalMove("Illegal destination for Beetle at "
                + from.toString());
        }
        if (this.board.canPush(from, to)) {
            this.board.put(to, this);
        } else {
            throw new IllegalMove();
        }

    }

    @Override
    public Player getColour() {
        return this.colour;
    }

    @Override
    public Tile getTile() {
        return this.tile;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Beetle) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.tile.hashCode() + this.colour.hashCode();
    }
}
