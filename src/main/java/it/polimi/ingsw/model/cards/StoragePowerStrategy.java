package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Player;

/**
 * @author Giacomo Gumiero
 * Class that implements the storagePower power
 */
public class StoragePowerStrategy implements Power {

    public StoragePowerStrategy() {
    }

    @Override
    /**
     * activates the two extra depots of the given resource for the player
     */
    public void activatePower(Player p, Resource res) {
        p.addLeaderDepot(res);
    }
}