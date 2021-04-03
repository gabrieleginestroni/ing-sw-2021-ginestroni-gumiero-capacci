package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Player;

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
    public void activatePower(Player p, Resource res) {
        p.addDiscount(res);
    }
}
