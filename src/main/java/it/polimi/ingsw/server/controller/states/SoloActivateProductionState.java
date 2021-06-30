package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the Solo version of the ActivateProduction state.
 */
public class SoloActivateProductionState extends ActivateProductionState implements SoloState{

    /**
     * This method is used in a Solo Game to perform the activation of the specified production and then the right state transition
     * on the base of the past choices of the current player : the controller continues to cycle on the SoloActivationProduction
     * state until the current player asks to terminate this process and, after that, if the current player has already done
     * a Leader Action the controller automatically terminates his turn, otherwise the controller switches to the MiddleTurn state.
     * In the case the activation of the productions doesn't resolve positively it appears that the player hasn't done
     * a Main Action yet, so the controller switches to the StartTurn state if he has neither done a Leader Action,
     * otherwise switches to the MainAction state. Finally, if after the activation of the productions the game is
     * over, the controller switches to the EndGame state (player won).
     * @param productionIndex The integer that represents the choice of the player: 0 - 2 for the activation of the
     *                        production of the DevelopmentCard placed at the top of the relative CardSlot in the board
     *                        of the current player, 3 - 4 for the production power of the relative active Leader Cards,
     *                        5 for the base production of every board and 6 to receive outputs. Every chosen index is
     *                        saved in a data structure in the CommunicationMediator: in this way every time this visit
     *                        is called it checks if the specified productionIndex has been already activated, in order
     *                        not to allow multiple activations of the same production in the same turn.
     * @param wareHouseMap The map that contains the indexes of the Warehouse or Leader Depots the current player
     *                     chose to get resources from, mapped to the number of resources to remove: 0 - 2 for
     *                     Warehouse Depots, 3 - 4 for Leader Depots.
     * @param strongBoxMap The map that contains the resources the current player chose to get from the Strongbox,
     *                     each one mapped to the quantity of that specific resource to remove.
     * @param chosenResource This parameter is nullable because it is only used for those production that produce as
     *                       output a resource at player's choice (Board and Leader productions): in case it is not
     *                       null it can only be COIN, SERVANT, SHIELD or STONE, not FAITH.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller){
        try {
            commonVisit(productionIndex, wareHouseMap, strongBoxMap, chosenResource, controller);
            if(productionIndex != 6) {
                controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), null);
            }else {
                if (!controller.getMediator().isMainActionDone()) {
                    if(controller.getMediator().isLeaderActionDone()) {
                        controller.setCurrentState(controller.getMainActionState());
                        controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), "Please do a main action");
                    } else {
                        controller.setCurrentState(controller.getStartTurnState());
                        controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(),null);
                    }
                } else {
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
            }
        } catch (invalidMoveException e) {
            controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), e.getErrorMessage());
        }
    }
}
