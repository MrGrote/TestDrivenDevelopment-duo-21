package nl.hanze.tdd;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class BeetleTest {
    private Game game;
    private Board board;

    @BeforeEach
    void createGame() {
        board = new Board();
        game = new Game(board);
    }

    @Test
    void givenBeetleWhenMoveBeetleThenBeetleMovedToTarget() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.BEETLE, 0, -1);
            game.play(Tile.BEETLE, 0, 2);
            game.move(0, -1, 1, -1);

        } catch (IllegalMove e){
            fail(e);
        }
        assertEquals(new Beetle(Player.WHITE, game.getCurrentBoard()),
                game.getCurrentBoard().getHexagon(new Point(1, -1)).peek());


    }
    @Test
    void givenBeetleWhenMoveBeetleToFarThenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.BEETLE, 0, -1);
            game.play(Tile.BEETLE, 0, 2);
        } catch (IllegalMove e){
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, -1, 1, 0);
        });
    }

    @Test
    void givenBeetleWhenMoveBeetleOntoOtherStoneThenBeetleMoved () {
        game.put(new Beetle(Player.WHITE, game.getCurrentBoard()), new Point(0, -1));
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 1, -1);
            game.pass();
            game.play(Tile.BEETLE, 2, -1);

            game.move(0, -1, 1, -1);

        } catch (IllegalMove e){
            fail(e);
        }
        assertEquals(new Beetle(Player.WHITE, game.getCurrentBoard()), game.getCurrentBoard().getHexagon(new Point(1, -1)).peek());
    }

    @Test
    void givenBeetleWhenMoveBeetleOntoOtherStoneAndNoSharedNeighbourThenBeetleMoved() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.BEETLE, 0, -1);
            game.play(Tile.BEETLE, 0, 2);
            game.move(0, -1, 0, 0);

        } catch (IllegalMove e){
            fail(e);
        }
        assertEquals(new Beetle(Player.WHITE, game.getCurrentBoard()),
                game.getCurrentBoard().getHexagon(new Point(0, 0)).peek());
    }
}
