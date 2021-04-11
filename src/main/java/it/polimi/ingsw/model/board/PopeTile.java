package it.polimi.ingsw.model.board;

/**
 * @author Gabriele Ginestroni
 * Class that represents a Pope Favor tile, its activation state and Victory Points
 */
public class PopeTile {

    private final int victoryPoints;
    private boolean active;

    /**Constructor of Pope Tile
     *
     * @param victoryPoints Number of victory points of the pope tile, involved in the count of player's total victory
     *                     points at the end of the game if the pope tile has been activated.
     */
    public PopeTile(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        this.active = false;
    }

    /**
     * Victory Points getter.
     * @return Victory Points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Activation state getter.
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets activation state to "TRUE".
     */
    public void setActive(){
        active = true;
    }

}
