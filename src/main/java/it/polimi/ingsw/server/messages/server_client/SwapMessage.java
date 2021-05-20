package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class SwapMessage implements AnswerMessage {
    private final String currentPlayerNickname;
    private final String errorMessage;

    @Override
    public void selectView(View view) {
        view.visitSwapState(this.currentPlayerNickname,this.errorMessage);
    }

    public SwapMessage(String currentPlayerNickname, String errorMessage) {
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }
}
