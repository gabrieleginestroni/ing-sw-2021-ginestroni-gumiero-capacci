package it.polimi.ingsw.server.model.board;

/**@author Gabriele Ginestroni
 * Class that represents a section of the player's faith track. It cointains a Pope Tile.
 */
public class FaithTrackSection {
    private final PopeTile popeTile;
    private final int firstTileNumber;
    private final int lastTileNumber;

    /**
     * Constructor of the Faith Track Section
     * @param victoryPoints Number of faith points of his Pope Favor Tile
     * @param firstTileNumber Index of the section's first tile in the faith track
     * @param lastTileNumber Index of the section's last tile in the faith track
     */
    public FaithTrackSection(int victoryPoints, int firstTileNumber, int lastTileNumber) {
        this.popeTile = new PopeTile(victoryPoints);
        this.firstTileNumber = firstTileNumber;
        this.lastTileNumber = lastTileNumber;
    }

    /**
     * Triggers the activation of the section's Pope Tile
     */
    public void activatePopeTile(){
        this.popeTile.setActive();
    }

    /**
     * Gets the section's Pope Tile activation state
     * @return Pope tile activation state
     */
    public boolean isPopeTileActive(){
        return popeTile.isActive();
    }

    /**
     * Getter of the index of the section's first tile's
     * @return Index of the section's first tile's
     */
    public int getFirstTileNumber() {
        return firstTileNumber;
    }

    /**
     * Getter of the index of the section's last tile's
     * @return Index of the section's last tile's
     */
    public int getLastTileNumber() {
        return lastTileNumber;
    }

    /**
     * Gets the section's Pope Tile victory points
     * @return Pope Tile victory points
     */
    public int getVictoryPoints(){
        return this.popeTile.getVictoryPoints();
    }


}
