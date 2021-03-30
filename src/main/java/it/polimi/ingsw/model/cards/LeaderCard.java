package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.*;
import java.util.Map;

/**
 * @author Giacomo Gumiero
 * Class of leader card, used to gain special power during the game
 */
public class LeaderCard extends Card{
    private int id;
    private Map<String, Map<String, Integer>> requirements;
    private Power powerType;
    private String power;
    private Resource resource;
    private boolean active;

    public void createPower(){
        if(this.power.equals("production"))
            this.powerType = new ProductionPowerStrategy();
        else
            this.powerType = new ProductionPowerStrategy();
    }

    @Override
    public String toString() {
        return "LeaderCards{" +
                "id=" + id +
                ", requirements=" + requirements +
                ", powerType=" + powerType +
                ", power='" + power +
                ", resource=" + resource +
                ", active=" + active +
                super.toString() +
                '}';
    }
}
