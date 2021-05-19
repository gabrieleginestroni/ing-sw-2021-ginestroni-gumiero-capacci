package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class EndTurnState implements MultiplayerState {
    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

    }

    @Override
    public void visitMarketState(int move, int index, Controller controller) {

    }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap,  Controller controller) {

    }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) {

    }

    @Override
    public void visitEndTurnState(Controller controller) {
        if(controller.isGameOver() && controller.isRoundOver()){
            controller.setCurrentState(controller.getEndGameState());
            controller.getEndGameState().visitEndGameState(null, controller);
        }else{
            String prevPlayer = controller.getCurrentPlayer().getNickname();
            controller.nextPlayer();
            controller.setCurrentState(controller.getStartTurnState());
            controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(), prevPlayer+" turn ended");
            controller.getMediator().refresh();
        }
    }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner,Controller controller) {

    }
}
