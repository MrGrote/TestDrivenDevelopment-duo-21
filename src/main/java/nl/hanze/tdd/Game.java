package nl.hanze.tdd;

import java.awt.Point;
import java.util.Stack;
import java.util.function.BooleanSupplier;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

class Game implements nl.hanze.hive.Hive {
    private ArrayList<GamePiece> pieces = new ArrayList<>();
    private HashMap<Point, Stack<GamePiece>> field = new HashMap<>(22, 1);
    private Player currentPlayer = Player.WHITE;
    private nl.hanze.tdd.Player[] players = new nl.hanze.tdd.Player[2];

    public Game() {
        pieces.addAll(Arrays.asList(new nl.hanze.tdd.Player(Player.WHITE).getPieces()));
        pieces.addAll(Arrays.asList(new nl.hanze.tdd.Player(Player.BLACK).getPieces()));
    }

    public Point[] getNeigbours(Point p) {
        Point[] ret = { new Point(p.x, p.y - 1), new Point(p.x - 1, p.y), new Point(p.x - 1, p.y + 1),
                new Point(p.x + 1, p.y - 1), new Point(p.x + 1, p.y), new Point(p.x, p.y + 1) };
        return ret;
    }

    public boolean isEmpty() {
        return this.field.isEmpty();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private Player getOpponent(Player player) {
        if (player == Player.WHITE) {
            return Player.BLACK;
        } else {
            return Player.WHITE;
        }
    }

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        GamePiece piece = new GamePiece(this.currentPlayer, tile);
        Point point = new Point(q, r);
        if (!this.pieces.contains(piece)) {
            throw new IllegalMove();
        } else if (!(this.field.get(point) == null)) {
            throw new IllegalMove();
        } else if (!this.isEmpty()) {
            boolean found = false;
            Point[] neighbours = this.getNeigbours(point);
            for (Point neighbour : neighbours) {
                if (this.field.containsKey(neighbour)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalMove();
            }
        }

        this.pieces.remove(piece);

        Stack<GamePiece> stack = new Stack<>();
        stack.push(piece);
        field.put(point, stack);

        this.currentPlayer = getOpponent(this.currentPlayer);
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        this.currentPlayer = getOpponent(this.currentPlayer);

    }

    @Override
    public void pass() throws IllegalMove {
        this.currentPlayer = getOpponent(this.currentPlayer);

    }

    @Override
    public boolean isWinner(Player player) {
        for (Point coordinate : this.field.keySet()) {
            GamePiece piece = this.field.get(coordinate).peek();
            if (piece.getTile().equals(Tile.QUEEN_BEE) && piece.getColour().equals(getOpponent(player))) {
                Point[] neigbours = this.getNeigbours(coordinate);
                return this.field.keySet().containsAll(Arrays.asList(neigbours));
            }
        }
        return false;
    }

    @Override
    public boolean isDraw() {

        return this.isWinner(this.currentPlayer) && this.isWinner(this.getOpponent(this.currentPlayer));
    }

    public boolean bothPlayersPlayed() {
        boolean black = false;
        boolean white = false;
        for (Point coordinate : this.field.keySet()) {
            GamePiece piece = this.field.get(coordinate).peek();
            if (piece.getColour().equals(Player.BLACK)) {
                black = true;
            } else {
                white = true;
            }
        }
        return black && white;
    }

}