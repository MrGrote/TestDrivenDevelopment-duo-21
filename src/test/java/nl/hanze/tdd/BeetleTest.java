package nl.hanze.tdd;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class BeetleTest {
    /**
     * A game object for use in the tests.
     */
    private Game game;


    @BeforeEach
    void createGame() {
        game = new Game();
    }

    @Test
    void givenBeetleWhenMoveBeetleThenBeetleMovedToTarget() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.BEETLE,0, -1);
            game.play(Tile.BEETLE, 0, 2);
            game.move(0, -1, 1, -1);

        } catch (IllegalMove e){
            fail(e);
        }
        assertEquals(new Beetle(Player.WHITE),
                game.getCurrentBoard().getHexagon(new Point(1, -1)).peek());


    }
    @Test
    void givenBeetleWhenMoveBeetleToFarThenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
            game.play(Tile.BEETLE,0, -1);
            game.play(Tile.BEETLE, 0, 2);
        } catch (IllegalMove e){
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, -1, 1, 0);
        });
    }
}
