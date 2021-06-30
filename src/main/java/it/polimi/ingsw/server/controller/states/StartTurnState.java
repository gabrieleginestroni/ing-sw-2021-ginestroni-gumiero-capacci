package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player's turn starts.
 */
public class StartTurnState implements MultiplayerState,SoloState {

    /**
     * This method is used to perform the right state transition on the base of the initial choice
     * of the player that, at the start of his turn, can choose between performing a Main Action or a Leader Action.
     * @param move The integer that represents the move requested by the player: 0 for Main Action, 1 for Leader Action.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitStartTurnState(int move, Controller controller) {
        String currentPlayer = controller.getCurrentPlayer().getNickname();

        /*
        //CHEATING
        System.out.println("start turn of " + controller.getCurrentPlayer().getNickname());
        int activatedSection = controller.getCurrentPlayer().getBoard().giveFaithPoints(10);
        if( activatedSection != -1)
            controller.getModel().vaticanReport(activatedSection);

        try {
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.COIN, 100);
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SHIELD, 200);
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.SERVANT, 100);
            controller.getCurrentPlayer().getBoard().addStrongboxResource(Resource.STONE, 5000);
            if(controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(0) == null
            && controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(1) == null
            && controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(2) == null) {
                controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.COIN, 1, 0);
                controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SHIELD, 2, 1);
                controller.getCurrentPlayer().getBoard().addWarehouseDepotResource(Resource.SERVANT, 3, 2);
                controller.getCurrentPlayer().getBoard().addLeaderDepot(Resource.STONE);
                controller.getCurrentPlayer().getBoard().addLeaderDepotResource(Resource.STONE, 2, 0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
         */

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

    @Override
    public void visitEndGameState(String winner, Controller controller) { }
}
