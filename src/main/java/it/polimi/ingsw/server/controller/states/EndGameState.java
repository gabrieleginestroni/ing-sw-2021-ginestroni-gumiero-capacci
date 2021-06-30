package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.model.Resource;

import java.util.*;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which a Multiplayer Game ended.
 */
public class EndGameState implements MultiplayerState{

    /**
     * This method is used to show the final results of a Multiplayer Game and delete the
     * relative lobby from the server.
     * @param winner Winner's nickname.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitEndGameState(String winner,Controller controller) {
        Map<String, Integer> tmpMap = new HashMap<>();
        Map<String, Integer> playersVictoryPoints = new LinkedHashMap<>();
        tmpMap.put(controller.getCurrentPlayer().getNickname(), controller.getCurrentPlayer().getBoard().computeVictoryPoints());
        for(Player p: controller.othersPlayers())
            tmpMap.put(p.getNickname(), p.getBoard().computeVictoryPoints());
        tmpMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) .forEachOrdered(p -> playersVictoryPoints.put(p.getKey(), p.getValue()));
        controller.getVirtualView().showResult(playersVictoryPoints.entrySet().iterator().next().getKey(), playersVictoryPoints);

        Server.lobbies.remove(controller.getGameID());
    }

    @Override
    public void visitStartTurnState(int move, Controller controller) { }

    @Override
    public void visitMainActionState(int move, Controller controller) { }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) { }

    @Override
    public void visitMarketState(int move, int index, Controller controller) { }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) { }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) { }

    @Override
    public void visitEndTurnState(Controller controller) { }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) { }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) { }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) { }

    @Override
    public void visitWhiteMarbleState(Controller controller) { }
}
