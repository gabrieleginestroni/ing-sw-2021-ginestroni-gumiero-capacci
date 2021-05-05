package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.SoloController;
import it.polimi.ingsw.server.messages.client_server.Message;

public interface SoloState {
    void handleInput(Message message, SoloController controller);
}
