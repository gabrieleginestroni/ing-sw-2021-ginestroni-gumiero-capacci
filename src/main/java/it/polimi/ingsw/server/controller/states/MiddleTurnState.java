package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player has to choose between performing a Leader Action or
 * ending his turn.
 */
public class MiddleTurnState implements MultiplayerState,SoloState {

    /**
     * This method is used to perform the right state transition on the base of the choice of the current player.
     * @param move The integer that represents the choice of the current player: 0 for skipping the Leader Action
     *             and end the turn, 1 to perform the Leader Action.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitMiddleTurnState(int move, Controller controller) {
        switch (move){
            case 0:
                if(controller.getMediator().isMainActionDone()) {
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }else{
                    controller.setCurrentState(controller.getMainActionState());
                    controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), "Please do a main action");
                }
                break;
            default:
                controller.setCurrentState(controller.getLeaderActionState());
                controller.getVirtualView().leaderAction(controller.getCurrentPlayer().getNickname());
                break;
        }
    }

    @Override
    public void visitStartTurnState(int move, Controller controller) { }

    @Override
    public void visitMainActionState(int move, Controller controller) { }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) { }

    @Override
    public void visitMarketState(int move, int index, Controller controller) { }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) { }

    @Override
    public void visitEndTurnState(Controller controller) { }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) { }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) { }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) { }

    @Override
    public void visitWhiteMarbleState(Controller controller) { }

    @Override
    public void visitEndGameState(String winner, Controller controller) { }
}
