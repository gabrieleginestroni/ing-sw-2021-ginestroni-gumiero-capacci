package it.polimi.ingsw.model.games;

/**
 * Class that represents the Action Token effect that adds 1 Faith Point to the Black Cross indicator and then shuffles the Action Token pile.
 */
public class ShuffleActionTokenPileStrategy implements ActionToken{

    private final String id;

    public ShuffleActionTokenPileStrategy(String id) {
        this.id = id;
    }

    @Override
    public void activateEffect(SoloGame solo) {
        solo.shuffleTokenPile();
        solo.addFaithLorenzo(1);
    }

    @Override
    public String getId() {
        return id;
    }
}
