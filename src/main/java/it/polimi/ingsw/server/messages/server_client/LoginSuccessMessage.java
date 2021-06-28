package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.controller.Player;

import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used in the login phase of the game to communicate all the client connected to a certain lobby that
 * someone new just connected.
 */
public class LoginSuccessMessage implements AnswerMessage {
    private final String currentPlayers;

    /**
     * @param currentPlayers The list of the players currently present inside the lobby.
     */
    public LoginSuccessMessage(List<Player> currentPlayers) {
        StringBuilder str = new StringBuilder();

        for(Player player : currentPlayers)
            str.append(player.getNickname()).append("\n");

        this.currentPlayers = str.toString();
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitLoginSuccess(currentPlayers);
    }
}
