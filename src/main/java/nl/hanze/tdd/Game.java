package nl.hanze.tdd;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;

class Game implements nl.hanze.hive.Hive {
    private ArrayList<GamePiece> pieces = new ArrayList<>();
    private HashMap<Point, Stack<GamePiece>> field = new HashMap<>(22, 1);
    private Player currentPlayer = Player.WHITE;

    public Game() {
        this.pieces.addAll(Arrays.asList(new nl.hanze.tdd.Player(Player.WHITE).getPieces()));
        this.pieces.addAll(Arrays.asList(new nl.hanze.tdd.Player(Player.BLACK).getPieces()));
    }

    public Point[] getNeigbours(Point p) {
        Point[] ret = {new Point(p.x, p.y - 1), new Point(p.x - 1, p.y), new Point(p.x - 1, p.y + 1),
                new Point(p.x + 1, p.y - 1), new Point(p.x + 1, p.y), new Point(p.x, p.y + 1)};
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

    public Stack<GamePiece> getHexagon(Point point) {
        return this.field.get(point);
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
        if (pieces.contains(new GamePiece(this.currentPlayer, Tile.QUEEN_BEE))) {
            throw new IllegalMove();
        }
        Point point = new Point(fromQ, fromR);
        try {
            GamePiece piece = this.field.get(point).peek();
            if (!piece.getColour().equals(this.currentPlayer)) {
                throw new IllegalMove();
            }
            this.field.get(point).pop();
            if (this.field.get(point).isEmpty()) {
                this.field.remove(point);
            }
            ;
            boolean found = false;
            Point[] neighbours = this.getNeigbours(new Point(toQ, toR));
            for (Point neighbour : neighbours) {
                if (this.field.containsKey(neighbour)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.put(piece, point);
                throw new IllegalMove();
            }
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

    private boolean canPush(Point from, Point to) {
        Set<Point> sharedNeighbours = new HashSet<>();
        Set<Point> fromNeighbours = new HashSet<>();

        for (Point neighbour : this.getNeigbours(from)) {
            if (this.field.containsKey(neighbour)) {
                fromNeighbours.add(neighbour);
            }
        }
        for (Point neighbour : this.getNeigbours(to)) {
            if (this.field.containsKey(neighbour)) {
                if (fromNeighbours.contains(neighbour)) {
                    sharedNeighbours.add(neighbour);
                }
            }
        }
        if (sharedNeighbours.size() == 1) {
            return  true;
        }
        if (sharedNeighbours.size() == 0) {
            return  false;
        }
        int minimum = sharedNeighbours.stream().map(this::getHeight).min(Integer::compare).get();
        return minimum <= min(this.getHeight(from) - 1, this.getHeight(to));
    }

    public void push(Point from, Point to) throws IllegalMove {
        if (canPush(from, to)) {
            move(from.x, from.y, to.x, to.y);
        } else {
            throw new IllegalMove();
        }
    }

    public int getHeight(Point point) {
        if (this.field.containsKey(point)) {
            return this.field.get(point).size();
        } else {
            return 0;
        }

    }
}