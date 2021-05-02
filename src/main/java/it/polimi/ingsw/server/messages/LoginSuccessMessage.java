package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.controller.Player;

import java.util.List;

public class LoginSuccessMessage extends AnswerMessage {
    private final String currentPlayers;

    public LoginSuccessMessage(List<Player> currentPlayers) {
        this.currentPlayers = currentPlayers.toString();
    }

    @Override
    public void selectView() {
        System.out.println("Login success. Current players: " + currentPlayers);
    }
}
