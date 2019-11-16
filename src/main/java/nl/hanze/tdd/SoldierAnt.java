package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;

import java.awt.Point;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public final class SoldierAnt  implements GamePiece {
    /** Max possible neighbours to a soldier ant.  */
    private static final int MAX_NEIGHBOURS = 6;

     /** The Soldier Ant's tile.   */
    private final Tile tile = Tile.SOLDIER_ANT;
    /** The colour of the Soldier Ant. */
    private final Player colour;
    /** The board the soldier ant is on. */
    private final Board board;

    /**
     * Create a new SoldierAnt.
     * @param colour The colour of the tile.
     * @param board The board the ant exists on.
     */
    public SoldierAnt(final Player colour, final Board board) {
        this.colour = colour;
        this.board = board;
    }
    private boolean routeExists(final Point currentHex,
        final Point destination, final Board board) {
        final Set<Point> visited = new HashSet<>();
        board.put(currentHex, this);
        final boolean exists = routeExists(currentHex, destination,
            board, visited);
        board.pop(currentHex);
        return exists;
    }

    private boolean routeExists(final Point currentHex,
        final Point destination, final Board board,
            final Set<Point> visited) {
        if (currentHex.equals(destination)) {
            return true;
        }
        if (visited.contains(currentHex)) {
            return false;
        }
        visited.add(currentHex);
        for (final Point neighbour : this.board.getNeigbours(currentHex)) {
            if (board.canPush(currentHex, neighbour)) {
                board.pop(currentHex);
                board.put(neighbour, this);
                final boolean routeFound = routeExists(neighbour, destination,
                    board, visited);
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
        if (canMove(from, to)) {
            this.board.put(to, this);
        } else {
            throw new IllegalMove();
        }
    }

    @Override
    public boolean canMove(final Point from, final Point to) {
        if (this.board.getOccupiedNeigbours(to).size() == MAX_NEIGHBOURS
                && !Arrays.asList(this.board.getNeigbours(from)).contains(to)) {
            return false;
        }
        if (from.equals(to) || this.board.getHexagon(to) != null) {
            return false;
        }
        if (!routeExists(from, to, this.board)) {
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
        if (other instanceof SoldierAnt) {
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.tile.hashCode() + this.colour.hashCode();
    }
}
