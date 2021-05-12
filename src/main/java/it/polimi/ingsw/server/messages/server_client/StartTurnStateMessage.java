package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class StartTurnStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;

    public StartTurnStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public void selectView(View view) {
        view.visitStartTurn(currentPlayerNickname);
    }
}
