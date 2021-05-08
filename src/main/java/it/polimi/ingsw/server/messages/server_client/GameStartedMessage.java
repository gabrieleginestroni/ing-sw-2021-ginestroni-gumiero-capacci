package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;

public class GameStartedMessage implements AnswerMessage {
    @Override
    public void selectView(View view) {
        view.visitGameStarted("Game started");
    }


}
