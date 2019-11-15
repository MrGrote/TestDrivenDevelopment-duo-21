package nl.hanze.tdd;

import nl.hanze.hive.Hive.Player;
import java.awt.Point;
import java.util.Map;
import java.util.Stack;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

import static java.lang.Math.max;

public class Board {
    /** A Map of points and stacks of gamepiees representing the board. */
    private Map<Point, Stack<GamePiece>> field;

    Board() {
        this.field = new HashMap<>();
    }

    /**
     * Gets all the neighbours of point p.
     * @param p The point of which to get the neighbours
     * @return The neighbours of Point p
     */
    public Point[] getNeigbours(final Point p) {
        return new Point[] {
                new Point(p.x, p.y - 1),
                new Point(p.x - 1, p.y),
                new Point(p.x - 1, p.y + 1),
                new Point(p.x + 1, p.y - 1),
                new Point(p.x + 1, p.y),
                new Point(p.x, p.y + 1)
        };
    }

    /**
     * Remove a gamepiece from the board at point p.
     * @param p the point where to pop from
     * @return the piece at that point.
     */
    public GamePiece pop(final Point p) {
        GamePiece piece = this.getHexagon(p).pop();
        if (this.getHexagon(p).isEmpty()) {
            this.remove(p);
        }
        return piece;
    }

    /**
     * Check wether there are any pieces on the board.
     * @return a boolean indicating wether the field is empty
     */
    public boolean isEmpty() {
        return this.field.isEmpty();
    }

    /**
     * Get the stack of gamepieces at location point.
     * @param point the location
     * @return the stack at location point
     */
    public Stack<GamePiece> getHexagon(final Point point) {
        return this.field.get(point);
    }
    /** Check if the point has neighbours.
     * @param point the point to check
     * @return a boolean to indicate wether the point has neighbours or not*/
    protected boolean hasNeighbours(final Point point) {
        boolean found = false;
        Point[] neighbours = this.getNeigbours(point);
        for (Point neighbour : neighbours) {
            if (this.field.containsKey(neighbour)) {
                found = true;
                break;
            }
        }
        return found;
    }
    /**
     * Check if there are any neighbours of the opposing colour.
     * @param point the point that has it's neigbours checked
     * @param player the current player
     * @return wether there are any neigbours of an opposing colour
     */
    protected boolean hasClashingNeighbours(final Point point,
                                            final Player player) {
        Player opponent = Game.getOpponent(player);
        for (Point neigbour : this.getNeigbours(point)) {
            if (this.field.containsKey(neigbour)) {
                if (this.getHexagon(neigbour)
                        .peek()
                        .getColour()
                        == opponent) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Get the height of a hexagon.
     * @param point the location of the hexagon
     * @return the height of the hexagon
     */
    public int getHeight(final Point point) {
        if (this.field.containsKey(point)) {
            return this.getHexagon(point).size();
        } else {
            return 0;
        }
    }

    /**
     * checks wether the board is in a legal state.
     * @return a boolean indicating wether the board is in a legal state
     */
    public boolean isValidState() {
        Point first = (Point) this.field.keySet().toArray()[0];
        Set<Point> allPoints = new HashSet<>();
        Set<Point> newPoints = new HashSet<>();
        newPoints.add(first);
        allPoints.add(first);
        while (!newPoints.isEmpty()) {
            for (Point point : newPoints.toArray(new Point[0])) {
                newPoints.remove(point);
                for (Point neighbour : this.getNeigbours(point)) {
                    if (this.field.containsKey(neighbour)) {
                        if (!allPoints.contains(neighbour)) {
                            newPoints.add(neighbour);
                            allPoints.add(neighbour);
                        }
                    }
                }
            }

        }
        return this.field.keySet().equals(allPoints);
    }
    /** Removes a point from the board.
     * @param point the point on the field*/
    public void remove(final Point point) {
        this.field.remove(point);
    }

    /** Returns true if the field contains the point.
     * @param point the point on the field
     * @return a boolean indicating if the field contains the point*/
    public boolean containsKey(final Point point) {
        return (this.field.containsKey(point));
    }
    /** Puts a piece in the hashmap.
     * @param point the point on the field
     * @param piece the piece to put */
    public void put(final Point point, final GamePiece piece) {
        if (this.containsKey(point)) {
            this.getHexagon(point).push(piece);
        } else {
            Stack<GamePiece> stack = new Stack<>();
            stack.push(piece);
            this.field.put(point, stack);
        }
    }
    /** returns the keyset.
     * @return the keyset*/
    public Iterable<? extends Point> keySet() {
        return this.field.keySet();
    }

    /** Returns true if all neighbours are occupied.
     * @param neighbours all the neighbours of a point
     * @return a boolean indicating wether all neighbours are occupied or not*/
    public boolean allNeighboursInKeyset(final Point[] neighbours) {
        return this.field
                .keySet()
                .containsAll(Arrays.asList(neighbours));
    }

    /**
     * Check wether a stone can be pushed to a certain location.
     *
     * @param from origin point
     * @param to   destination point
     * @return boolean indicating wether the push can be done.
     */
    public boolean canPush(final Point from, final Point to) {
        Set<Point> toNeighbours = getOccupiedNeigbours(to);
        toNeighbours.add(to);
        Set<Point> fromNeighbours = getOccupiedNeigbours(from);
        fromNeighbours.retainAll(toNeighbours);

        if (fromNeighbours.size() == 1) {
            return true;
        } else if (fromNeighbours.size() == 0) {
            return false;
        }
        int minimum = fromNeighbours.stream()
                .map(this::getHeight)
                .min(Integer::compare)
                .get();
        return minimum <= max(this.getHeight(from),
                this.getHeight(to));
    }

    /**
     * Get a set of all the neighbours of the point that have gamepiece in them.
     * @param point the point from which to get the neighbours
     * @return a set containing all the neighbouring points that have stones in
     * them.
     */
    public Set<Point> getOccupiedNeigbours(final Point point) {
        Set<Point> occupiedNeighbours = new HashSet<>();
        Point[] neigbours = this.getNeigbours(point);
        for (Point neighbour : neigbours) {
            if (this.field.containsKey(neighbour)) {
                occupiedNeighbours.add(neighbour);
            }
        }
        return occupiedNeighbours;
    }

    /**
     * Check wether there is a straight line from a to b.
     * @param a a point on the path
     * @param b a point on the path
     * @return a boolean indicating wether there is a straight line
     */
    public static boolean isInStraightLine(final Point a, final Point b) {
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        if (dx == 0 || dy == 0 || dx == -dy) {
            return true;
        }
        return false;
    }
}
