package it.polimi.ingsw.model.games;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that adds 2 Faith Points to the Black Cross indicator.
 */
public class Add2BlackCrossStrategy implements ActionToken{

    @Override
    public void activateEffect(SoloGame solo) {

        solo.addFaithLorenzo(2);

    }
}
