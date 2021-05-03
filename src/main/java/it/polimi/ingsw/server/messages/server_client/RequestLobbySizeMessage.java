package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class RequestLobbySizeMessage extends AnswerMessage {
    @Override
    public void selectView(CLI view) {
        view.showMessage("A game with the given ID doesn't exist. Type the size of the game you'd like:");
    }

    @Override
    public void selectView(GUI gui) {

    }
}
