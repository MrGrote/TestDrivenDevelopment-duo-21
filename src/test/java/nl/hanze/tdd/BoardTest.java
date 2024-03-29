package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;

class BoardTest {
    /** A game object for use in the tests. */
    private Game game;
    private Board board;

    @BeforeEach
    void createGame() {
        board = new Board();
        game = new Game(board);
    }

    @Test
    void whenGetNeighboursThenLengthSix() {
        Point[] neigbours = game.getCurrentBoard().getNeigbours(new Point());
        assertEquals(6, neigbours.length);
    }
    @Test
    void whenGetNeigboursThenCorrectNeighbours() {
        Point[] neigbours = game.getCurrentBoard().getNeigbours(new Point());
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
        assertTrue(game.getCurrentBoard().isEmpty());
    }
    @Test
    void givenSeperatedHivewWhenIsValidStateThenNotVailidState() {
        game.put(new QueenBee(Player.BLACK, board), new Point(0, -3));
        game.put(new QueenBee(Player.BLACK, board), new Point(0, -1));
        game.put(new QueenBee(Player.BLACK, board), new Point(0, 0));

        assertFalse(game.getCurrentBoard().isValidState());
    }

    @Test
    void givenNotSeperatedHivewWhenIsValidStateThenVailidState() {
        game.put(new QueenBee(Player.BLACK, board), new Point(0, -1));
        game.put(new QueenBee(Player.WHITE, board), new Point(0, 0));

        assertTrue(game.getCurrentBoard().isValidState());
    }

    @Test
    void givenPointsNotOnStraightLineWhenIsInStriaghtLineThenFalse() {
        assertFalse(Board.isInStraightLine(new Point(3, -3), new Point(-2, 1)));
        assertFalse(Board.isInStraightLine(new Point(3, -3), new Point(-3, 1)));
    }

    @Test
    void givenPointsOnStriaghtLineWhenIsInStraightLineThenTrue() {
        assertTrue(Board.isInStraightLine(new Point(-2, -1), new Point(3, -1)));
        assertTrue(Board.isInStraightLine(new Point(-3, 0), new Point(0, -3)));
        assertTrue(Board.isInStraightLine(new Point(-3, 0), new Point(-3, 2)));
        assertTrue(Board.isInStraightLine(new Point(-3, 0), new Point(-5, 0)));
        assertTrue(Board.isInStraightLine(new Point(-3, 0), new Point(-3, -5)));
        assertTrue(Board.isInStraightLine(new Point(-3, 0), new Point(-5, 2)));
    }
}

