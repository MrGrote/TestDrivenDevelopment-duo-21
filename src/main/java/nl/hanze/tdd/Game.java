package nl.hanze.tdd;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
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
    /** The player whose turn it is. */
    private Player currentPlayer = Player.WHITE;
    /** Declaring the board.*/
    private Board field;

    /**
     * Create a game.
     */
    Game() {
        this.pieces.addAll(
            Arrays.asList(new nl.hanze.tdd.Player(Player.WHITE).getPieces()));
        this.pieces.addAll(
            Arrays.asList(new nl.hanze.tdd.Player(Player.BLACK).getPieces()));
        this.field = new Board();
    }

    /**
     * Get the current player.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Get the current board.
     * @return the current board.
     */
    public Board getCurrentBoard() {
        return this.field; }

    /**
     * Get the opponent of the player.
     * @param player the player whose opponent to find
     * @return the opponent of player
     */
    static Player getOpponent(final Player player) {
        if (player == Player.WHITE) {
            return Player.BLACK;
        } else {
            return Player.WHITE;
        }
    }

    /**
     * check if the queen is played or not.
     * @return wether or not the queen is played
     */
    public boolean queenPlayed() {
        return !this.pieces.contains(new QueenBee(this.currentPlayer));
    }

    /**
     * get amount of pieces played by the current player.
     * @return the amount of pieces played
     */
    private int amountPlayed() {
        int temp = (int) this.pieces.stream()
                .map(GamePiece::getColour)
                .filter((colour) -> colour.equals(this.currentPlayer))
                .count();
        int totalPieces = 11;
        return totalPieces - temp;
    }

    @Override
    public void play(
        final Tile tile,
        final int q,
        final int r) throws IllegalMove {
        int maxPLayedBeforeQueen = 3;
        if (tile != Tile.QUEEN_BEE && !queenPlayed() && amountPlayed() >= maxPLayedBeforeQueen) {
            throw new IllegalMove();
        }
        GamePiece piece;
        switch (tile) {
            case BEETLE:
                piece = new Beetle(this.currentPlayer);
                break;
            case GRASSHOPPER:
                piece = new Grasshopper(this.currentPlayer);
                break;
            case QUEEN_BEE:
                piece = new QueenBee(this.currentPlayer);
                break;
            case SPIDER:
                piece = new Spider(this.currentPlayer);
                break;
            case SOLDIER_ANT:
                piece = new SoldierAnt(this.currentPlayer);
                break;
            default:
                throw new IllegalMove();
        }

        Point point = new Point(q, r);
        if (!this.pieces.contains(piece)) {
            throw new IllegalMove();
        }
        if (!(this.field.getHexagon(point) == null)) {
            throw new IllegalMove();
        }

        if (!this.field.isEmpty()) {
            if (!this.field.hasNeighbours(point)) {
                throw new IllegalMove();
            }
        }
        if (bothPlayersPlayed() && this.field
                .hasClashingNeighbours(point, this.currentPlayer)) {
                throw new IllegalMove();
        }
        this.pieces.remove(piece);
        this.put(piece, point);

        this.currentPlayer = getOpponent(this.currentPlayer);
    }



    /**
     * Put a piece on the board at the location of point.
     * @param piece the piece to place
     * @param point the location to place the piece at
     */
    public void put(final GamePiece piece, final Point point) {
        if (this.field.containsKey(point)) {
            this.field.getHexagon(point).push(piece);
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

        Point fromPoint = new Point(fromQ, fromR);
        Point toPoint = new Point(toQ, toR);
        if (!queenPlayed()) {
            throw new IllegalMove();
        }
        try {
            GamePiece piece = this.field.getHexagon(fromPoint).peek();
            if (!piece.getColour().equals(this.currentPlayer)) {
                throw new IllegalMove();
            }
            this.field.getHexagon(fromPoint).pop();
            if (this.field.getHexagon(fromPoint).isEmpty()) {
                this.field.remove(fromPoint);
            }
            boolean found = this.field.hasNeighbours(toPoint);

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
            GamePiece piece = this.field.getHexagon(coordinate).peek();
            if (piece.getTile().equals(Tile.QUEEN_BEE)
            && piece.getColour().equals(getOpponent(player))) {
                Point[] neighbours = this.field.getNeigbours(coordinate);
                return this.field.allNeighboursInKeyset(neighbours);
            }
        }
        return false;
    }

    @Override
    public boolean isDraw() {
        return this.isWinner(this.currentPlayer)
            && this.isWinner(getOpponent(this.currentPlayer));
    }

    /**
     * Checks wether both players have played a piece.
     * @return true if both players have played a piece, else false
     */
    public boolean bothPlayersPlayed() {
        boolean black = false;
        boolean white = false;
        for (Point coordinate : this.field.keySet()) {
            GamePiece piece = this.field.getHexagon(coordinate).peek();
            if (piece.getColour().equals(Player.BLACK)) {
                black = true;
            } else {
                white = true;
            }
        }
        return black && white;
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
                                      .map(this.field::getHeight)
                                      .min(Integer::compare)
                                      .get();
        return minimum <= min(this.field.getHeight(from) - 1,
                this.field.getHeight(to));
    }

    private Set<Point> getOccupiedNeigbours(final Point point) {
        Set<Point> occupiedNeighbours = new HashSet<>();
        Point[] neigbours = this.field.getNeigbours(point);
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



}
