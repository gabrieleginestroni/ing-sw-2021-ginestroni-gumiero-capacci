package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Player;

public class ControllerPlayer {
    private String ipAddress;
    private int portNumber;
    private String nickname;
    private Player player;

    public String getNickname() {
        return nickname;
    }

    public void buildPlayer(){
        player = new Player(nickname);
    }

    public Player getPlayer(){
        return player;
    }
}
