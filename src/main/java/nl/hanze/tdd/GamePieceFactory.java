package nl.hanze.tdd;

import nl.hanze.hive.Hive.Tile;
import nl.hanze.hive.Hive.Player;

class GamePieceFactory {
    /** The field that the gamepieces exist on. */
    private Board field;

    GamePieceFactory(final Board board) {
        this.field = board;
    }

    public GamePiece getGamePiece(final Player player, final Tile tile) {
        switch (tile) {
        case BEETLE:
            return new Beetle(player, this.field);
        case GRASSHOPPER:
            return  new Grasshopper(player, this.field);
        case QUEEN_BEE:
            return new QueenBee(player, this.field);
        case SPIDER:
            return new Spider(player, this.field);
        case SOLDIER_ANT:
            return new SoldierAnt(player, this.field);
        default :
            return null;
        }
    }
}
