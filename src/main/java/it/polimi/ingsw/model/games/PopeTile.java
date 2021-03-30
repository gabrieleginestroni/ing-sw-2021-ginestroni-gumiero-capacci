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
     * Getter
     * @return Victory Points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Getter
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Active setter
     */
    public void setActive(){
        active = true;
    }

}
