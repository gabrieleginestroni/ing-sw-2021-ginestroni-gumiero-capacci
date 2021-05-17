package it.polimi.ingsw.server.model.cards;
import it.polimi.ingsw.server.model.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Giacomo Gumiero
 * Class of leader card, used to gain special power during the game
 */
public class LeaderCard extends Card{
    private List<CardRequirement> cardRequirements;
    private List<ResourceRequirement> resourceRequirements;
    private String power;
    private Resource resource;
    private boolean active;

    /**
     * Activates the card, implementing the strategy power
     */
    public void activateCard(){
        Power powerType;
        switch (this.power) {
            case "production":
                powerType = new ProductionPowerStrategy();
                break;
            case "discount":
                powerType = new DiscountPowerStrategy();
                break;
            case "whiteMarble":
                powerType = new WhiteMarblePowerStrategy();
                break;
            default: //"depots"
                powerType = new StoragePowerStrategy();
                //throw new powerMissingException();
        }
        this.active = true;
        powerType.activatePower(super.getOwner(), resource);
    }

    public void discardCard(){
        this.setOwner(null);
    }

    public String getPower() {
        return power;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean isActive() {
        return active;
    }

    public List<CardRequirement> getCardRequirements() {
        if(cardRequirements != null)
            return new ArrayList<>(cardRequirements);
        return new ArrayList<>();
    }

    public List<ResourceRequirement> getResourceRequirements() {
        if(resourceRequirements != null)
            return new ArrayList<>(resourceRequirements);
        return new ArrayList<>();
    }
}
