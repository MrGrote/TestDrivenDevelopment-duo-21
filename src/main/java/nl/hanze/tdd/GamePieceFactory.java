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
        GamePiece piece;
        switch (tile) {
        case BEETLE:
            piece = new Beetle(player, this.field);
            break;
        case GRASSHOPPER:
            piece = new Grasshopper(player, this.field);
            break;
        case QUEEN_BEE:
            piece = new QueenBee(player, this.field);
            break;
        case SPIDER:
            piece = new Spider(player, this.field);
            break;
        case SOLDIER_ANT:
            piece = new SoldierAnt(player, this.field);
            break;
        default:
            throw new IllegalArgumentException();
        }
        return piece;
    }
}
