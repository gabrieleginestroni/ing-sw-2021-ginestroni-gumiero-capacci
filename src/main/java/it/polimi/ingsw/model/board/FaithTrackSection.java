package it.polimi.ingsw.model.board;

public class FaithTrackSection {
    private PopeTile popeTile;
    private int firstTileNumber;
    private int lastTileNumber;


    public FaithTrackSection(int victoryPoints, int firstTileNumber, int lastTileNumber) {
        this.popeTile = new PopeTile(victoryPoints);
        this.firstTileNumber = firstTileNumber;
        this.lastTileNumber = lastTileNumber;


    }


    public void activatePopeTile(){
        this.popeTile.setActive();
    }



    public int getFirstTileNumber() {
        return firstTileNumber;
    }

    public int getLastTileNumber() {
        return lastTileNumber;
    }


}
