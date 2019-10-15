package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

class GamePieceTests {

    @Test
    void givenSameGamePieceWhenEqualsThenTrue() {
        GamePiece piece1 = new GamePiece(Player.WHITE, Tile.SOLDIER_ANT);
        GamePiece piece2 = new GamePiece(Player.WHITE, Tile.SOLDIER_ANT);
        assertEquals(piece1, piece2);
    }

    @Test
    void givenDifferentGamePieceWhenEqualsThenFalse() {
        GamePiece piece1 = new GamePiece(Player.BLACK, Tile.SOLDIER_ANT);
        GamePiece piece2 = new GamePiece(Player.WHITE, Tile.SOLDIER_ANT);
        assertNotEquals(piece1, piece2);
    }

}