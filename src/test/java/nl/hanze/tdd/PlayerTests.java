package nl.hanze.tdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Tile;

class PlayerTests {
    private  Board board = new Board();
    private Map<Tile, Integer>  expected = Map.of(Tile.QUEEN_BEE, 1,
                                                        Tile.BEETLE, 2,
                                                        Tile.GRASSHOPPER, 3,
                                                        Tile.SOLDIER_ANT, 3,
                                                        Tile.SPIDER, 2);

    @Test
    void whenNewPlayerThenCorrectPieces() {
        Hive.Player colour = Hive.Player.WHITE;
        Player player = new Player(colour);

        assertEquals(expected, player.getPieces());

        colour = Hive.Player.BLACK;
        player = new Player(colour);

        assertEquals(expected, player.getPieces());
    }
}
