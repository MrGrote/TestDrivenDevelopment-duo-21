package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;

class Player {
    /** An array of gamepieces avaiable to that player. */
    private GamePiece[] pieces;

     Player(final nl.hanze.hive.Hive.Player colour) {
        this.pieces = new GamePiece[] {
            new GamePiece(colour, Tile.QUEEN_BEE),
            new GamePiece(colour, Tile.BEETLE),
            new GamePiece(colour, Tile.BEETLE),
            new GamePiece(colour, Tile.SPIDER),
            new GamePiece(colour, Tile.SPIDER),
            new GamePiece(colour, Tile.GRASSHOPPER),
            new GamePiece(colour, Tile.GRASSHOPPER),
            new GamePiece(colour, Tile.GRASSHOPPER),
            new GamePiece(colour, Tile.SOLDIER_ANT),
            new GamePiece(colour, Tile.SOLDIER_ANT),
            new GamePiece(colour, Tile.SOLDIER_ANT),
        };
    }

    /**
     * Get an array of all avaiable gamepieces.
     * @return an array of all avaiable pieces
     */
    public GamePiece[] getPieces() {
        return this.pieces;
    }
}
