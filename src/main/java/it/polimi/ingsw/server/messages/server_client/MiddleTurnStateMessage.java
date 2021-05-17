package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class MiddleTurnStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;
    private final String errorMessage;

    public MiddleTurnStateMessage(String currentPlayerNickname,String errorMessage) {
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }

    @Override
    public void selectView(View view) {
        view.visitMiddleTurn(this.currentPlayerNickname,this.errorMessage);
    }
}
