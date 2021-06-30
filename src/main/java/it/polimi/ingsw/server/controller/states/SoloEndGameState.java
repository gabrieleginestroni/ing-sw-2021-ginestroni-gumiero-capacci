package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the Solo version of the EndGame state.
 */
public class SoloEndGameState extends EndGameState implements SoloState{

    /**
     * This method is used to show the final results of a Solo Game and delete the
     * relative lobby from the server.
     * @param winner Winner's nickname: if it is null it means that Lorenzo won the game, otherwise it contains the
     *               nickname of the only player in the specific Solo Game.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitEndGameState(String winner, Controller controller) {
        Map<String, Integer> playersVictoryPoints = new HashMap<>();
        if(winner == null)
            winner = "Lorenzo";
        playersVictoryPoints.put(controller.getCurrentPlayer().getNickname(), controller.getCurrentPlayer().getBoard().computeVictoryPoints());
        controller.getVirtualView().showResult(winner, playersVictoryPoints);

        Server.lobbies.remove(controller.getGameID());
    }
}
