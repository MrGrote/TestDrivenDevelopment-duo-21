package nl.hanze.tdd;

import java.awt.Point;
import java.util.Stack;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

class Game implements nl.hanze.hive.Hive {
    private ArrayList<GamePiece> pieces = new ArrayList<>();
    private HashMap<Point, Stack<GamePiece>> field = new HashMap<>(22, 1);
    private Player currentPlayer = Player.WHITE;

    public Game() {
        this.pieces.addAll(Arrays.asList(new nl.hanze.tdd.Player(Player.WHITE).getPieces()));
        this.pieces.addAll(Arrays.asList(new nl.hanze.tdd.Player(Player.BLACK).getPieces()));
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
        }
        if (!(this.field.get(point) == null)) {
            throw new IllegalMove();
        }
        if (!this.isEmpty()) {
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
        if (bothPlayersPlayed()) {
            Player opponent = getOpponent(this.currentPlayer);
            for (Point neigbour : this.getNeigbours(point)) {
                if (this.field.containsKey(neigbour)) {
                    if (this.field.get(neigbour).peek().getColour().equals(opponent)) {
                        throw new IllegalMove();
                    }
                }
            }
        }
        this.pieces.remove(piece);
        this.put(piece, point);

        this.currentPlayer = getOpponent(this.currentPlayer);
    }

    public void put(GamePiece piece, Point point) {
        if (this.field.keySet().contains(point)) {
            this.field.get(point).push(piece);
        } else {
            Stack<GamePiece> stack = new Stack<>();
            stack.push(piece);
            this.field.put(point, stack);
        }
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        if (!pieces.contains(new GamePiece(this.currentPlayer, Tile.QUEEN_BEE))){
            throw new IllegalMove();
        }
        try {
            GamePiece piece = this.field.get(new Point(fromQ, fromR)).peek();
            if (!piece.getColour().equals(this.currentPlayer)) {
                throw new IllegalMove();
            }
            this.field.get(new Point(fromQ, fromR)).pop();
            this.put(piece, new Point(toQ, toR));
        } catch (EmptyStackException e) {
            throw new IllegalMove();
        } catch (NullPointerException e) {
            throw new IllegalMove();
        }
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