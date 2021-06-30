package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the Solo version of the DevCardSale state.
 */
public class SoloDevCardSaleState extends DevCardSaleState implements SoloState {

    /**
     * This method is used in a Solo Game to perform the Development Card purchase and then the right state transition on
     * the base of the past choices of the current player: after the purchase of the Development Card, if the current player has
     * already done a Leader Action the controller automatically terminates his turn, otherwise the controller switches to the
     * MiddleTurn state. In the case the purchase doesn't resolve positively it appears that the player hasn't done
     * a Main Action yet, so the controller switches to the StartTurn state if he has neither done a Leader Action,
     * otherwise switches to the MainAction state. Finally, if after the purchase the game is over the controller
     * switches to the EndGame state (both of current player and Lorenzo can win).
     * @param row The row index of the card the current player requested to buy.
     * @param col The column index of the card the current player requested to buy.
     * @param resToRemove A double-map that contains the indexes of the depots the player chose to get resources from
     *                    (0 - 2 for warehouse depots, 3 - 4 for leader depots and 5 for strongbox) mapped to an inner
     *                    map that contains the type of the resource to remove mapped to the quantity to remove.
     * @param cardSlot The index of the CardSlot in the current player's board where he wants to place the purchased card.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {
        try {
            super.commonVisit(row, col, resToRemove, cardSlot, controller);
            if(controller.isGameOver()){
                if(controller.getModel().isColumnEmpty(col)){
                    controller.setCurrentState(controller.getEndGameState());
                    controller.getEndGameState().visitEndGameState(null, controller);
                }else{
                    controller.setCurrentState(controller.getEndGameState());
                    controller.getEndGameState().visitEndGameState(controller.getCurrentPlayer().getNickname(), controller);
                }
            }else{
                if (!controller.getMediator().isLeaderActionDone()) {
                    controller.setCurrentState(controller.getMiddleTurnState());
                    controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
                } else {
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }
            }
        } catch(invalidMoveException e) {
            System.out.println(controller.getCurrentPlayer().getNickname() + " " + e.getErrorMessage());
            if(controller.getMediator().isLeaderActionDone()) {
                controller.setCurrentState(controller.getMainActionState());
                controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), e.getErrorMessage()+"\nPlease do a main action");
            } else {
                controller.setCurrentState(controller.getStartTurnState());
                controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
            }
        }
    }
}
