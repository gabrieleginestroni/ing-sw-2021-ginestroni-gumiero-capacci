package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.virtual_view.VirtualView;

public abstract class Controller {

    VirtualView virtualView;

    public Controller(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public abstract void handleMessage(Message message);
    public abstract boolean isGameOver();

    public VirtualView getVirtualView(){
        return this.virtualView;
    }
}
