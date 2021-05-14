package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.client_server.Message;

public interface State {
    void visitStartTurnState(int move, Controller controller);

}
