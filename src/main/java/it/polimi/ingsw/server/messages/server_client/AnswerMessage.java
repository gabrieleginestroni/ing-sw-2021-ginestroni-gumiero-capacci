package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

import java.io.Serializable;

public interface AnswerMessage extends Serializable {
    void selectView(View view) throws invalidClientInputException;


}
