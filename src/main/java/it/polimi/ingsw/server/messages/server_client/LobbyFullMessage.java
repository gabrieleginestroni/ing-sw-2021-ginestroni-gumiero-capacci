package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class LobbyFullMessage extends AnswerMessage {
    @Override
    public void selectView(CLI view) {
        view.showMessage("Game full. Please choose another game ID:");
    }

    @Override
    public void selectView(GUI view) {

    }
}
