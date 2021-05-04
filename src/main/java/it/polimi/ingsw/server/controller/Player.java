package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.virtual_view.BoardObserver;
import it.polimi.ingsw.server.virtual_view.VirtualView;

public class Player {

    private final String nickname;
    private Board board;
    private BoardObserver boardObserver;
    private transient final ClientHandler clientHandler;

    public Player(String nickname, ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        this.boardObserver = null;
    }

    public String getNickname() {
        return nickname;
    }

    public void buildBoard(Game game, VirtualView virtualView){
        this.boardObserver = new BoardObserver(this.nickname,virtualView);
        board = new Board(game,boardObserver);
    }

    public BoardObserver getBoardObserver () {return boardObserver;}

    public Board getBoard(){
        return board;
    }


    public ClientHandler getClientHandler(){return this.clientHandler;}


}
