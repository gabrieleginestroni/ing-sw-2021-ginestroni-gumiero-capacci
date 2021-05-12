package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class NicknameAlreadyUsedMessage implements AnswerMessage {
    private final String gameID;

    public NicknameAlreadyUsedMessage(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public void selectView(View view) {
        view.visitNicknameAlreadyUsed("Nickname already used. Please choose another nickname.",gameID);
    }
}
