package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;

import java.util.Map;

public class SoloLeaderActionState extends LeaderActionState implements SoloState {
    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {
        try{
            super.commonVisit(actionMap, controller);
            if(controller.isGameOver()){
                controller.setCurrentState(controller.getEndGameState());
                controller.getEndGameState().visitEndGameState(controller.getCurrentPlayer().getNickname(), controller);
            }else{
                if (!controller.getMediator().isMainActionDone()) {
                    controller.setCurrentState(controller.getMainActionState());
                    controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), null);
                } else {
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }
            }
        }catch (invalidMoveException e) {
            System.out.println(controller.getCurrentPlayer().getNickname() + " " + e.getErrorMessage());
            controller.setCurrentState(controller.getMiddleTurnState());
            controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
        }
    }
}
