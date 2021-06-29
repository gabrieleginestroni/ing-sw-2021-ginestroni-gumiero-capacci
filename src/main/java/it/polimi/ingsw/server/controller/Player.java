package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.virtual_view.BoardObserver;
import it.polimi.ingsw.server.virtual_view.VirtualView;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that contains all the information relative to one of the players.
 */
public class Player {
    private final String nickname;
    private Board board;
    private BoardObserver boardObserver;
    private transient ClientHandler clientHandler;

    /**
     * @param nickname The nickname the player chose in the login phase of the game.
     * @param clientHandler The ClientHandler instance the server explicitly created to handle the communication
     *                      with the socket of the player.
     */
    public Player(String nickname, ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.nickname = nickname;
        this.boardObserver = null;
    }

    /**
     * @return Player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method is used to create the Board of the player and its relative BoardObserver.
     * @param game The game in which the player plays.
     * @param virtualView The VirtualView of the game in which the player plays.
     */
    public void buildBoard(Game game, VirtualView virtualView){
        this.boardObserver = new BoardObserver(this.nickname,virtualView);
        board = new Board(game,boardObserver);
    }

    /**
     * @return The BoardObserver relative to the Board of the player.
     */
    public BoardObserver getBoardObserver () {return boardObserver;}

    /**
     * @return The Board of the player.
     */
    public Board getBoard(){ return board; }

    /**
     * @return The ClientHandler of the player.
     */
    public ClientHandler getClientHandler(){return this.clientHandler;}

    /**
     * This method is used to update the reference of the ClientHandler used for the player in case
     * he disconnects and then reconnects to the game: when he does it the socket changes and so has to do the
     * relative ClientHandler.
     * @param newClientHandler The new ClientHandler.
     */
    public void refreshClientHandler(ClientHandler newClientHandler){
        this.clientHandler = newClientHandler;
    }
}
