package nl.hanze.tdd;

import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.IllegalMove;

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
    public Player getColour();

    /**
     * Get the symbol on the piece.
     *
     * @return the symbol
     */
    public Tile getTile();

    /**
     * Move from point to point.
     *
     * @param from the origin
     * @param to   the destination
     * @exception  IllegalMove throw illegal move
     */
    public void move(Point from, Point to) throws IllegalMove;

    /**
     * Compare the piece to another object.
     *
     * @param other the object to compare the piece with
     * @return a boolean indicating wether the object is equal to the piece
     */
    public boolean equals(final Object other);

}
