package nl.hanze.tdd;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;


public class QueenBeeTest {
    /**
     * A game object for use in the tests.
     */
    private Game game;
    private Board board;


    @BeforeEach
    void createGame() {
        board = new Board();
        game = new Game(board);
    }

    @Test
    void givenQueenWhenMoveQueenThenQueenMoved(){
         try {
             this.game.play(Tile.QUEEN_BEE, 0, 0);
             this.game.play(Tile.QUEEN_BEE, 0, 1);
             this.game.move(0, 0, 1, 0);
         } catch (IllegalMove e){
             fail(e);
         }
         assertEquals(new QueenBee(Player.WHITE, this.game.getCurrentBoard()), this.game.getCurrentBoard().getHexagon(new Point(1, 0)).peek());
    }

    @Test
    void givenQueenWhenMovedToFarThenIllegalMove() {
        try {
            this.game.play(Tile.QUEEN_BEE, 0, 0);
            this.game.play(Tile.QUEEN_BEE, 0, 1);
        } catch (IllegalMove e){
            fail(e);
        }
        assertThrows(IllegalMove.class, () ->{
            this.game.move(0, 0, 1, 1);
        });
    }
    @Test
    void givenQueenWhenMoveQueenOntoOtherStoneThenIllegalMove() {
        try {
            game.play(Tile.QUEEN_BEE, 0, 0);
            game.play(Tile.SOLDIER_ANT, 0, -1);
            game.play(Tile.SOLDIER_ANT, 0, 1);
            game.play(Tile.QUEEN_BEE, 0, -2);
            game.play(Tile.SOLDIER_ANT,0, 2);


        } catch (IllegalMove e){
            fail(e);
        }
        assertThrows(IllegalMove.class, () ->{
            this.game.move(0, -2, 0, -1);
        });

    }

}
