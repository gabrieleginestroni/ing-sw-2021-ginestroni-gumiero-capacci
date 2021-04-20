package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Purple Development cards from the grid.
 */
public class Discard2PurpleStrategy implements ActionToken{

    private final String id;

    public Discard2PurpleStrategy(String id) {
        this.id = id;
    }

    @Override
    public void activateEffect(SoloGame solo) {
        solo.discard2Cards(Color.PURPLE);
    }

    @Override
    public String getId() {
        return id;
    }
}
