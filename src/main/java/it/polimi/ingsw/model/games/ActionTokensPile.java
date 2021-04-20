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

        actionTokens.add(new Add2BlackCrossStrategy("Add 2 Black Cross"));
        actionTokens.add(new Add2BlackCrossStrategy("Add 2 Black Cross"));
        actionTokens.add(new ShuffleActionTokenPileStrategy("Shuffle Action Tokens Pile"));
        actionTokens.add(new Discard2GreenStrategy("Discard 2 Green"));
        actionTokens.add(new Discard2BlueStrategy("Discard 2 Blue"));
        actionTokens.add(new Discard2PurpleStrategy("Discard 2 Purple"));
        actionTokens.add(new Discard2YellowStrategy("Discard 2 Yellow"));

        tokenPileStatus = new int[actionTokens.size()];

        for(int i = 0; i < tokenPileStatus.length; i++)
            tokenPileStatus[i] = i;

        shufflePile();
    }

    /**
     * Method that emulates the draw of the next Action Token of the pile and applies its effect.
     * @param solo The Solo Game which the effect of the drawn Action Token has to be applied on.
     */
    //TODO
    public String drawPile(SoloGame solo) {
        String str = actionTokens.get(tokenPileStatus[nextToDraw]).getId();

        nextToDraw++;
        actionTokens.get(tokenPileStatus[nextToDraw - 1]).activateEffect(solo);

        return str;
    }

    /**
     * Method that shuffles the status of the Action Token pile.
     */
    public void shufflePile(){
        Collections.shuffle(actionTokens);
        nextToDraw = 0;
    }
}
