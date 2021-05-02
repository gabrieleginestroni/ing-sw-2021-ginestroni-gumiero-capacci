package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    List<Player> players;
    Integer size;
    Controller controller;
    String gameID;

    public Lobby(Integer size, String gameID) {
        this.players = new ArrayList<>();
        this.size = size;
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
}
