package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;

/**
 * @author Giacomo Gumiero
 * Class that implements the whiteMarble power
 */
public class WhiteMarblePowerStrategy implements Power {

    public WhiteMarblePowerStrategy() {
    }

    @Override
    /**
     * activates the whiteMarble bonus of the given resource for the player
     */
    public void activatePower(Board p, Resource res) {
        p.addWhiteMarble(res);
    }
}