package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class SoloDevCardSaleState extends DevCardSaleState implements SoloState {

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {
        try {
            super.commonVisit(row, col, resToRemove, cardSlot, controller);
            //TODO change next state
            if (!controller.getMediator().isLeaderActionDone()) {
                System.out.println("Solo turn end!");
                controller.setCurrentState(controller.getMiddleTurnState());
                controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
            } else {
                controller.setCurrentState(controller.getEndTurnState());
                controller.getEndTurnState().visitEndTurnState(controller);
            }
        } catch(invalidMoveException e) {
            System.out.println(controller.getCurrentPlayer().getNickname() + " " + e.getErrorMessage());
            controller.setCurrentState(controller.getMainActionState());
            controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
        }
    }
}
