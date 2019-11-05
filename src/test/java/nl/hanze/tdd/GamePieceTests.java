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
        GamePiece piece = new Spider(Player.BLACK);
        Player colour = piece.getColour();
        assertNotNull(colour);
    }

    @Test
    void givenColourWhenColourThenColour() {
        GamePiece piece = new Spider(Player.BLACK);
        Player colour = piece.getColour();
        assertEquals(colour, Player.BLACK);
    }

    @Test
    void whenTileThenTileNotNull() {
        GamePiece piece = new Spider(Player.BLACK);
        Tile tile = piece.getTile();
        assertNotNull(tile);
    }

    @Test
    void givenTileWhenTileThenTile() {
        GamePiece piece = new QueenBee(Player.BLACK);
        Tile tile = piece.getTile();
        assertEquals(tile, Tile.QUEEN_BEE);
    }

    @Test
    void givenTileAndColourWhenTileAndColourThenTileAndColor() {
        GamePiece piece = new QueenBee(Player.WHITE);
        Player colour = piece.getColour();
        Tile tile = piece.getTile();
        assertEquals(colour, Player.WHITE);
        assertEquals(tile, Tile.QUEEN_BEE);
    }

    @Test
    void givenSameGamePieceWhenEqualsThenTrue() {
        GamePiece piece1 = new SoldierAnt(Player.WHITE);
        GamePiece piece2 = new SoldierAnt(Player.WHITE);
        assertEquals(piece1, piece2);
    }

    @Test
    void givenDifferentGamePieceWhenEqualsThenFalse() {
        GamePiece piece1 = new SoldierAnt(Player.BLACK);
        GamePiece piece2 = new SoldierAnt(Player.WHITE);
        assertNotEquals(piece1, piece2);
    }

}
