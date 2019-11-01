package nl.hanze.tdd;

import org.junit.jupiter.api.Test;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GamePieceTests {
    @Test
    void whenColourThenColourNotNull() {
        GamePiece piece = new GamePiece(Player.BLACK, Tile.SPIDER);
        Player colour = piece.getColour();
        assertNotNull(colour);
    }

    @Test
    void givenColourWhenColourThenColour() {
        GamePiece piece = new GamePiece(Player.BLACK, Tile.SPIDER);
        Player colour = piece.getColour();
        assertEquals(colour, Player.BLACK);
    }

    @Test
    void whenTileThenTileNotNull() {
        GamePiece piece = new GamePiece(Player.BLACK, Tile.SPIDER);
        Tile tile = piece.getTile();
        assertNotNull(tile);
    }

    @Test
    void givenTileWhenTileThenTile() {
        GamePiece piece = new GamePiece(Player.BLACK, Tile.QUEEN_BEE);
        Tile tile = piece.getTile();
        assertEquals(tile, Tile.QUEEN_BEE);
    }

    @Test
    void givenTileAndColourWhenTileAndColourThenTileAndColor() {
        GamePiece piece = new GamePiece(Player.WHITE, Tile.QUEEN_BEE);
        Player colour = piece.getColour();
        Tile tile = piece.getTile();
        assertEquals(colour, Player.WHITE);
        assertEquals(tile, Tile.QUEEN_BEE);
    }

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
