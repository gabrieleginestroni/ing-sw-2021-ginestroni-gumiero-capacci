package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;

import java.io.Serializable;

public interface AnswerMessage extends Serializable {
    public abstract void selectView(View view);


}
