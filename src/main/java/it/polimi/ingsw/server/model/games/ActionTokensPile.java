package it.polimi.ingsw.server.model.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Tommaso Capacci
 * Class that represents the Action Token pile used in the solo version of the game.
 */
public class ActionTokensPile {

    private final int[] tokenPileStatus;
    private final List<ActionToken> actionTokens;
    private int nextToDraw;

    /**
     * Pseudo-random initialization of the pile.
     */
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

        for(int i = 0; i < tokenPileStatus.length; i++)
            tokenPileStatus[i] = i;

        shufflePile();
    }

    /**
     * Method that emulates the draw of the next Action Token of the pile and applies its effect.
     * @param solo The Solo Game which the effect of the drawn Action Token has to be applied on.
     * @return -1 if the drawn Action Token does not activate any Vatican Report, 0 if it activates the first Vatican Report, 1 for the second and 2 for the last one.
     */
    public int drawPile(SoloGame solo) {
        nextToDraw++;

        solo.notifyDrawnActionToken(actionTokens.get(tokenPileStatus[nextToDraw - 1]).getId());

        return actionTokens.get(tokenPileStatus[nextToDraw - 1]).activateEffect(solo);
    }

    /**
     * Method that shuffles the status of the Action Token pile.
     */
    public void shufflePile(){
        Collections.shuffle(actionTokens);
        nextToDraw = 0;
    }
}
