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

    /**
     * @return Map<Resource, Integer> Map of resources that can be produced by the card
     */
    public Map<Resource, Integer> getProductionOutput() {
        return productionOutput;
    }

    public Map<Resource, Integer> getProductionInput() { return productionInput; }

    public Map<Resource, Integer> getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public Color getType() {
        return type;
    }


}
