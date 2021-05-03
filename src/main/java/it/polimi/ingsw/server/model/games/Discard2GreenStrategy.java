package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Green Development cards from the grid.
 */
public class Discard2GreenStrategy implements ActionToken{

    private final String id;

    public Discard2GreenStrategy() {
        id = "Discarded 2 Green";
    }

    @Override
    public int activateEffect(SoloGame solo) {
        solo.discard2Cards(Color.GREEN);
        return -1;
    }

    @Override
    public String getId() {
        return id;
    }
}
