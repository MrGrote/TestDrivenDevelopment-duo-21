package nl.hanze.tdd;

import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.IllegalMove;

import java.awt.*;

/**
 * A class representing a Hive gamepiece.
 */
public abstract class GamePiece {
    /**
     * The player that own the piece.
     */
    private Player colour;
    /**
     * The symbol on the piece.
     */
    private Tile tile;

    /**
     * Create a GamePiece with a given colour and symbol.
     *
     * @param colour the colour of the piece
     */
    public GamePiece(final Player colour) {
        this.colour = colour;
    }

    /**
     * Get the colour of the piece.
     *
     * @return the colour
     */
    public Player getColour() {
        return this.colour;
    }

    /**
     * Get the symbol on the piece.
     *
     * @return the symbol
     */
    public Tile getTile() {
        return this.tile;
    }

    /**
     * Set the tile.
     *
     * @param tile the tile to set.
     */
    public void setTile(final Tile tile) {
        this.tile = tile;
    }

    /**
     * Move from point to point.
     *
     * @param from the origin
     * @param to   the destination
     * @exception  IllegalMove throw illegal move
     */
    @SuppressWarnings("checkstyle:JavadocMethod")
    public abstract void move(Point from, Point to) throws IllegalMove;

    /**
     * Compare the piece to another object.
     *
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
