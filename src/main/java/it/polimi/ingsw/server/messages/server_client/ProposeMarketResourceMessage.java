package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to announce the phase of the Market Action in which all the resource
 * obtained with a move from the Market are, one at a time, proposed to the current player.
 */
public class ProposeMarketResourceMessage implements AnswerMessage {
    private final Resource proposedRes;
    private final String currentPlayerNickname;
    private final String errorMessage;

    /**
     * @param proposedRes The proposed resource.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public ProposeMarketResourceMessage(Resource proposedRes, String currentPlayerNickname, String errorMessage) {
        this.proposedRes = proposedRes;
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitResourceManagementState(this.proposedRes,this.currentPlayerNickname,this.errorMessage);
    }
}
