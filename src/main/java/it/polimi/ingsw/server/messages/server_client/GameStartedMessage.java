package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class GameStartedMessage extends AnswerMessage {
    @Override
    public void selectView(CLI cli) {
        cli.showMessage("Game started");
    }

    @Override
    public void selectView(GUI gui) {

    }
}
