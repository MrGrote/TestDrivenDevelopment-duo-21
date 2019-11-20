package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public final class SoldierAnt  implements GamePiece {

     /** The Soldier Ant's tile.   */
    private final Tile tile = Tile.SOLDIER_ANT;
    /** The colour of the Soldier Ant. */
    private final Player colour;
    /** The board the soldier ant is on. */
    private final Board board;

    /**
     * Create a new Soldier Ant.
     * @param colour The colour of the tile.
     * @param board The board the ant exists on.
     */
    public SoldierAnt(final Player colour, final Board board) {
        this.colour = colour;
        this.board = board;
    }
    private boolean routeExists(final Point currentHex,
                                final Point destination,
                                final Board board) {
        Set<Point> visited = new HashSet<>();
        board.put(currentHex, this);
        boolean exists = routeExists(currentHex, destination, board, visited);
        board.pop(currentHex);
        return exists;
    }
    private boolean routeExists(final Point currentHex,
                                final Point destination,
                                final Board board,
                                final Set<Point> visited) {
        if (currentHex.equals(destination)) {
            return true;
        }
        if (visited.contains(currentHex)) {
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
                                                    visited);
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
    public boolean isLegalMove(final Point from, final Point to) {
        int maxNeighbours = 6;
        if (this.board.getOccupiedNeigbours(to).size() == maxNeighbours
                && !Arrays.asList(this.board.getNeigbours(from)).contains(to)) {
            return false;
        }
        if (from.equals(to) || this.board.getHexagon(to) != null) {
            return false;
        }
        if (!routeExists(from, to, this.board)) {
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
