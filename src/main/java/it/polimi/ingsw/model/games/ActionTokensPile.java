package it.polimi.ingsw.model.games;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token pile used in the solo version of the game.
 */
public class ActionTokensPile {

    private int[] tokenPileStatus;
    private List<ActionToken> actionTokens;
    private int nextToDraw;

    public ActionTokensPile(){
        actionTokens = new ArrayList<>();

        actionTokens.add(new Add2BlackCrossStrategy());
        actionTokens.add(new Add2BlackCrossStrategy());
        actionTokens.add(new ShuffleActionTokenPileStrategy());
        actionTokens.add(new Discard2GreenStrategy());
        actionTokens.add(new Discard2BlueStrategy());
        actionTokens.add(new Discard2PurpleStrategy());
        actionTokens.add(new Discard2YellowStrategy());

        tokenPileStatus = new int[actionTokens.size()];

        int randomNumber;
        int temp;

        for(int i = 0; i < tokenPileStatus.length; i++)
            tokenPileStatus[i] = i;

        shufflePile();
        shufflePile();
    }

    /**
     * Method that emulates the draw of the next Action Token of the pile and applies its effect.
     * @param solo The Solo Game which the effect of the drawn Action Token has to be applied on.
     */
    public void drawPile(SoloGame solo) { //throws vaticanReportActivated{
        actionTokens.get(tokenPileStatus[nextToDraw]).activateEffect(solo);
        nextToDraw++;
    }

    /**
     * Method that shuffles the status of the Action Token pile.
     */
    public void shufflePile(){

        for(int max = tokenPileStatus.length - 1 ; max > 0 ; max--){
            int randomNumber = ThreadLocalRandom.current().nextInt(0, max + 1);
            int temp = tokenPileStatus[randomNumber];
            tokenPileStatus[randomNumber] = tokenPileStatus[max];
            tokenPileStatus[max] = temp;
        }

        nextToDraw = 0;
    }
}
