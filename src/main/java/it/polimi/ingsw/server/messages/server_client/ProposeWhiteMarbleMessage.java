package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to announce the phase of the Market Action in which, for every White Marble
 * obtained with a move from the Market, the player has to choose in which resource trasform it: this happens
 * only when the player that requested the Market Action has 2 White Marble Effects activated.
 */
public class ProposeWhiteMarbleMessage implements AnswerMessage {
    private final Resource res1;
    private final Resource res2;

    /**
     * @param res1 The resource that represents the first White Marble Effect.
     * @param res2 The resource that represents the second White Marble Effect.
     */
    public ProposeWhiteMarbleMessage(Resource res1, Resource res2) {
        this.res1 = res1;
        this.res2 = res2;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitWhiteMarbleProposal(res1,res2);
    }
}
