package nl.hanze.tdd;

import java.awt.Point;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nl.hanze.hive.Hive;


/**
 * The main game class.
 */
class Game implements Hive {
    /** A List of pieces that can still be placed. */
    private Map<Player, Map<Tile, Integer>> pieces = new HashMap<>();
    /** The player whose turn it is. */
    private Player currentPlayer = Player.WHITE;
    /** Declaring the board. */
    private Board board;
    /** Factory for creating gamepieces. */
    private GamePieceFactory gamePieceFactory;
    /** Integer representing the total pieces per player. */
    private int totalPieces = 11;

    /**
     * Create a game.
     *
     * @param board The board the game is played on.
     */
    Game(final Board board) {
        this.pieces.put(Player.BLACK,
            new nl.hanze.tdd.Player(Player.BLACK).getPieces());
        this.pieces.put(Player.WHITE,
            new nl.hanze.tdd.Player(Player.WHITE).getPieces());
        this.board = board;
        this.gamePieceFactory = new GamePieceFactory(board);
    }

    /**
     * Get the current player.
     *
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Get the current board.
     *
     * @return the current board.
     */
    public Board getCurrentBoard() {
        return this.board;
    }

    /**
     * Get the opponent of the player.
     *
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
     *
     * @return wether or not the queen is played
     */
    public boolean queenPlayed() {
        return this.pieces.get(this.currentPlayer).get(Tile.QUEEN_BEE) == 0;
    }

    /**
     * get amount of pieces played by the current player.
     *
     * @return the amount of pieces played
     */
    private int amountPlayed() {
        int remainingPieces = this.pieces
                                  .get(this.currentPlayer)
                                  .values()
                                  .stream()
                                  .mapToInt(Integer::intValue)
                                  .sum();
        return totalPieces - remainingPieces;
    }

    @Override
    public void play(final Tile tile, final int q, final int r)
    throws IllegalMove {
        int maxPLayedBeforeQueen = 3;
        if (tile != Tile.QUEEN_BEE && !queenPlayed()
            && amountPlayed() >= maxPLayedBeforeQueen) {
            throw new IllegalMove();
        }
        GamePiece piece = gamePieceFactory.getGamePiece(currentPlayer, tile);

        Point point = new Point(q, r);
        if (this.pieces.get(this.currentPlayer).get(tile) == 0) {
            throw new IllegalMove();
        }
        if (!(this.board.getHexagon(point) == null)) {
            throw new IllegalMove();
        }

        if (!this.board.isEmpty()) {
            if (!this.board.hasNeighbours(point)) {
                throw new IllegalMove();
            }
        }
        if (bothPlayersPlayed()
            && this.board.hasClashingNeighbours(point, this.currentPlayer)) {
            throw new IllegalMove();
        }
        this.pieces.get(this.currentPlayer)
            .put(tile, this.pieces.get(this.currentPlayer).get(tile) - 1);
        this.put(piece, point);

        this.currentPlayer = getOpponent(this.currentPlayer);
    }

    /**
     * Put a piece on the board at the location of point.
     *
     * @param piece the piece to place
     * @param point the location to place the piece at
     */
    public void put(final GamePiece piece, final Point point) {
        this.board.put(point, piece);
    }

    @Override
    public void move(final int fromQ, final int fromR,
        final int toQ, final int toR)throws IllegalMove {

        Point fromPoint = new Point(fromQ, fromR);
        Point toPoint = new Point(toQ, toR);

        try {
            GamePiece piece = this.board.getHexagon(fromPoint).peek();

            if (!queenPlayed() || piece.getColour() != currentPlayer) {
                throw new IllegalMove();
            }

            Set<Point> neigbhours = this.board.getOccupiedNeigbours(toPoint);
            neigbhours.remove(fromPoint);
            if (neigbhours.size() == 0) {
                throw new IllegalMove();
            }

            if (piece.isLegalMove(fromPoint, toPoint)) {
            this.board.pop(fromPoint);
            this.board.put(toPoint, piece);
            } else {
                throw new IllegalMove();
            }
        } catch (NullPointerException e) {
                throw new IllegalMove();
            }

        this.currentPlayer = getOpponent(this.currentPlayer);

    }
    private boolean hasPiecesLeft() {
        return totalPieces - amountPlayed() != 0;
    }
    private Set<Point> getBorder() {
        Set<Point> points = this.board.keySet();
        Set<Point> neighbours = new HashSet<>();
        for (Point point : points) {
            neighbours.addAll(Arrays.asList(this.board.getNeigbours(point)));
        }
        neighbours.removeAll(points);
        return neighbours;
    }
    private boolean canPlay() {
        Set<Point> border = getBorder();

        if (!hasPiecesLeft()) {
            return false;
        }
        if (this.board.keySet().size() <= 1) {
            return true;
        }
        for (Point point : border) {
            if (this.board.getOccupiedNeigbours(point)
                    .stream().anyMatch(p -> this.board
                             .getHexagon(p)
                             .peek()
                             .getColour() != currentPlayer)) {
                return true;
            }

        }
        return false;
    }
    private boolean canMove() {
        Set<Point> border = getBorder();
        for (Point point : new HashSet<>(this.board.keySet())) {
            for (Point destination : border) {
                if (this.board.getHexagon(point)
                              .peek()
                              .isLegalMove(point, destination)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void pass() throws IllegalMove {
        if (!canPlay() && !canMove()) {
            this.currentPlayer = getOpponent(this.currentPlayer);
        } else {
            throw new IllegalMove();
        }


    }

    @Override
    public boolean isWinner(final Player player) {
        for (Point coordinate : this.board.keySet()) {
            GamePiece piece = this.board.getHexagon(coordinate).peek();
            if (piece.getTile() == Tile.QUEEN_BEE
                && piece.getColour() == getOpponent(player)) {
                Point[] neighbours = this.board.getNeigbours(coordinate);
                return this.board.allNeighboursInKeyset(neighbours);
            }
        }
        return false;
    }

    @Override
    public boolean isDraw() {
        return this.isWinner(this.currentPlayer)
        &&  this.isWinner(getOpponent(this.currentPlayer));
    }

    /**
     * Checks wether both players have played a piece.
     *
     * @return true if both players have played a piece, else false
     */
    public boolean bothPlayersPlayed() {
        boolean black = false;
        boolean white = false;
        for (Point coordinate : this.board.keySet()) {
            GamePiece piece = this.board.getHexagon(coordinate).peek();
            if (piece.getColour() == Player.BLACK) {
                black = true;
            } else {
                white = true;
            }
        }
        return black && white;
    }



    /**
     * Push stone from origin to destination.
     *
     * @param from origin point
     * @param to   destination point
     * @throws IllegalMove when push obstructed
     */
    public void push(final Point from, final Point to) throws IllegalMove {
        if (this.board.canPush(from, to)) {
            move(from.x, from.y, to.x, to.y);
        } else {
            throw new IllegalMove();
        }
    }

}
