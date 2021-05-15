package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class StartTurnState implements MultiplayerState,SoloState {

    @Override
    public void visitStartTurnState(int move, Controller controller) {


        String currentPlayer = controller.getCurrentPlayer().getNickname();
        switch (move){
            case 0:
                controller.setCurrentState(controller.getMarketState());
                controller.getVirtualView().marketAction(currentPlayer);
                break;
            case 1:
                controller.setCurrentState(controller.getDevCardSaleState());
                controller.getVirtualView().devCardSaleAction(currentPlayer);
                break;
            case 2:
                controller.setCurrentState(controller.getActivateProductionState());
                controller.getVirtualView().productionAction(currentPlayer);
                break;
            default:
                controller.setCurrentState(controller.getLeaderActionState());
                controller.getVirtualView().leaderAction(currentPlayer);
                break;

        }



    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Resource, Map<Integer,Integer>> resToRemove, Controller controller) {

    }
}
