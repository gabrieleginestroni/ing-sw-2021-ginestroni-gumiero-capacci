package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

public class Ping implements AnswerMessage {
    @Override
    public void selectView(View view) throws invalidClientInputException {

    }
}
