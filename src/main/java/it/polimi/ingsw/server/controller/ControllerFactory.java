package it.polimi.ingsw.server.controller;

import java.util.List;

public class ControllerFactory {
    public static Controller getController(List<Player> players, String gameID){
        if(players.size() == 1)
            return new SoloController(players.get(0),gameID);
        else
            return new MultiplayerController(players,gameID);
    }
}
