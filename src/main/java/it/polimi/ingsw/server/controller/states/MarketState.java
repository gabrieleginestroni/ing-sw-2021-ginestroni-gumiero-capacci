package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.MultiplayerController;
import it.polimi.ingsw.server.controller.SoloController;
import it.polimi.ingsw.server.messages.client_server.Message;

public class MarketState implements MultiplayerState,SoloState {
    @Override
    public void handleInput(Message message, MultiplayerController controller) {

    }

    @Override
    public void handleInput(Message message, SoloController controller) {

    }
}
