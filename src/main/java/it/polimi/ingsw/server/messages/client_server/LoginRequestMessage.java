package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class LoginRequestMessage implements Message  {
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

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
