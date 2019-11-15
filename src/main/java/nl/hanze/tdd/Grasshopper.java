package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;

import java.awt.Point;
import java.util.Arrays;

public final class Grasshopper  implements GamePiece {

     /** The Queen Bee's tile.   */
    private final Tile tile = Tile.GRASSHOPPER;
    /** The colour of the Beetle. */
    private final Player colour;
    /** The board the beetle is on. */
    private final Board board;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.
     * @param board The board the Grassphopper is on.
     */
    public Grasshopper(final Player colour, final Board board) {
        this.colour = colour;
        this.board = board;
    }
    private static int[] getDirections(final Point from, final Point to) {
        int dx = to.x - from.x;
        int dy = to.y - from.y;
        if (dx == 0 && dy > 0) {
            return new int[] {0, 1};
        }
        else if (dx == 0 && dy < 0) {
            return new int[] {0, -1};
        }
        else if (dx > 0 && dy == 0) {
            return new int[] {1, 0};
        }
        else if (dx < 0 && dy == 0) {
            return new int[] {-1, 0};
        }
        else if (dx == -dy && dx > 0) {
            return new int[] {1, -1};
        }
        else if (dx == -dy && dx < 0) {
            return new int[] {-1, 1};
        }
        throw new IllegalArgumentException();
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
        if (Arrays.asList(this.board.getNeigbours(from)).contains(to)
                || this.board.getHexagon(to) != null
                || !Board.isInStraightLine(from, to)
                || from.equals(to)) {
            return false;
        }
        int[] direction = getDirections(from, to);
        Point currentPoint = new Point(from.x + direction[0], from.y + direction[1]);
        while (!currentPoint.equals(to)) {
            if (this.board.getHexagon(currentPoint) == null) {
                return false;
            }
            currentPoint = new Point(currentPoint.x + direction[0], currentPoint.y + direction[1]);
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
        if (other instanceof Grasshopper) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.tile.hashCode() + this.colour.hashCode();
    }
}
