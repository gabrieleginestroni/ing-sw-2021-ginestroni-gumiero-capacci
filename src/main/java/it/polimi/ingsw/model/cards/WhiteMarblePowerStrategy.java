package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Player;

/**
 * @author Giacomo Gumiero
 * Class that implements the whiteMarble power
 */
public class WhiteMarblePowerStrategy implements Power {

    public WhiteMarblePowerStrategy() {
    }

    @Override
    public String toString() {
        return "POWER WhiteMarblePowerStrategy";
    }

    @Override
    /**
     * activates the whiteMarble bonus of the given resource for the player
     */
    public void activatePower(Player p, Resource res) {
        p.addWhiteMarble(res);
    }
}