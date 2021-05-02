package it.polimi.ingsw.server.messages;

import java.io.Serializable;

public class LoginSizeMessage implements Serializable {
    private int size;

    public LoginSizeMessage(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
