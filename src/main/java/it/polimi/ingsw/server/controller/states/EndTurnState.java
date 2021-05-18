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
    public void visitMiddleTurnState(int move, Controller controller) {

    }

    @Override
    public void visitEndTurnState(Controller controller) {
        if(controller.isGameOver() && controller.isRoundOver()){
            controller.setCurrentState(controller.getEndGameState());
            controller.getEndGameState().visitEndGameState(null, controller);
        }else{
            controller.nextPlayer();
            controller.setCurrentState(controller.getStartTurnState());
            System.out.println("OK");
            controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(), null);
        }
    }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner,Controller controller) {

    }
}
