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
            new QueenBee(colour),
            new Beetle(colour),
            new Beetle(colour),
            new Spider(colour),
            new Spider(colour),
            new Grasshopper(colour),
            new Grasshopper(colour),
            new Grasshopper(colour),
            new SoldierAnt(colour),
            new SoldierAnt(colour),
            new SoldierAnt(colour),
        };

        assertArrayEquals(pieces, player.getPieces());

        colour = Hive.Player.BLACK;
        player = new Player(colour);
        GamePiece[] piecesBlack = {
            new QueenBee(colour),
            new Beetle(colour),
            new Beetle(colour),
            new Spider(colour),
            new Spider(colour),
            new Grasshopper(colour),
            new Grasshopper(colour),
            new Grasshopper(colour),
            new SoldierAnt(colour),
            new SoldierAnt(colour),
            new SoldierAnt(colour),
            };

        assertArrayEquals(piecesBlack, player.getPieces());
    }
}
