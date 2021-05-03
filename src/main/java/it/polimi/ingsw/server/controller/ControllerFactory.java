package it.polimi.ingsw.server.controller;

import java.util.List;

public class ControllerFactory {
    public static Controller getController(List<Player> players){
        if(players.size() == 1)
            return new SoloController(players.get(0));
        else
            return new MultiplayerController();
    }
}
