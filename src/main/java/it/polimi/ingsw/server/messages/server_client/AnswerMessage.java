package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

import java.io.Serializable;

public abstract class AnswerMessage implements Serializable {
    public abstract void selectView(CLI cli);
    public abstract void selectView(GUI gui);

}
