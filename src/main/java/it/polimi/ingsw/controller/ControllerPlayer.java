package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Board;

public class ControllerPlayer {
    private String ipAddress;
    private int portNumber;
    private String nickname;
    private Board board;

    public ControllerPlayer(String ipAddress, int portNumber, String nickname) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void buildPlayer(){
        board = new Board();
    }

    public Board getPlayer(){
        return board;
    }
}
