package it.polimi.ingsw.server.messages.client_server;

import java.io.Serializable;

public class LoginSizeMessage implements Serializable {
    private final int size;

    public LoginSizeMessage(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
