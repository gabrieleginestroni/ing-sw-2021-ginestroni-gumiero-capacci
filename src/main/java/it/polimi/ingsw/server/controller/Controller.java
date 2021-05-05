package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.messages.client_server.Message;

public interface Controller {

    void handleMessage(Message message);
    boolean isGameOver();
}
