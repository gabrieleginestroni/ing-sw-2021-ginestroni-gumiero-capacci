package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final List<Player> players;
    private int size;
    private final Controller controller;
    private final String gameID;

    public Lobby(String gameID) {
        this.players = new ArrayList<>();
        this.size = 0;
        this.gameID = gameID;
        this.controller = null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Integer getSize() {
        return size;
    }

    public Controller getController() {
        return controller;
    }

    public String getGameID() {
        return gameID;
    }

    public boolean isFull(){
        return size == players.size();
    }

    public void addPlayer(String nickname, ClientHandler clientHandler){
        this.players.add(new Player(nickname,clientHandler));

    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
