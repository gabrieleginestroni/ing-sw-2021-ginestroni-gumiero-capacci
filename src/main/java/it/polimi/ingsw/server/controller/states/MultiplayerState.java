package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.MultiplayerController;
import it.polimi.ingsw.server.messages.client_server.Message;

public interface MultiplayerState {
    void handleInput(Message message, MultiplayerController controller);
}
