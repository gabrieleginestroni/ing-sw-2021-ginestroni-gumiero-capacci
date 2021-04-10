package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.games.Game;

public class ControllerPlayer {
    private final String ipAddress;
    private final int portNumber;
    private final String nickname;
    private Board board;

    public ControllerPlayer(String ipAddress, int portNumber, String nickname) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void buildBoard(Game game){
        board = new Board(game);
    }

    public Board getBoard(){
        return board;
    }
}
