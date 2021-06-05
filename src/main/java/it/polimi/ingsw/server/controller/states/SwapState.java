package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.exceptions.invalidSwapException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class SwapState implements MultiplayerState,SoloState {

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) {
        try {
            if(dep1 != -1) {
                try {
                    if(dep1 > 2 || dep2 > 2)
                        throw new invalidMoveException("Invalid depot");
                    if(dep1 == dep2)
                        throw new invalidMoveException("Cannot swap same depot");
                    controller.getCurrentPlayer().getBoard().swapDepot(dep1, dep2);
                    String nextRes = null;
                    if(!controller.getMediator().getMarketResources().isEmpty())
                        nextRes = "(Picked resource: "+ controller.getMediator().getMarketResources().entrySet().iterator().next().getKey()+")";
                    controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(),nextRes);
                } catch (invalidSwapException e) {
                    throw new invalidMoveException("Cannot swap warehouse depot " + dep1 + " with warehouse depot " + dep2);
                }
            } else {
                controller.setCurrentState(controller.getResourceManagementState());
                controller.getResourceManagementState().visitResourceManagementState(null,controller);
            }
        } catch(invalidMoveException e) {
            controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
        }
    }
    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {

    }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) {

    }

    @Override
    public void visitEndTurnState(Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner, Controller controller) {

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
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) {

    }



    @Override
    public void visitWhiteMarbleState(Controller controller) {

    }
}
