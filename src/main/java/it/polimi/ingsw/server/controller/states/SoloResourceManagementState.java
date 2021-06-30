package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the Solo version of the ResourceManagement state.
 */
public class SoloResourceManagementState extends ResourceManagementState implements SoloState {

    /**
     * This method is used in a Solo Game to perform the action chose by the current player and then the right state transition
     * on the base of the choices of the current player: the controller continues to cycle on the same
     * state until the current player asks to do a swap, in which case the controller switches to the Swap state.
     * After the Market Action end, if the current player has already done a Leader Action the controller automatically
     * terminates his turn, otherwise the controller switches to the MiddleTurn state.
     * In the case the action chose doesn't resolve positively it appears that the player hasn't done
     * a Main Action yet, so the controller switches to the StartTurn state if he has neither done a Leader Action,
     * otherwise switches to the MainAction state. Finally, if after the Market Action the game is
     * over, the controller switches to the EndGame state (both of current player and Lorenzo can win).
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitResourceManagementState(String errorMessage, Controller controller) {
        try{
            commonVisit(errorMessage,controller);
            int chosenDepot = controller.getMediator().getChosenDepot();
            if(chosenDepot == -2){ //swap depot
                controller.setCurrentState(controller.getSwapState());
                String nextRes = null;
                if(!controller.getMediator().getMarketResources().isEmpty())
                    nextRes = "(Picked resource: "+ controller.getMediator().getMarketResources().entrySet().iterator().next().getKey()+")";
                controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(),nextRes );
            }else {
                if (chosenDepot == -1) { //discard
                    int activatedSection = controller.getModel().addFaithLorenzo(1);
                    if (activatedSection != -1)
                        controller.getModel().vaticanReport(activatedSection);
                }
                if (controller.getMediator().isMarketStateEnded()) {
                    if (controller.isGameOver()) { //Lorenzo or player won
                        controller.setCurrentState(controller.getEndGameState());
                        if (controller.getMediator().hasPlayerWon()) //player won
                            controller.getEndGameState().visitEndGameState(controller.getCurrentPlayer().getNickname(), controller);
                        else //Lorenzo won
                            controller.getEndGameState().visitEndGameState(null, controller);
                    } else { //game not over
                        if (!controller.getMediator().isLeaderActionDone()) { //player can do a leader action
                            controller.setCurrentState(controller.getMiddleTurnState());
                            controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
                        } else { //player can't do a leader action
                            controller.setCurrentState(controller.getEndTurnState());
                            controller.getEndTurnState().visitEndTurnState(controller);
                        }
                    }
                } else { //market action not ended
                    controller.getMediator().setChosenDepot(-3);
                    controller.setCurrentState(controller.getResourceManagementState());
                    controller.getResourceManagementState().visitResourceManagementState(null, controller);
                }
            }
        } catch (invalidMoveException e) {
            controller.getResourceManagementState().visitResourceManagementState(e.getErrorMessage(),controller);
        }
    }
}
