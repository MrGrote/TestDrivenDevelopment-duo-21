package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Tile;

import java.awt.Point;

class GameTests {
    @Test
    void whenGetNeighboursThenLengthSix() {
        Game game = new Game();
        Point[] neigbours = game.getNeigbours(new Point());
        assertEquals(6, neigbours.length);
    }

    @Test
    void whenGetNeigboursThenCorrectNeighbours() {
        Game game = new Game();
        Point[] neigbours = game.getNeigbours(new Point());
        Point[] knownNeigbours = { new Point(0, -1), new Point(-1, 0), new Point(-1, 1), new Point(1, -1),
                new Point(1, 0), new Point(0, 1) };
        outside: for (Point knownNeigbour : knownNeigbours) {
            for (Point neigbour : neigbours) {
                if (neigbour.equals(knownNeigbour)) {
                    continue outside;
                }
            }
            fail("Couldn't find known neibour in neigbours");
        }
    }

    @Test
    void whenNewBoardThenEmpty() {
        Game game = new Game();
        assertTrue(game.isEmpty());
    }

    @Test
    void whenNewGameThenWhiteActive() {
        Game game = new Game();
        assertEquals(nl.hanze.hive.Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenMadeMoveNextActive() {
        Game game = new Game();
        try {
            game.move(0, 0, 1, 1);
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.BLACK, game.getCurrentPlayer());
        try {
            game.move(0, 0, 1, 1);
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenMadePlayNextActive() {
        Game game = new Game();
        try {
            game.play(Tile.BEETLE, 0, 0);
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.BLACK, game.getCurrentPlayer());
        try {
            game.play(Tile.BEETLE, 0, 1);
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenMadePassNextActive() {
        Game game = new Game();
        try {
            game.pass();
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.BLACK, game.getCurrentPlayer());
        try {
            game.pass();
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenPlayPlayedPieceThenIllegalMove() {
        Game game = new Game();
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.pass();
        } catch (IllegalMove e) {
            fail();
        }
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.QUEEN_BEE, 0, 1);
        });
    }

    @Test
    void whenPlayOccupiedSquareThenIllegalMove() {
        Game game = new Game();
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
        } catch (IllegalMove e) {
            fail();
        }
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.SOLDIER_ANT, 0, 0);
        });
    }

    @Test
    void whenPiecePlayedNotConnectedThenIllegalMove() {
        Game game = new Game();
        try{
            game.play(Tile.QUEEN_BEE, 0, 0);}
            catch (IllegalMove e){
                fail();
            }
        assertThrows(IllegalMove.class, () -> {game.play(Tile.QUEEN_BEE, 10, 10);});
    }
}