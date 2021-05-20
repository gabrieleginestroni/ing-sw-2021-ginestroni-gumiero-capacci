package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

public class ProposeWhiteMarbleMessage implements AnswerMessage {
    private final Resource res1;
    private final Resource res2;

    public ProposeWhiteMarbleMessage(Resource res1, Resource res2) {
        this.res1 = res1;
        this.res2 = res2;
    }

    @Override
    public void selectView(View view) {
        view.visitWhiteMarbleProposal(res1,res2);
    }
}
