package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Blue Development cards from the grid.
 */
public class Discard2BlueStrategy implements ActionToken{

    private final String id;

    public Discard2BlueStrategy(String id) {
        this.id = id;
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
