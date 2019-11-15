package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;

import java.awt.Point;
import java.util.Arrays;

public final class QueenBee  implements GamePiece {

     /** The Queen Bee's tile.   */
    private final Tile tile = Tile.QUEEN_BEE;
    /** The colour of the Beetle. */
    private final Player colour;
    /** The board the beetle is on. */
    private final Board board;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.'
     * @param board The board the QueenBee is on.'
     */
    public QueenBee(final Player colour, final Board board) {
        this.colour = colour;
        this.board = board;
    }

    @Override
    public void move(final Point from, final Point to) throws IllegalMove {

        if (canMove(from, to)) {
            this.board.put(to, this);
        } else {
            throw new IllegalMove();
        }
    }

    @Override
    public boolean canMove(final Point from, final Point to) {
        if (!Arrays.asList(this.board.getNeigbours(from)).contains(to)
                || this.board.getHexagon(to) != null) {
            return false;
        }
        if (!this.board.canPush(from, to)) {
            return false;
        }
        return true;
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
        if (other instanceof QueenBee) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.tile.hashCode() + this.colour.hashCode();
    }
}
