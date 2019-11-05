package nl.hanze.tdd;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import nl.hanze.hive.Hive;
import static java.lang.Math.min;

/**
 * The main game class.
 */
class Game implements Hive {
    /** A List of pieces that can still be placed. */
    private List<GamePiece> pieces = new ArrayList<>();
    /** A Map of points and stacks of gamepiees representing the board. */
    private Map<Point, Stack<GamePiece>> field = new HashMap<>();
    /** The player whose turn it is. */
    private Player currentPlayer = Player.WHITE;

    /**
     * Create a game.
     */
    Game() {
        this.pieces.addAll(
            Arrays.asList(new nl.hanze.tdd.Player(Player.WHITE).getPieces()));
        this.pieces.addAll(
            Arrays.asList(new nl.hanze.tdd.Player(Player.BLACK).getPieces()));
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
     * Check wether there are any pieces on the board.
     * @return a boolean indicating wether the field is empty
     */
    public boolean isEmpty() {
        return this.field.isEmpty();
    }

    /**
     * Get the current player.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Get the opponent of the player.
     * @param player the player whose opponent to find
     * @return the opponent of player
     */
    private Player getOpponent(final Player player) {
        if (player == Player.WHITE) {
            return Player.BLACK;
        } else {
            return Player.WHITE;
        }
    }

    /**
     * Get the stack of gamepieces at location point.
     * @param point the location
     * @return the stack at location point
     */
    public Stack<GamePiece> getHexagon(final Point point) {
        return this.field.get(point);
    }

    @Override
    public void play(
        final Tile tile,
        final int q,
        final int r) throws IllegalMove {
        GamePiece piece = new GamePiece(this.currentPlayer, tile);
        Point point = new Point(q, r);
        if (!this.pieces.contains(piece)) {
            throw new IllegalMove();
        }
        if (!(this.getHexagon(point) == null)) {
            throw new IllegalMove();
        }

        if (!this.isEmpty()) {
            if (!this.hasNeighbours(point)) {
                throw new IllegalMove();
            }
        }
        if (bothPlayersPlayed() && hasClashingNeigbours(point)) {
                throw new IllegalMove();
        }
        this.pieces.remove(piece);
        this.put(piece, point);

        this.currentPlayer = getOpponent(this.currentPlayer);
    }

private boolean hasNeighbours(final Point point) {
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
     * @return wether there are any neigbours of an opposing colour
     */
    private boolean hasClashingNeigbours(final Point point) {
        Player opponent = getOpponent(this.currentPlayer);
        for (Point neigbour : this.getNeigbours(point)) {
            if (this.field.containsKey(neigbour)) {
                if (this.getHexagon(neigbour)
                              .peek()
                              .getColour()
                              .equals(opponent)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Put a piece on the board at the location of point.
     * @param piece the piece to place
     * @param point the location to place the piece at
     */
    public void put(final GamePiece piece, final Point point) {
        if (this.field.containsKey(point)) {
            this.getHexagon(point).push(piece);
        } else {
            Stack<GamePiece> stack = new Stack<>();
            stack.push(piece);
            this.field.put(point, stack);
        }
    }

    @Override
    public void move(final int fromQ,
                     final int fromR,
                     final int toQ,
                     final int toR) throws IllegalMove {
        if (this.pieces.contains(
            new GamePiece(this.currentPlayer, Tile.QUEEN_BEE)
            )) {
            throw new IllegalMove();
        }
        Point fromPoint = new Point(fromQ, fromR);
        Point toPoint = new Point(toQ, toR);

        try {
            GamePiece piece = this.getHexagon(fromPoint).peek();
            if (!piece.getColour().equals(this.currentPlayer)) {
                throw new IllegalMove();
            }
            this.getHexagon(fromPoint).pop();
            if (this.getHexagon(fromPoint).isEmpty()) {
                this.field.remove(fromPoint);
            }
            boolean found = hasNeighbours(toPoint);

            if (!found) {
                this.put(piece, fromPoint);
                throw new IllegalMove();
            }
            this.put(piece, toPoint);
        } catch (EmptyStackException | NullPointerException e) {
            throw new IllegalMove();
        }

        this.currentPlayer = getOpponent(this.currentPlayer);

    }

    @Override
    public void pass() throws IllegalMove {
        this.currentPlayer = getOpponent(this.currentPlayer);

    }

    @Override
    public boolean isWinner(final Player player) {
        for (Point coordinate : this.field.keySet()) {
            GamePiece piece = this.getHexagon(coordinate).peek();
            if (piece.getTile().equals(Tile.QUEEN_BEE)
            && piece.getColour().equals(getOpponent(player))) {
                Point[] neigbours = this.getNeigbours(coordinate);
                return this.field
                           .keySet()
                           .containsAll(Arrays.asList(neigbours));
            }
        }
        return false;
    }

    @Override
    public boolean isDraw() {
        return this.isWinner(this.currentPlayer)
            && this.isWinner(this.getOpponent(this.currentPlayer));
    }

    /**
     * Checks wether both players have played a piece.
     * @return true if both players have played a piece, else false
     */
    public boolean bothPlayersPlayed() {
        boolean black = false;
        boolean white = false;
        for (Point coordinate : this.field.keySet()) {
            GamePiece piece = this.getHexagon(coordinate).peek();
            if (piece.getColour().equals(Player.BLACK)) {
                black = true;
            } else {
                white = true;
            }
        }
        return black && white;
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

    /**
     * Check wether a stone can be pushed to a certain location.
     * @param from origin point
     * @param to destination point
     * @return boolean indicating wether the push can be done.
     */
    private boolean canPush(final Point from, final Point to) {
        Set<Point> toNeighbours = getOccupiedNeigbours(to);
        Set<Point> fromNeighbours = getOccupiedNeigbours(from);
        fromNeighbours.retainAll(toNeighbours);

        if (fromNeighbours.size() == 1) {
            return true;
        }
        if (fromNeighbours.size() == 0) {
            return false;
        }
        int minimum = fromNeighbours.stream()
                                      .map(this::getHeight)
                                      .min(Integer::compare)
                                      .get();
        return minimum <= min(this.getHeight(from) - 1, this.getHeight(to));
    }

    private Set<Point> getOccupiedNeigbours(final Point point) {
        Set<Point> occupiedNeighbours = new HashSet<>();
        Point[] neigbours = this.getNeigbours(point);
        for (Point neighbour: neigbours) {
            if (this.field.containsKey(neighbour)) {
                occupiedNeighbours.add(neighbour);
            }
        }
        return occupiedNeighbours;
    }

    /**
     * Push stone from origin to destination.
     * @param from origin point
     * @param to destination point
     * @throws IllegalMove when push obstructed
     */
    public void push(final Point from, final Point to) throws IllegalMove {
        if (canPush(from, to)) {
            move(from.x, from.y, to.x, to.y);
        } else {
            throw new IllegalMove();
        }
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
}
