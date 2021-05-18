package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;

import java.util.HashMap;
import java.util.Map;

public class SoloEndGameState extends EndGameState implements SoloState{

    @Override
    public void visitEndGameState(String winner, Controller controller) {
        Map<String, Integer> playersVictoryPoints = new HashMap<>();
        if(winner == null)
            winner = "Lorenzo il MAGNIFICO";
        playersVictoryPoints.put(controller.getCurrentPlayer().getNickname(), controller.getCurrentPlayer().getBoard().computeVictoryPoints());
        controller.getVirtualView().showResult(winner, playersVictoryPoints);
    }
}
