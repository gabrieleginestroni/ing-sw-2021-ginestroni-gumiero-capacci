package it.polimi.ingsw.server.controller;

import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class used to handle the creation of Controller instances.
 */
public class ControllerFactory {

    /**
     * This method is used to request the creation of an instance of a Controller.
     * @param players List of the players that are going to play the game: if this list contains only 1 player the factory will
     *                instantiate a SoloController, otherwise a MultiplayerController. In every case the new controller will
     *                be referred as a generic Controller instance and not with its real dynamic type.
     * @param gameID The gameID of the game the controller is going to handle.
     * @return The reference to the new Controller.
     */
    public static Controller getController(List<Player> players, String gameID){
        if(players.size() == 1)
            return new SoloController(players.get(0),gameID);
        else
            return new MultiplayerController(players,gameID);
    }
}
