package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.games.Game;
import it.polimi.ingsw.virtualview.BoardObserver;
import it.polimi.ingsw.virtualview.VirtualView;

public class Player {

    private final String nickname;
    private Board board;
    private final BoardObserver boardObserver;
    private final ClientHandler clientHandler;

    public Player(String nickname, ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        this.boardObserver = new BoardObserver(this);
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

    public void setVirtualView(VirtualView virtualView){
        this.boardObserver.setVirtualView(virtualView);

    }
    public ClientHandler getClientHandler(){return this.clientHandler;}


}
