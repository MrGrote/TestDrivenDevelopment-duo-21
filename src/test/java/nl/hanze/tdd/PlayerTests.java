package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;
import nl.hanze.hive.Hive;

class PlayerTests {
    @Test
    void whenNewPlayerThenCorrectPieces() {
        Hive.Player colour = Hive.Player.WHITE;
        Player player = new Player(colour);
        GamePiece[] pieces = {
            new GamePiece(colour, Hive.Tile.QUEEN_BEE),
            new GamePiece(colour, Hive.Tile.BEETLE),
            new GamePiece(colour, Hive.Tile.BEETLE),
            new GamePiece(colour, Hive.Tile.SPIDER),
            new GamePiece(colour, Hive.Tile.SPIDER),
            new GamePiece(colour, Hive.Tile.GRASSHOPPER),
            new GamePiece(colour, Hive.Tile.GRASSHOPPER),
            new GamePiece(colour, Hive.Tile.GRASSHOPPER),
            new GamePiece(colour, Hive.Tile.SOLDIER_ANT),
            new GamePiece(colour, Hive.Tile.SOLDIER_ANT),
            new GamePiece(colour, Hive.Tile.SOLDIER_ANT),
        };

        assertArrayEquals(pieces, player.getPieces());

        colour = Hive.Player.BLACK;
        player = new Player(colour);
        GamePiece[] piecesBlack = {
            new GamePiece(colour, Hive.Tile.QUEEN_BEE),
                new GamePiece(colour, Hive.Tile.BEETLE),
                new GamePiece(colour, Hive.Tile.BEETLE),
                new GamePiece(colour, Hive.Tile.SPIDER),
                new GamePiece(colour, Hive.Tile.SPIDER),
                new GamePiece(colour, Hive.Tile.GRASSHOPPER),
                new GamePiece(colour, Hive.Tile.GRASSHOPPER),
                new GamePiece(colour, Hive.Tile.GRASSHOPPER),
                new GamePiece(colour, Hive.Tile.SOLDIER_ANT),
                new GamePiece(colour, Hive.Tile.SOLDIER_ANT),
                new GamePiece(colour, Hive.Tile.SOLDIER_ANT),
            };

        assertArrayEquals(piecesBlack, player.getPieces());
    }
}
