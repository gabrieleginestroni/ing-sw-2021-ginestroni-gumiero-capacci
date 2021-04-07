package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.games.Game;

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

    public void buildBoard(Game g){
        board = new Board(g);
    }

    public Board getBoard(){
        return board;
    }
}
