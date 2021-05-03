package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.controller.Player;

import java.util.List;

public class LoginSuccessMessage extends AnswerMessage {
    private final String currentPlayers;

    public LoginSuccessMessage(List<Player> currentPlayers) {
        StringBuilder str = new StringBuilder();

        for(Player player : currentPlayers)
            str.append(player.getNickname()).append("\n");

        this.currentPlayers = str.toString();
    }

    @Override
    public void selectView(CLI view) {
        view.showMessage("Login success. Current players: \n" + currentPlayers);
    }

    @Override
    public void selectView(GUI gui) {

    }
}
