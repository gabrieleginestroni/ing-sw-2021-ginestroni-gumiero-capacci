package it.polimi.ingsw.server.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TurnHandler {
    private final ArrayList<Player> players;
    int currentPlayer;
    private final Map<Player,Integer> disconnectedPlayers;

    public TurnHandler(ArrayList<Player> players) {
        this.players = players;
        this.disconnectedPlayers = new HashMap<>();
        currentPlayer = 0;
    }

    public void nextPlayer(){
        if(isRoundOver())
            currentPlayer = 0;
        else
            currentPlayer++;
    }

    public void notifyPlayerDisconnection(Player player){
        int index = players.indexOf(player);
        players.remove(player);
        disconnectedPlayers.put(player,index);
    }
    public void notifyPlayerReconnection(Player player){
        int index = disconnectedPlayers.get(player);
        disconnectedPlayers.remove(player);
        players.add(index,player);

    }
    public boolean isRoundOver(){
        return currentPlayer == players.size() - 1;
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public List<Player> getOtherPlayers(){
        return this.players.stream().filter(p -> p != getCurrentPlayer()).collect(Collectors.toList());
    }
    public int getConnectedPlayersNumber(){
        return this.players.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
