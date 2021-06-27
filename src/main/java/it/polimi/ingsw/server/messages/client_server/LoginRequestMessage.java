package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains game id and nickname used to log the client into a lobby
 */
public class LoginRequestMessage implements Message  {
    private final String requestedGameID;
    private final String nickname;

    /**
     *
     * @param requestedGameID Id of the lobby to create or join
     * @param nickname Client nickname
     */
    public LoginRequestMessage(String requestedGameID, String nickname) {
        this.requestedGameID = requestedGameID;
        this.nickname = nickname;
    }

    /**
     *
     * @return Id of the lobby to create or join
     */
    public String getRequestedGameID() {
        return requestedGameID;
    }

    /**
     *
     * @return Client nickname
     */
    public String getNickname() {
        return nickname;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
