package nl.hanze.tdd;

import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

class GamePiece {
    private Player colour;
    private Tile tile;

    public GamePiece(Player colour, Tile tile) {
        this.colour = colour;
        this.tile = tile;
    }

    public boolean equals(Object other) {
        if (other instanceof GamePiece) {
            GamePiece otherGamePiece = (GamePiece) other;
            return this.colour == otherGamePiece.getColour() && this.tile == otherGamePiece.getTile();
        }
        return false;
    }

    public Player getColour() {
        return this.colour;
    }

    public Tile getTile() {
        return this.tile;
    }
}