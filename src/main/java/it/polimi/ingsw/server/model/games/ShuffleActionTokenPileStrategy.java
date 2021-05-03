package it.polimi.ingsw.server.model.games;

/**
 * Class that represents the Action Token effect that adds 1 Faith Point to the Black Cross indicator and then shuffles the Action Token pile.
 */
public class ShuffleActionTokenPileStrategy implements ActionToken{

    private final String id;

    public ShuffleActionTokenPileStrategy() {
        id = "Gave Lorenzo 1 Faith Point and shuffled Action Tokens pile";
    }

    @Override
    public int activateEffect(SoloGame solo) {
        solo.shuffleTokenPile();
        return solo.addFaithLorenzo(1);
    }

    @Override
    public String getId() {
        return id;
    }
}
