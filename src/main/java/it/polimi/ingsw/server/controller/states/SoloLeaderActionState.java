package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the Solo version of the LeaderAction state.
 */
public class SoloLeaderActionState extends LeaderActionState implements SoloState {

    /**
     * This method is used in a Solo Game to perform the Leader action and then the right state transition on the base
     * of the past choices of the current player: after the application of the Leader Action, if the current player has
     * already done a Main Action and also a Leader Action the controller automatically terminates his turn, otherwise if
     * he has not done a Main Action, the controller switches to the MainAction state. In the case the application of the
     * Leader Card doesn't resolve positively it appears that the player hasn't done a Leader Action yet, so the controller
     * switches to the MiddleTurn state. Finally, if after the application of the Leader Action the game is over,
     * the controller switches to the EndGame state (current player won).
     * @param actionMap The map that contains the relative indexes of every Leader Card present in the HiddenHand of the current
     *                  player mapped to the integer that represents the action requested by the player for that card: 0 to do
     *                  nothing, 1 to activate and 2 to discard.
     * @param controller The controller that handles the current game.
     */
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
