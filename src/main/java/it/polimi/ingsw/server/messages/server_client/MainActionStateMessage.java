package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class MainActionStateMessage  implements AnswerMessage{
    private final String errorMessage;
    private final String currentPlayerNickname;

    public MainActionStateMessage(String currentPlayerNickname,String errorMessage) {
        this.errorMessage = errorMessage;
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public void selectView(View view) {
        view.visitMainActionState(currentPlayerNickname,errorMessage);

    }
}
