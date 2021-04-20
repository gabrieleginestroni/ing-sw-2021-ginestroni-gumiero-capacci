package it.polimi.ingsw.model.games;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that adds 2 Faith Points to the Black Cross indicator.
 */
public class Add2BlackCrossStrategy implements ActionToken{

    private final String id;

    public Add2BlackCrossStrategy(String id) {
        this.id = id;
    }

    @Override
    public void activateEffect(SoloGame solo) {
        solo.addFaithLorenzo(2);
    }

    @Override
    public String getId() {
        return id;
    }
}
