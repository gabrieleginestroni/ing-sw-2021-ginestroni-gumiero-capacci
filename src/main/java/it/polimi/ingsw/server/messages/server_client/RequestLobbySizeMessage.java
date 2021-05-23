package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

public class RequestLobbySizeMessage implements AnswerMessage {
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitRequestLobbySize("A game with the given ID doesn't exist. Type the size of the game you'd like:");
    }

}
