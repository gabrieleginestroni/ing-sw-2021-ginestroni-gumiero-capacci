package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.board.*;

/**
 * @author Giacomo Gumiero
 * Interface that represents a power
 */
public interface Power {

    public void activatePower(Board p, Resource res);
}
