package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class ActivateProductionStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;

    public ActivateProductionStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public void selectView(View view) {

    }
}
