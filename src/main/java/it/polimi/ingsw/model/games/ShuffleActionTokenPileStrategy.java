package it.polimi.ingsw.model.games;

/**
 * Class that represents the Action Token effect that adds 1 Faith Point to the Black Cross indicator and then shuffles the Action Token pile.
 */
public class ShuffleActionTokenPileStrategy implements ActionToken{

    @Override
    public void activateEffect(SoloGame solo) throws vaticanReportActivated{
        solo.shuffleTokenPile();
        solo.addFaithLorenzo(1);
    }
}
