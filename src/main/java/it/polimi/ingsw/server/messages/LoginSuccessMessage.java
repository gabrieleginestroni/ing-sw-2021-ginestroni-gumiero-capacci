package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.controller.Player;

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
    public void selectView() {
        System.out.println("Login success. Current players: \n" + currentPlayers);
    }
}
