package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.ClientView;

import java.io.Serializable;

public abstract class AnswerMessage implements Serializable {
    public abstract void selectView();
}
