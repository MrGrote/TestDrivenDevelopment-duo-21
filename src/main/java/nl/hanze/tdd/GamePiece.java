package nl.hanze.tdd;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

class GamePiece{
    private Player colour;
    private Tile tile;

    public GamePiece(){
        this.colour = Player.WHITE;
        this.tile = Tile.SOLDIER_ANT;
    }

    public GamePiece(Player colour){
        this.colour = colour;
        this.tile = Tile.SOLDIER_ANT;
    }

    public GamePiece(Tile tile){
        this.colour = Player.WHITE;
        this.tile = tile;
    }

    public Player getColour(){
        return this.colour;
    }

    public void setColour(Player colour){
        this.colour = colour;
    }

    public Tile getTile(){
        return this.tile;
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }
}