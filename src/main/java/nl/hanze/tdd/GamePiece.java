package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

import java.awt.Point;

/**
 * A class representing a Hive gamepiece.
 */
public interface GamePiece {
    /**
     * Get the colour of the piece.
     *
     * @return the colour
     */
    Player getColour();

    /**
     * Get the symbol on the piece.
     *
     * @return the symbol
     */
    Tile getTile();

    /**
     * Cechk if player can move.
     *
     * @param from the origin
     * @param to the destination
     * @return wether or not the player still can make a move
     */
    boolean isLegalMove(Point from, Point to);
}
