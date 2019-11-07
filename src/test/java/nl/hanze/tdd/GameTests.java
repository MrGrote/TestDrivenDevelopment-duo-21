package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;

class GameTests {

    /**
     * A game object for use in the tests.
     */
    private Game game;


    @BeforeEach
    void createGame() {
        game = new Game();
    }


    @Test
    void whenNewGameThenWhiteActive() {
        assertEquals(nl.hanze.hive.Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenMadeMoveNextActive() {

        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 1, 0);
            game.move(0, 0, 0, 1);
            assertEquals(Player.BLACK, game.getCurrentPlayer());
            game.put(new Grasshopper(Player.WHITE),
                    new Point(2, -1));
            game.move(1, 0, 1, -1);
            assertEquals(Player.WHITE,
                    game.getCurrentPlayer());
        } catch (IllegalMove e) {
            fail(e);
        }
    }

    @Test
    void whenMadePlayNextActive() {
        try {
            game.play(Tile.BEETLE, 0, 0);
        } catch (IllegalMove e) {
        }
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        try {
            game.play(Tile.BEETLE, 0, 1);
        } catch (IllegalMove e) {
        }
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenMadePassNextActive() {
        try {
            game.pass();
        } catch (IllegalMove e) {
        }
        assertEquals(Player.BLACK, game.getCurrentPlayer());
        try {
            game.pass();
        } catch (IllegalMove e) {
        }
        assertEquals(Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenPlayPlayedPieceThenIllegalMove() {
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
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
        } catch (IllegalMove e) {
            fail();
        }
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.QUEEN_BEE, 10, 10);
        });
    }

    @Test
    void whenColourQueenSurroundedThenOppositeColourWin() {
        game.put(new QueenBee(Player.WHITE), new Point(0, 0));
        game.put(new Grasshopper(Player.BLACK), new Point(0, -1));
        game.put(new Beetle(Player.BLACK), new Point(-1, 0));
        game.put(new Beetle(Player.BLACK), new Point(-1, 1));
        game.put(new Beetle(Player.BLACK), new Point(1, -1));
        game.put(new Beetle(Player.BLACK), new Point(1, 0));
        game.put(new Grasshopper(Player.BLACK), new Point(0, 1));
        assertTrue(game.isWinner(Player.BLACK));
    }

    @Test
    void whenColourQueenSurroundedThenColourNotWin() {
        game.put(new QueenBee(Player.WHITE), new Point(0, 0));
        game.put(new Grasshopper(Player.BLACK), new Point(0, -1));
        game.put(new Beetle(Player.BLACK), new Point(-1, 0));
        game.put(new Beetle(Player.BLACK), new Point(-1, 1));
        game.put(new Beetle(Player.BLACK), new Point(1, -1));
        game.put(new Beetle(Player.BLACK), new Point(1, 0));
        game.put(new Grasshopper(Player.BLACK), new Point(0, 1));
        assertFalse(game.isWinner(Player.WHITE));
    }

    @Test
    void whenBothQueenSurroundedThenDraw() {
        game.put(new QueenBee(Player.BLACK), new Point(0, 0));
        game.put(new QueenBee(Player.WHITE), new Point(0, -1));
        game.put(new Beetle(Player.BLACK), new Point(-1, 0));
        game.put(new Beetle(Player.BLACK), new Point(-1, 1));
        game.put(new Beetle(Player.BLACK), new Point(1, -1));
        game.put(new Beetle(Player.BLACK), new Point(1, 0));
        game.put(new Beetle(Player.BLACK), new Point(0, 1));
        game.put(new Beetle(Player.BLACK), new Point(-1, -1));
        game.put(new Beetle(Player.BLACK), new Point(0, -2));
        game.put(new Beetle(Player.BLACK), new Point(1, -2));
        assertTrue(game.isDraw());
    }

    @Test
    void whenNotBothQueenSurroundedThenNotDraw() {
        assertFalse(game.isDraw());
    }

    @Test
    void whenTwoColourPlacedThenTwoColourPlaced() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertTrue(game.bothPlayersPlayed());
    }

    @Test
    void whenOneColourPlacedThenNotTwoColourPlaced() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertFalse(game.bothPlayersPlayed());
    }

    @Test
    void givenBothPlayedWhenPlayAdjecentOpponentThenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> game.play(Tile.BEETLE, 1, 0));
    }

    @Test
    void givenEmptyBoardWhenMoveThenIllegalMove() {
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 0, 0, 0);
        });
    }

    @Test
    void givenSpaceOccupiedWhenMoveThenMoved() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 1, 0);
        } catch (IllegalMove e) {
            fail(e);
        }

        try {
            game.move(0, 0, 0, 1);

        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(NullPointerException.class, () -> {
            game.getCurrentBoard().getHexagon(new Point(0, 0)).peek();
        });
        assertEquals(new QueenBee(Player.WHITE),
                game.getCurrentBoard().getHexagon(new Point(0, 1)).peek());
    }

    @Test
    void whenMoveOpponentPieceThenIllegalMove() {
        game.put(new QueenBee(Player.BLACK), new Point(0, 0));
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 0, 0, 1);
        });
    }

    @Test
    void givenNoColourQueenPlacedWhenMoveThenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 0, 0, 1);
        });
    }

    @Test
    void givenMovedWhenNoNeighborsThenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.QUEEN_BEE, 0, 1);
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 0, 0, -1);
        });
    }


    @Test
    void givenEmptyHexWhenGetHeightThenReturn0() {
        assertEquals(0, game.getCurrentBoard().getHeight(new Point(0, 0)));
    }

    @Test
    void given1StoneWhenGetHeightThenReturn1() {
        game.put(new QueenBee(Player.WHITE), new Point(0, 0));
        assertEquals(1, game.getCurrentBoard().getHeight(new Point(0, 0)));
    }

    @Test
    void whenBlockedPushThenIllegalMove() {
        game.put(new QueenBee(Player.BLACK), new Point(0, -1));
        game.put(new QueenBee(Player.BLACK), new Point(0, 0));
        game.put(new QueenBee(Player.BLACK), new Point(-1, 1));

        assertThrows(IllegalMove.class, () -> {
            game.push(new Point(0, 0), new Point(-1, 0));
        });
    }

    @Test
    void whenUnblockedPushThenLegalPush() {
        game.put(new QueenBee(Player.BLACK), new Point(0, -1));

        try {
            game.pass();
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.pass();
            game.push(new Point(0, 0), new Point(-1, 0));
        } catch (IllegalMove e) {
            fail(e);
        }
        assertEquals(new QueenBee(Player.BLACK),
                game.getCurrentBoard().getHexagon(new Point(-1, 0)).peek());
    }

    @Test
    void whenUnconnectedPushThenIllegalMove() {
        game.put(new Beetle(Player.WHITE), new Point(-1, -1));
        game.put(new Beetle(Player.WHITE), new Point(1, -1));
        game.put(new Spider(Player.BLACK), new Point(0, -2));
        game.put(new Spider(Player.WHITE), new Point(1, -2));
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.pass();
        } catch (IllegalMove e) {
            fail(e);
        }
        assertThrows(IllegalMove.class, () -> {
            game.push(new Point(0, 0), new Point(-1, 0));
        });
    }

    @Test
    void whenConnectedThenLegalMove() {
        game.put(new Beetle(Player.WHITE), new Point(-1, -1));
        game.put(new Beetle(Player.WHITE), new Point(1, -1));
        game.put(new Spider(Player.BLACK), new Point(0, -2));
        game.put(new Spider(Player.WHITE), new Point(1, -2));
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.pass();
            game.push(new Point(0, 0), new Point(0, -1));
        } catch (IllegalMove e) {
            fail(e);
        }
        assertEquals(new QueenBee(Player.WHITE),
                game.getCurrentBoard().getHexagon(new Point(0, -1)).peek());
    }


    @Test
    void givenThreeNonQueenBeeStonesPlayedWhenPlayQueenBeeThenQueenBeeIsPlayed() {
        try {
            game.play(Tile.SPIDER, 0, 0);
            game.play(Tile.GRASSHOPPER, 0, 1);
            game.play(Tile.GRASSHOPPER, 0, -1);
            game.play(Tile.QUEEN_BEE, 0, 2);
            game.play(Tile.BEETLE, 0, -2);
            game.play(Tile.BEETLE, 0, 3);
            game.play(Tile.QUEEN_BEE, 0, -3);

        } catch (IllegalMove e) {
            fail(e);
        }
        assertEquals(new QueenBee(Player.WHITE),
                game.getCurrentBoard().getHexagon(new Point(0, -3)).peek());

    }


}
