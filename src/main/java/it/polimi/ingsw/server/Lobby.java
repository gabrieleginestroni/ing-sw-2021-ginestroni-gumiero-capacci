package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.ControllerFactory;
import it.polimi.ingsw.server.controller.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Lobby {
    private final String gameID;
    private final List<Player> players;
    private int size;
    private Controller controller;
    private boolean gameStarted;

    public Lobby(String gameID) {
        this.players = new ArrayList<>();
        this.size = 0;
        this.gameID = gameID;
        this.controller = null;
        this.gameStarted = false;
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

    public synchronized boolean isFull(){
        return size == players.size();
    }

    public synchronized void addPlayer(String nickname, ClientHandler clientHandler){
        this.players.add(new Player(nickname,clientHandler));
    }

    public synchronized void startGame(){

        controller = ControllerFactory.getController(players);
        gameStarted = true;

    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isNicknameUsed(String nickname){
        return players.stream().anyMatch(p -> p.getNickname().equalsIgnoreCase(nickname));
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}


