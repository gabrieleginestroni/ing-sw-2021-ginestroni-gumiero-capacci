package it.polimi.ingsw.server.messages.client_server;

import java.io.Serializable;

public class LoginRequestMessage extends Message  {
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
