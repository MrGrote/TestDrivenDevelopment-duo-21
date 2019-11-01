package nl.hanze.tdd;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

/** A class representing a Hive gamepiece. */
class GamePiece {
    /** The player that own the piece. */
    private Player colour;
    /** The symbol on the piece. */
    private Tile tile;

    /**
     * Create a GamePiece with a given colour and symbol.
     * @param colour the colour of the piece
     * @param tile the symbol on the piece
     */
    GamePiece(final Player colour, final Tile tile) {
        this.colour = colour;
        this.tile = tile;
    }

    /**
     * Get the colour of the piece.
     * @return the colour
     */
    public Player getColour() {
        return this.colour;
    }

    /**
     * Get the symbol on the piece.
     * @return the symbol
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Compare the piece to another object.
     * @param other the object to compare the piece with
     * @return a boolean indicating wether the object is equal to the piece
     */
    public boolean equals(final Object other) {
        if (other instanceof GamePiece) {
            GamePiece otherGamePiece = (GamePiece) other;
            return this.colour == otherGamePiece.getColour()
                && this.tile == otherGamePiece.getTile();
        }
        return false;
    }

    /**
     * @return the hash of a GamePiece
     */
    public int hashCode() {
        return this.colour.hashCode() + this.tile.hashCode();
    }
}
