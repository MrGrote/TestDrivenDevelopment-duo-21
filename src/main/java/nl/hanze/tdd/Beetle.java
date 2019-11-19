package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

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
    public boolean isLegalMove(final Point from, final Point to) {
        if (!Arrays.asList(this.board.getNeigbours(from)).contains(to)) {
            return false;
        }

        if (!this.board.canPush(from, to)) {
            return false;
        }

        this.board.pop(from);
        this.board.put(to, this);
        boolean result = this.board.isValidState();
        this.board.pop(to);
        this.board.put(from, this);
        return result;
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
