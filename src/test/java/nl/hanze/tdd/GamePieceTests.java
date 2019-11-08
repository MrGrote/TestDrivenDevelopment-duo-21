package nl.hanze.tdd;

import org.junit.jupiter.api.Test;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class GamePieceTests {
    public Board board = new Board();

    @Test
    void whenColourThenColourNotNull() {
        GamePiece piece = new Spider(Player.BLACK, board);
        Player colour = piece.getColour();
        assertNotNull(colour);
    }

    @Test
    void givenColourWhenColourThenColour() {
        GamePiece piece = new Spider(Player.BLACK, board);
        Player colour = piece.getColour();
        assertEquals(colour, Player.BLACK);
    }

    @Test
    void whenTileThenTileNotNull() {
        GamePiece piece = new Spider(Player.BLACK, board);
        Tile tile = piece.getTile();
        assertNotNull(tile);
    }

    @Test
    void givenTileWhenTileThenTile() {
        GamePiece piece = new QueenBee(Player.BLACK, board);
        Tile tile = piece.getTile();
        assertEquals(tile, Tile.QUEEN_BEE);
    }

    @Test
    void givenSameGamePieceWhenEqualsThenTrue() {
        GamePiece piece1 = new SoldierAnt(Player.WHITE, board);
        GamePiece piece2 = new SoldierAnt(Player.WHITE, board);
        assertEquals(piece1, piece2);
    }

    @Test
    void givenDifferentGamePieceWhenEqualsThenFalse() {
        GamePiece piece1 = new SoldierAnt(Player.BLACK, board);
        GamePiece piece2 = new SoldierAnt(Player.WHITE, board);
        assertNotEquals(piece1, piece2);
    }

}