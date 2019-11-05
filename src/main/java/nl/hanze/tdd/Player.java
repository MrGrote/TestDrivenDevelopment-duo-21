package nl.hanze.tdd;

class Player {
    /** An array of gamepieces avaiable to that player. */
    private GamePiece[] pieces;

     Player(final nl.hanze.hive.Hive.Player colour) {
        this.pieces = new GamePiece[] {
            new QueenBee(colour),
            new Beetle(colour),
            new Beetle(colour),
            new Spider(colour),
            new Spider(colour),
            new Grasshopper(colour),
            new Grasshopper(colour),
            new Grasshopper(colour),
            new SoldierAnt(colour),
            new SoldierAnt(colour),
            new SoldierAnt(colour),
        };
    }

    /**
     * Get an array of all avaiable gamepieces.
     * @return an array of all avaiable pieces
     */
    public GamePiece[] getPieces() {
        return this.pieces;
    }
}
