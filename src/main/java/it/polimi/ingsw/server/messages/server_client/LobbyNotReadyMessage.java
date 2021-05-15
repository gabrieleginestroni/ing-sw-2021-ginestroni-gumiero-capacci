package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class LobbyNotReadyMessage implements AnswerMessage {
    @Override
    public void selectView(View view) {
        view.visitLobbyNotReady("Lobby it's being creating, but not ready yet. Please choose another game ID:");
    }


}
