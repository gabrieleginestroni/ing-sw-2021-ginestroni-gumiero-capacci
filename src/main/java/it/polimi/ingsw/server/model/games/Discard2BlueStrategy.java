package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Blue Development cards from the grid.
 */
public class Discard2BlueStrategy implements ActionToken{

    private final String id;

    public Discard2BlueStrategy() {
        id = "Discarded 2 Blue";
    }

    @Override
    public int activateEffect(SoloGame solo) {
        solo.discard2Cards(Color.BLUE);
        return -1;
    }

    @Override
    public String getId() {
        return id;
    }
}
