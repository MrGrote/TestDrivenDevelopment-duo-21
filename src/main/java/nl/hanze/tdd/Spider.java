package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public final class Spider  implements GamePiece {

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
                                final Point destination,
                                final Board board) {
        Set<Point> visited = new HashSet<>();
        board.put(currentHex, this);
        int maxDepth = 3;
        boolean exists = routeExists(currentHex,
                                        destination,
                                        board,
                                        visited,
                                        maxDepth);
        board.pop(currentHex);
        return exists;
    }
    private boolean routeExists(final Point currentHex,
                                final Point destination,
                                final Board board,
                                final Set<Point> visited,
                                final int depth) {
        if (currentHex.equals(destination)) {
            return true;
        }
        if (visited.contains(currentHex) || depth == 0) {
            return false;
        }
        visited.add(currentHex);
        for (Point neighbour : this.board.getNeigbours(currentHex)) {
            if (board.canPush(currentHex, neighbour)) {
                board.pop(currentHex);
                board.put(neighbour, this);
                boolean routeFound = routeExists(neighbour,
                        destination,
                        board,
                        visited,
                        depth - 1);
                board.put(currentHex, this);
                board.pop(neighbour);
                if (routeFound) {
                    return true;
                }
            }
        }


        return false;
    }
    @Override
    public void move(final Point from, final Point to) throws IllegalMove {
        if (from.equals(to) || this.board.getHexagon(to) != null) {
            throw new IllegalMove("Illegal destination for Beetle at "
                + from.toString());
        }
        if (routeExists(from, to, this.board)) {
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
