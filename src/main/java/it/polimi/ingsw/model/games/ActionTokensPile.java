package it.polimi.ingsw.model.games;

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

        Collections.shuffle(actionTokens);

        nextToDraw = 0;
    }

    /**
     * Method that emulates the draw of the next Action Token of the pile and applies its effect.
     * @param solo The Solo Game which the effect of the drawn Action Token has to be applied on.
     */
    public void drawPile(SoloGame solo) {
        actionTokens.get(tokenPileStatus[nextToDraw]).activateEffect(solo);
        nextToDraw++;
    }

    /**
     * Method that shuffles the status of the Action Token pile.
     */
    public void shufflePile(){
        Collections.shuffle(actionTokens);
        nextToDraw = 0;
    }
}
