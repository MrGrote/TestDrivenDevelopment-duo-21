package nl.hanze.tdd;

import org.junit.jupiter.api.Test;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.IllegalMove;

class FullGameTests {
    @Test
    void gameOne() throws IllegalMove {
        Board board = new Board();
        Game game = new Game(board);

        game.play(Tile.SOLDIER_ANT, 0, 0);
        game.play(Tile.SOLDIER_ANT, -1, 0);
        game.play(Tile.GRASSHOPPER, 1, -1);
        game.play(Tile.GRASSHOPPER, -2, 1);
        game.play(Tile.QUEEN_BEE, 1, 0);
        game.play(Tile.SPIDER, -2, 0);
        game.play(Tile.SPIDER, 1, 1);
        game.play(Tile.QUEEN_BEE, -3, 1);
        game.play(Tile.BEETLE, 2, -2);
        game.play(Tile.GRASSHOPPER, -2, 2);
        game.move(2, -2, 1, -1);
    }

    /**
     * https://boardgamearena.com/archive/replay/191014-0956/?table=58656210&player=38719335&comments=85297893;
     */
    @Test
    void gameTwo() throws IllegalMove {
        Board board = new Board();
        Game game = new Game(board);

        game.play(Tile.GRASSHOPPER, 0, 0);
        game.play(Tile.GRASSHOPPER, 0, -1);
        game.play(Tile.SOLDIER_ANT, 0, 1);
        game.play(Tile.QUEEN_BEE, -1, -1);
        game.play(Tile.QUEEN_BEE, 1, 0);
        game.play(Tile.SOLDIER_ANT, 1, -2);
        game.move(0, 1, 1, -3);
        game.play(Tile.SOLDIER_ANT, -2, 0);
        game.play(Tile.SPIDER, -1, 1);
        game.move(-2, 0, -1, 2);
        game.play(Tile.SOLDIER_ANT, 2, -1);
        game.play(Tile.SPIDER, 0, 2);
        game.move(2, -1, 0, 3);
        game.play(Tile.SOLDIER_ANT, -2, 0);
        game.play(Tile.SPIDER, -1, 4);
        game.move(-2, 0, -1, 5);
        game.play(Tile.SOLDIER_ANT, 2, -1);
        game.play(Tile.GRASSHOPPER, -2, -1);
        game.move(2, -1, -2, 6);
        game.play(Tile.GRASSHOPPER, -2, 0);
        game.play(Tile.BEETLE, 0, -3);
        game.move(-2, 0, 0, -2);
        game.move(1, -3, 2, -3);
        game.move(-2, -1, 1, -1);
        game.move(0, -3, 0, -2);
        game.play(Tile.BEETLE, -2, 0);
        game.play(Tile.BEETLE, 0, -3);
        game.move(0, -1, 0, -4);
        game.move(2, -3, -3, 0);
        game.play(Tile.SPIDER, 2, -2);
        game.move(1, 0, 2, -1);
        game.move(2, -2, 2, 0);
        game.move(0, -2, 1, -2);
        game.play(Tile.BEETLE, -1, -4);
        game.play(Tile.GRASSHOPPER, 2, -3);
        game.move(-1, -4, -1, -3);
        game.move(1, -2, 0, -2);
        game.move(0, -4, -2, -2);
        game.move(-3, 0, -3, 1);
        game.move(2, 0, 2, -2);
        game.move(2, -1, 3, -2);
        game.move(-2, -2, 0, -4);
        game.play(Tile.GRASSHOPPER, -3, 6);
        game.move(-1, -3, 0, -3);
        game.move(0, -2, -1, -1);
        game.move(0, -3, 0, -2);
        game.move(-1, -1, 0, -2);
        game.move(0, -4, 0, -1);
        game.move(-3, 6, -1, 6);
        game.move(0, -1, 3, -4);
        game.move(0, -3, 0, -2);
        game.move(3, -4, 0, -1);
        game.move(0, -2, 0, -1);
        game.move(1, -1, 1, -3);
        game.move(-2, 6, 1, -4);
        game.pass();
        game.move(2, -3, 2, -1);
        game.pass();
        game.move(3, -2, 3, -3);
        game.pass();
        game.move(3, -3, 2, -3);
        game.pass();
        game.move(2, -3, 2, -4);
        game.pass();
        game.move(1, -4, 3, -2);
        game.pass();
        game.move(2, -4, 1, -4);
        game.pass();
        game.move(3, -2, 2, -4);
        game.pass();
        game.move(1, -4, 0, -3);
        game.pass();
        game.move(0, -3, -1, -2);
        game.pass();
        game.move(2, -4, -2, -1);
        game.move(1, -3, 1, -1);
        game.move(0, -1, -1, 0);

    }

    /**
     * https://boardgamearena.com/gamereview?table=43010728
     */
    @Test
    void gameThree() throws IllegalMove {
        Board board = new Board();
        Game game = new Game(board);

        game.play(Tile.GRASSHOPPER, 0, 0);
        game.play(Tile.GRASSHOPPER, 0, -1);
        game.play(Tile.SOLDIER_ANT, 0, 1);
        game.play(Tile.QUEEN_BEE, 1, -2);
        game.play(Tile.QUEEN_BEE, -1, 1);
        game.play(Tile.SOLDIER_ANT, -1, -1);
        game.move(0, 1, -1, -2);
        game.play(Tile.SOLDIER_ANT, 2, -2);
        game.play(Tile.SPIDER, 1, 0);
        game.move(2, -2, -1, 2);
        game.move(1, 0, 2, -3);
        game.play(Tile.BEETLE, 0, 2);
        game.play(Tile.BEETLE, 3, -3);
        game.move(0, 2, -1, 2);
        game.move(3, -3, 2, -2);
        game.move(-1, 2, -1, 1);
        game.move(2, -2, 1, -2);
        game.move(-1, 2, 2, -2);
        game.play(Tile.BEETLE, 1, -3);
        game.play(Tile.SPIDER, 2, -1);
        game.move(-1, -2, 0, -2);
        game.move(2, -1, 0, 1);
        game.move(1, -2, 1, -1);

    }
}
