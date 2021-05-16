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
                //TODO TESTING
                try {
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.COIN, 100);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SHIELD, 100);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SERVANT, 100);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.STONE, 100);
                    controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.COIN, 1, 0);
                    controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SHIELD, 2, 1);
                    controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SERVANT, 3, 2);
                    controller.getCurrentPlayer().getBoard().addLeaderDepot(Resource.STONE);
                    controller.getCurrentPlayer().getBoard().addLeaderDepotResource(Resource.STONE, 2, 0);
                }catch(Exception e){
                    e.printStackTrace();
                }
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
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, Controller controller) {

    }
}
