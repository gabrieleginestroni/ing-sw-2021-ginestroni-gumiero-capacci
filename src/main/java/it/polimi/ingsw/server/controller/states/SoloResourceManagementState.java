package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;

public class SoloResourceManagementState extends ResourceManagementState implements SoloState {
    @Override
    public void visitResourceManagementState(String errorMessage, Controller controller) {
        try{
            commonVisit(errorMessage,controller);
            int chosenDepot = controller.getMediator().getChosenDepot();
            if(chosenDepot == -1){ //discard
                    int activatedSection = controller.getModel().addFaithLorenzo(1);
                    if(activatedSection != -1)
                        controller.getModel().vaticanReport(activatedSection);
            } //TODO
            if(controller.getMediator().isMarketStateEnded()) {
                if(controller.isGameOver()){ //Lorenzo or player won
                    controller.setCurrentState(controller.getEndGameState());
                    if(controller.getMediator().hasPlayerWon()) //player won
                        controller.getEndGameState().visitEndGameState(controller.getCurrentPlayer().getNickname(),controller);
                    else //Lorenzo won
                        controller.getEndGameState().visitEndGameState(null,controller);
                } else { //game not over
                    if(!controller.getMediator().isLeaderActionDone()){ //player can do a leader action
                        controller.setCurrentState(controller.getMiddleTurnState());
                        controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(),null);
                    } else { //player can't do a leader action
                        controller.setCurrentState(controller.getEndTurnState());
                        controller.getEndTurnState().visitEndTurnState(controller);
                    }
                }
            } else { //market action not ended
                controller.getMediator().setChosenDepot(-2);
                controller.setCurrentState(controller.getSwapState());
                controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(), null);
            }

        } catch (invalidMoveException e) {
            controller.getResourceManagementState().visitResourceManagementState(e.getErrorMessage(),controller);
        }

    }
}
