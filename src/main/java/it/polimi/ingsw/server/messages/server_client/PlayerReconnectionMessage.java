package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

public class PlayerReconnectionMessage implements AnswerMessage{
    private String nickname;

    public PlayerReconnectionMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitPlayerReconnection(nickname);
    }
}
