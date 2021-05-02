package it.polimi.ingsw.server.messages;

import java.io.Serializable;

public class LoginRequestMessage implements Serializable {
    private final String requestedGameID;
    private final String nickname;

    public LoginRequestMessage(String requestedGameID, String nickname) {
        this.requestedGameID = requestedGameID;
        this.nickname = nickname;
    }

    public String getRequestedGameID() {
        return requestedGameID;
    }

    public String getNickname() {
        return nickname;
    }
}
