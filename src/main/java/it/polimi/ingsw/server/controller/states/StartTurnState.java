package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class StartTurnState implements MultiplayerState,SoloState {

    @Override
    public void visitStartTurnState(int move, Controller controller) {


        String currentPlayer = controller.getCurrentPlayer().getNickname();

        //TODO CHEATING
        try {
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.COIN, 100);
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SHIELD, 200);
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SERVANT, 100);
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.STONE, 5000);
            if(controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(0) == null) {
                controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.COIN, 1, 0);
                controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SHIELD, 2, 1);
                controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SERVANT, 3, 2);
                controller.getCurrentPlayer().getBoard().addLeaderDepot(Resource.STONE);
                controller.getCurrentPlayer().getBoard().addLeaderDepotResource(Resource.STONE, 2, 0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        switch (move){
            case 0:
                controller.setCurrentState(controller.getMainActionState());
                controller.getVirtualView().mainAction(currentPlayer,null);
                break;
            case 1:
                controller.setCurrentState(controller.getLeaderActionState());
                controller.getVirtualView().leaderAction(currentPlayer);
                break;
        }



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

    }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }
}
