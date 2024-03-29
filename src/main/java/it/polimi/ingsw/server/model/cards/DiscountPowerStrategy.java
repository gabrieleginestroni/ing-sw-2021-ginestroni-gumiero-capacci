package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;

/**
 * @author Giacomo Gumiero
 * Class that implements the discount power
 */
public class DiscountPowerStrategy implements Power {

    public DiscountPowerStrategy() {
    }

    @Override
    /**
     * activates the discount of the given resource for the player
     */
    public void activatePower(Board p, Resource res) {
        p.addDiscount(res);
    }
}
