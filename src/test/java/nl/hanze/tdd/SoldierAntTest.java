package nl.hanze.tdd;

import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class SoldierAntTest {
    private Game game;
    private Board board;

    @BeforeEach
    void createGame() {
        board = new Board();
        game = new Game(board);
    }

    @Test
    void givenSoldierAntWhenMoveSoldierAntOntoOtherStoneTHenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.SOLDIER_ANT, 0, -1);
            game.play(Tile.SOLDIER_ANT, 0, 2);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, -1, 0, 0);
        });
    }

    @Test
    void givenSoldierAntWhenMoveSoldierAntFromStartToStartTHenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.SOLDIER_ANT, 0, -1);
            game.play(Tile.SOLDIER_ANT, 0, 2);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, -1, 0, -1);
        });
    }


    @Test
    void givenSoldierAntAndEnclosedDestinationWHenMoveToEnclosedDestinationThenIllegalMove() {
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(1, 0));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(-1, 1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(-1, 0));
        game.put(new SoldierAnt(Player.WHITE, game.getCurrentBoard()), new Point(-1, -1));
        try {
            game.play(Tile.QUEEN_BEE, 0, -1);
            game.play(Tile.QUEEN_BEE, 1, -1);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(-1, -1, 0, 0);
        });
    }
    @Test
    void givenSoldierAntAndPossibleRouteWHenMoveThenMoved() {
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, 2));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, -1));
        game.put(new Spider(Player.WHITE, game.getCurrentBoard()), new Point(0, -2));
        game.put(new SoldierAnt(Player.WHITE, game.getCurrentBoard()), new Point(0, -3));
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.move(0, -3, 0, 3);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertEquals(new SoldierAnt(Player.WHITE, game.getCurrentBoard()), game.getCurrentBoard().getHexagon(new Point(0, 3)).peek());

    }
}




