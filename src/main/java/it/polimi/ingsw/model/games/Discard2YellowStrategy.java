package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that discards 2 Yellow Development cards from the grid.
 */
public class Discard2YellowStrategy implements ActionToken{

    @Override
    public void activateEffect(SoloGame solo) {
        solo.discard2Cards(Color.YELLOW);
    }
}
