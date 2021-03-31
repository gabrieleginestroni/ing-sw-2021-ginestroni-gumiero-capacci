package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.*;
import java.util.Map;

/**
 * @author Giacomo Gumiero
 * Class of leader card, used to gain special power during the game
 */
public class LeaderCard extends Card{
    private Map<String, Map<String, Integer>> requirements;
    private Power powerType;
    private String power;
    private Resource resource;
    private boolean active;

    /**
     * Creates the strategy power of the card
     */
    public void createPower(){
        if(this.power.equals("production"))
            this.powerType = new ProductionPowerStrategy();
        else if(this.power.equals("discount"))
            this.powerType = new DiscountPowerStrategy();
        else if(this.power.equals("whiteMarble"))
            this.powerType = new WhiteMarblePowerStrategy();
        else if(this.power.equals("depots"))
            this.powerType = new StoragePowerStrategy();
    }

    @Override
    public String toString() {
        return "LeaderCards{"+
                ", requirements=" + requirements +
                ", powerType=" + powerType +
                ", power='" + power +
                ", resource=" + resource +
                ", active=" + active +
                super.toString() +
                '}';
    }
}
