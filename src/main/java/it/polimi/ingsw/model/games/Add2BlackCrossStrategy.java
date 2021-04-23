package it.polimi.ingsw.model.games;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token effect that adds 2 Faith Points to the Black Cross indicator.
 */
public class Add2BlackCrossStrategy implements ActionToken{

    private final String id;

    public Add2BlackCrossStrategy() {
        id = "Added 2 Black Cross";
    }

    @Override
    public int activateEffect(SoloGame solo) {
        return solo.addFaithLorenzo(2);
    }

    @Override
    public String getId() {
        return id;
    }
}
