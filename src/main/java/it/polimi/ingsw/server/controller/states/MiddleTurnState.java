package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class MiddleTurnState implements MultiplayerState,SoloState {
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
        switch (move){
            case 0:
                if(controller.getMediator().isMainActionDone()) {
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }else{
                    controller.setCurrentState(controller.getMainActionState());
                    controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), "Please do a main action");
                }
                break;
            default:
                controller.setCurrentState(controller.getLeaderActionState());
                controller.getVirtualView().leaderAction(controller.getCurrentPlayer().getNickname());
                break;
        }
    }

    @Override
    public void visitEndTurnState(Controller controller) {

    }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner, Controller controller) {

    }
}
