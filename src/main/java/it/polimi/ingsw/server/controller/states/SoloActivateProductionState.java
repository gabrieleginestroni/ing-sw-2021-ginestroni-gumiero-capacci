package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class SoloActivateProductionState extends ActivateProductionState implements SoloState{
    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller){
        try {
            commonVisit(productionIndex, wareHouseMap, strongBoxMap, chosenResource, controller);
            if(productionIndex != 6) {
                controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), null);
            }else {
                if (controller.isGameOver()) {
                    controller.setCurrentState(controller.getEndGameState());
                    controller.getEndGameState().visitEndGameState(controller.getCurrentPlayer().getNickname(), controller);
                } else {
                    if (!controller.getMediator().isLeaderActionDone()) {
                        controller.setCurrentState(controller.getMiddleTurnState());
                        controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
                    } else {
                        controller.setCurrentState(controller.getEndTurnState());
                        controller.getEndTurnState().visitEndTurnState(controller);
                    }
                }
            }
        } catch (invalidMoveException e) {
            controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), e.getErrorMessage());
        }
    }
}
