package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;

class Player{
    private GamePiece[] pieces;

    public Player(nl.hanze.hive.Hive.Player colour){
        GamePiece[] pieces = {
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
        this.pieces = pieces;
    }

    public GamePiece[] getPieces(){
        return this.pieces;
    }
}