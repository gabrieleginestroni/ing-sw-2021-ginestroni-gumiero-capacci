package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.*;

import java.util.Map;

/**
 * @author Giacomo Gumiero
 * Class of development card, used to gain special abilities during the game
 */
public class DevelopmentCard extends Card{
    private int level;
    private Color type;
    private Map<Resource, Integer> cost;
    private Map<Resource, Integer> productionInput;
    private Map<Resource, Integer> productionOutput;
    private int cardslot;

    public Map<Resource, Integer> produce() {
        return productionOutput;
    }

    @Override
    public String toString() {
        return "DevelopmentCards{" +
                "level=" + level +
                ", type=" + type +
                ", cost=" + cost +
                ", productionInput=" + productionInput +
                ", productionOutput=" + productionOutput +
                ", cardslot=" + cardslot +
                super.toString() +
                '}';
    }
}
