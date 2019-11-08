package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.IllegalMove;
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
     * Move from point to point.
     *
     * @param from the origin
     * @param to   the destination
     * @exception  IllegalMove throw illegal move
     */
    void move(Point from, Point to) throws IllegalMove;
}
