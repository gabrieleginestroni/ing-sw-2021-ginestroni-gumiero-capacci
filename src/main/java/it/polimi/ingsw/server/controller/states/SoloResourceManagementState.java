package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;

public class SoloResourceManagementState extends ResourceManagementState implements SoloState {
    @Override
    public void visitResourceManagementState(Controller controller) {
        try{
            commonVisit(controller);
            int chosenDepot = controller.getMediator().getChosenDepot();
            if(chosenDepot == -1){ //discard
                    int activatedSection = controller.getModel().addFaithLorenzo(1);
                    if(activatedSection != -1)
                        controller.getModel().vaticanReport(activatedSection);
            } //TODO
            if(controller.getMediator().isMarketStateEnded()) {
                if(controller.isGameOver()){ //Lorenzo wins
                    controller.setCurrentState(controller.getEndGameState());
                    controller.getEndTurnState().visitEndGameState(null,controller);
                } else {
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }
            } else {
                controller.getMediator().setChosenDepot(-2);
                controller.setCurrentState(controller.getSwapState());
                controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(), null);
            }

        } catch (invalidMoveException e) {
            controller.getMediator().setPreviousErrorMessage(e.getErrorMessage());
            controller.getResourceManagementState().visitResourceManagementState(controller);
        }

    }
}
