package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;

/**
 * @author Giacomo Gumiero
 * Interface that represents a power
 */
public interface Power {

    public void activatePower(Board p, Resource res);
}
