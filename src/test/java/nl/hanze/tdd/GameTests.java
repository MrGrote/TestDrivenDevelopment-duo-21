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

    /** A game object for use in the tests. */
    private Game game;


    @BeforeEach
    void createGame() {
        game = new Game();
    }

    @Test
    void whenGetNeighboursThenLengthSix() {
        Point[] neigbours = game.getNeigbours(new Point());
        assertEquals(6, neigbours.length);
    }

    @Test
    void whenGetNeigboursThenCorrectNeighbours() {
        Point[] neigbours = game.getNeigbours(new Point());
        Point[] knownNeigbours = {
            new Point(0, -1),
            new Point(-1, 0),
            new Point(-1, 1),
            new Point(1, -1),
            new Point(1, 0),
            new Point(0, 1)
        };
        outside:
        for (Point knownNeigbour : knownNeigbours) {
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
        assertTrue(game.isEmpty());
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
            assertEquals(nl.hanze.hive.Hive.Player.BLACK,
                game.getCurrentPlayer());
            game.put(new GamePiece(Player.WHITE, Tile.GRASSHOPPER),
                new Point(2, -1));
            game.move(1, 0, 1, -1);
            assertEquals(nl.hanze.hive.Hive.Player.WHITE,
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
        assertEquals(nl.hanze.hive.Hive.Player.BLACK, game.getCurrentPlayer());
        try {
            game.play(Tile.BEETLE, 0, 1);
        } catch (IllegalMove e) {
        }
        assertEquals(nl.hanze.hive.Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void whenMadePassNextActive() {
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
        game.put(new GamePiece(Player.WHITE, Tile.QUEEN_BEE), new Point(0, 0));
        game.put(new GamePiece(Player.BLACK, Tile.GRASSHOPPER),
            new Point(0, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, 0));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, 1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, 0));
        game.put(new GamePiece(Player.BLACK, Tile.GRASSHOPPER),
            new Point(0, 1));
        assertTrue(game.isWinner(nl.hanze.hive.Hive.Player.BLACK));
    }

    @Test
    void whenColourQueenSurroundedThenColourNotWin() {
        game.put(new GamePiece(Player.WHITE, Tile.QUEEN_BEE), new Point(0, 0));
        game.put(new GamePiece(Player.BLACK, Tile.GRASSHOPPER),
            new Point(0, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, 0));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, 1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, 0));
        game.put(new GamePiece(Player.BLACK, Tile.GRASSHOPPER),
            new Point(0, 1));
        assertFalse(game.isWinner(nl.hanze.hive.Hive.Player.WHITE));
    }

    @Test
    void whenBothQueenSurroundedThenDraw() {
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, 0));
        game.put(new GamePiece(Player.WHITE, Tile.QUEEN_BEE), new Point(0, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, 0));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, 1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, 0));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(0, 1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(-1, -1));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(0, -2));
        game.put(new GamePiece(Player.BLACK, Tile.BEETLE), new Point(1, -2));
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
            game.getHexagon(new Point(0, 0)).peek();
        });
        assertEquals(new GamePiece(Player.WHITE, Tile.QUEEN_BEE),
            game.getHexagon(new Point(0, 1)).peek());
    }

    @Test
    void whenMoveOpponentPieceThenIllegalMove() {
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, 0));
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
    void givenSeperatedHivewWhenIsValidStateThenNotVailidState() {
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, -3));
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, -1));
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, 0));

        assertFalse(game.isValidState());
    }

    @Test
    void givenNotSeperatedHivewWhenIsValidStateThenVailidState() {
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, -1));
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, 0));

        assertTrue(game.isValidState());
    }

    @Test
    void givenEmptyHexWhenGetHeightThenReturn0() {
        assertEquals(0, game.getHeight(new Point(0, 0)));
    }

    @Test
    void given1StoneWhenGetHeightThenReturn1() {
        game.put(new GamePiece(Player.WHITE, Tile.QUEEN_BEE), new Point(0, 0));
        assertEquals(1, game.getHeight(new Point(0, 0)));
    }

    @Test
    void whenBlockedPushThenIllegalMove() {
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, -1));
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, 0));
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(-1, 1));

        assertThrows(IllegalMove.class, () -> {
            game.push(new Point(0, 0), new Point(-1, 0));
        });
    }

    @Test
    void whenUnblockedPushThenLegalPush() {
        game.put(new GamePiece(Player.BLACK, Tile.QUEEN_BEE), new Point(0, -1));

        try {
            game.pass();
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.pass();
            game.push(new Point(0, 0), new Point(-1, 0));
        } catch (IllegalMove e) {
            fail(e);
        }
        assertEquals(new GamePiece(Player.BLACK, Tile.QUEEN_BEE),
            game.getHexagon(new Point(-1, 0)).peek());
    }

    @Test
    void whenUnconnectedPushThenIllegalMove() {
        game.put(new GamePiece(Player.WHITE, Tile.BEETLE), new Point(-1, -1));
        game.put(new GamePiece(Player.WHITE, Tile.BEETLE), new Point(1, -1));
        game.put(new GamePiece(Player.BLACK, Tile.SPIDER), new Point(0, -2));
        game.put(new GamePiece(Player.WHITE, Tile.SPIDER), new Point(1, -2));
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


}
