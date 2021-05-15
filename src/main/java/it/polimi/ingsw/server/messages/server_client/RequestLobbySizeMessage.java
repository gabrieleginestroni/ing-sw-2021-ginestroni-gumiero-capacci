package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class RequestLobbySizeMessage implements AnswerMessage {
    @Override
    public void selectView(View view) {
        view.visitRequestLobbySize("A game with the given ID doesn't exist. Type the size of the game you'd like:");
    }

}
