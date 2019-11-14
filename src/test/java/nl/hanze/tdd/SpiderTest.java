package nl.hanze.tdd;

import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;


import static org.junit.jupiter.api.Assertions.*;

public class SpiderTest {
    private Game game;
    private Board board;

    @BeforeEach
    void createGame() {
        board = new Board();
        game = new Game(board);
    }

    @Test
    void givenSpiderWhenMoveSpiderOntoOtherStoneTHenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.SPIDER, 0, -1);
            game.play(Tile.SOLDIER_ANT, 0, 2);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, -1, 0, 0);
        });
    }

    @Test
    void givenSpiderWhenMoveSpiderFromStartToStartTHenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.SPIDER, 0, -1);
            game.play(Tile.SOLDIER_ANT, 0, 2);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, -1, 0, -1);
        });
    }
    @Test
    void givenEnclosedSpiderAndDestinationOutisdeEnclosureWhenMoveToDestinationThenIllegalMove() {
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(1, 0));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(-1, 1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(-1, 0));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 0));
        try {
            game.play(Tile.QUEEN_BEE, 0, -1);
            game.play(Tile.QUEEN_BEE, 1, -1);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 0, -1, -1);
        });
    }

    @Test
    void givenSpiderWhenMoveFourThenIllegalMove() {
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 2));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 3));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, -2));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, -3));
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, -1);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 3, 1, -1);
        });
    }

    @Test
    void givenSpiderWhenMoveThreeThenMoved() {
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 2));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 3));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, -2));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, -3));
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, -1);
            game.move(0, 3, 1, 0);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertEquals(new Spider(Player.WHITE, game.getCurrentBoard()),game.getCurrentBoard().getHexagon(new Point(1, 0)).peek());
    }

    @Test
    void givenSPiderWhenMoveOntoOtherTileTHenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, -1);
            game.play(Tile.SPIDER, 0, 1);
            game.play(Tile.SPIDER, 0, -2);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 1, 0, 0);
        });
    }
}
