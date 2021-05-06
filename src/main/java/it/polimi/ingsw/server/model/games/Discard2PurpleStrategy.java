package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Purple Development cards from the grid.
 */
public class Discard2PurpleStrategy implements ActionToken{

    private final String id;

    public Discard2PurpleStrategy() {
        id = "Discarded 2 Purple";
    }

    @Override
    public int activateEffect(SoloGame solo) {
        solo.discard2Cards(Color.PURPLE);
        return -1;
    }

    @Override
    public String getId() {
        return id;
    }
}