package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.games.Game;
import it.polimi.ingsw.virtualview.BoardObserver;

public class Player {
    private final String ipAddress;
    private final int portNumber;
    private final String nickname;
    private Board board;
    private final BoardObserver boardObserver;

    public Player(String ipAddress, int portNumber, String nickname) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        this.nickname = nickname;
        this.boardObserver = new BoardObserver(this.nickname);
    }

    public String getNickname() {
        return nickname;
    }

    public void buildBoard(Game game){
        board = new Board(game,boardObserver);
    }

    public BoardObserver getBoardObserver () {return boardObserver;}

    public Board getBoard(){
        return board;
    }
}
