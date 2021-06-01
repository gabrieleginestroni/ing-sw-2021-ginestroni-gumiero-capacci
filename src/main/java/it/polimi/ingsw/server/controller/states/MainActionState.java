package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class MainActionState implements SoloState,MultiplayerState {
    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {
        String currentPlayer = controller.getCurrentPlayer().getNickname();
        switch (move){
            case 0:
                controller.setCurrentState(controller.getMarketState());
                controller.getVirtualView().marketAction(currentPlayer,null);
                break;
            case 1:
                ///*
                //TODO TESTING
                try {
                    controller.getCurrentPlayer().getBoard().giveFaithPoints(23);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.COIN, 30);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SHIELD, 20);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SERVANT, 10);
                    controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.STONE, 50);
                    /*
                    if(controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(0) == null) {
                        controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.COIN, 1, 0);
                        controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SHIELD, 2, 1);
                        controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SERVANT, 3, 2);
                        controller.getCurrentPlayer().getBoard().addLeaderDepot(Resource.STONE);
                        controller.getCurrentPlayer().getBoard().addLeaderDepotResource(Resource.STONE, 2, 0);
                    }
                    */
                }catch(Exception e){
                    e.printStackTrace();
                }
                //*/
                controller.setCurrentState(controller.getDevCardSaleState());
                controller.getVirtualView().devCardSaleAction(currentPlayer);
                break;
            case 2:
                controller.setCurrentState(controller.getActivateProductionState());
                controller.getVirtualView().productionAction(currentPlayer, null);
                break;

        }
    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

    }

    @Override
    public void visitMarketState(int move, int index, Controller controller) {

    }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) {

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

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) {

    }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) {

    }

    @Override
    public void visitWhiteMarbleState(Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner,Controller controller) {

    }
}
