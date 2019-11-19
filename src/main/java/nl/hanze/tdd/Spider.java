package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public final class Spider  implements GamePiece {

    /** The length of a spiders path. */
    private static final int PATH_LENGTH = 3;

     /** The Queen Bee's tile.   */
    private final Tile tile = Tile.SPIDER;
    /** The colour of the Beetle. */
    private final Player colour;
    /** The board the beetle is on. */
    private final Board board;

    /**
     * Create a new QueenBee.
     * @param colour The colour of the tile.
     * @param board The board the spider is on.
     */
    public Spider(final Player colour, final Board board) {
        this.colour = colour;
        this.board = board;
    }

    private boolean routeExists(final Point currentHex,
            final Point destination) {
        Set<Point> visited = new HashSet<>();
        boolean exists = routeExists(currentHex, destination, visited,
            PATH_LENGTH);
        return exists;
    }
    private boolean routeExists(final Point currentHex, final Point destination,
            final Set<Point> visited, final int depth) {

        if (currentHex.equals(destination) && depth == 0) {
            return true;
        }
        if (currentHex.equals(destination) || depth == 0
            || visited.contains(currentHex)) {
            return false;
        }

        for (Point neighbour : this.board.getNeigbours(currentHex)) {
            if (board.getHexagon(neighbour) == null
                    && board.canPush(currentHex, neighbour)) {
                board.pop(currentHex);
                board.put(neighbour, this);
                visited.add(currentHex);
                boolean routeFound = routeExists(neighbour,
                        destination, visited, depth - 1);
                board.put(currentHex, this);
                board.pop(neighbour);
                visited.remove(currentHex);
                if (routeFound) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isLegalMove(final Point from, final Point to) {
        if (from.equals(to) || this.board.getHexagon(to) != null) {
            return false;
        }
        if (!routeExists(from, to)) {
            return false;
        }
        if (this.board.getHexagon(from).peek().getTile() == Tile.SPIDER) {
            boolean t = true;
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
        if (other instanceof Spider) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.tile.hashCode() + this.colour.hashCode();
    }
}
