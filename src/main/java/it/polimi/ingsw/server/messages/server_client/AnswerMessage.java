package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

import java.io.Serializable;

public interface AnswerMessage extends Serializable {
    void selectView(View view);


}
