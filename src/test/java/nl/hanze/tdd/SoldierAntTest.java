package nl.hanze.tdd;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
}

//    @Test
//    void givenSoldierAntWhenMoveSoldierAntThenSoldierAntMoved() {
//
//    }


