package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Yellow Development cards from the grid.
 */
public class Discard2YellowStrategy implements ActionToken{

    private final String id;

    public Discard2YellowStrategy() {
        id = "Discarded 2 Yellow";
    }

    @Override
    public int activateEffect(SoloGame solo) {
        solo.discard2Cards(Color.YELLOW);
        return -1;
    }

    @Override
    public String getId() {
        return id;
    }
}
