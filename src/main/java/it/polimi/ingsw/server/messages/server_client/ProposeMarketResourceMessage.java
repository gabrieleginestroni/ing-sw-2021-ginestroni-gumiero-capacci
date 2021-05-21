package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

public class ProposeMarketResourceMessage implements AnswerMessage {
    private final Resource proposedRes;
    private final String currentPlayerNickname;
    private final String errorMessage;

    public ProposeMarketResourceMessage(Resource proposedRes, String currentPlayerNickname, String errorMessage) {
        this.proposedRes = proposedRes;
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }

    @Override
    public void selectView(View view) {
        view.visitResourceManagementState(this.proposedRes,this.currentPlayerNickname,this.errorMessage);
    }
}
