package it.polimi.ingsw.model.games;

/**
 * @author Tommaso Capacci
 * Class that represents the Pope tile, with its activation state and Victory Points
 */
public class PopeTile {

    private final int victoryPoints;
    private boolean active;

    public PopeTile(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        active = false;
    }

    /**
     * Victory Points getter.
     * @return vVictory Points
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
