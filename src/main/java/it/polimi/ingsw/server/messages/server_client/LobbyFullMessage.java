package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;

public class LobbyFullMessage implements AnswerMessage {
    @Override
    public void selectView(View view) {
        view.visitLobbyFull("Game full. Please choose another game ID:");
    }


}
