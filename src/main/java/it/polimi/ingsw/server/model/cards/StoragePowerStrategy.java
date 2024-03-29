package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;

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
    public void activatePower(Board p, Resource res) {
        p.addLeaderDepot(res);
    }
}