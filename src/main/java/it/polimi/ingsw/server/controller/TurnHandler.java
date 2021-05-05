package it.polimi.ingsw.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TurnHandler {
    private final ArrayList<Player> players;
    int currentPlayer;

    public TurnHandler(ArrayList<Player> players) {
        this.players = players;
        currentPlayer = 0;
    }

    public void nextPlayer(){
        if(isRoundOver())
            currentPlayer = 0;
        else
            currentPlayer++;
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
}
