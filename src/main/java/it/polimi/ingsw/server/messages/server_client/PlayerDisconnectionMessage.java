package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

public class PlayerDisconnectionMessage implements AnswerMessage{
    private String nickname;

    public PlayerDisconnectionMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitPlayerDisconnection(nickname);
    }
}
