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

    /**
     * Pseudo-random initialization of the pile.
     */
    public ActionTokensPile(){
        actionTokens = new ArrayList<>();

        actionTokens.add(new Add2BlackCrossStrategy("Added 2 Black Cross"));
        actionTokens.add(new Add2BlackCrossStrategy("Added 2 Black Cross"));
        actionTokens.add(new ShuffleActionTokenPileStrategy("Gave Lorenzo 1 Faith Point and shuffled Action Tokens pile"));
        actionTokens.add(new Discard2GreenStrategy("Discarded 2 Green"));
        actionTokens.add(new Discard2BlueStrategy("Discarded 2 Blue"));
        actionTokens.add(new Discard2PurpleStrategy("Discarded 2 Purple"));
        actionTokens.add(new Discard2YellowStrategy("Discarded 2 Yellow"));

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
