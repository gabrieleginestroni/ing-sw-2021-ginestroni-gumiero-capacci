package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player chose to perform a Main Action and now has to choose which
 * Main Action perform (Market, Development Card Purchase, Activate Production).
 */
public class MainActionState implements SoloState,MultiplayerState {

    /**
     * This method is used to perform the right state transition on the base of the choice of the current player.
     * @param move The integer that represent the action requested by the player: 0 for Market Action,
     *             1 for DevCardSale Action and 2 for ActivateProduction Action.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitMainActionState(int move, Controller controller) {
        String currentPlayer = controller.getCurrentPlayer().getNickname();
        switch (move){
            case 0:
                controller.setCurrentState(controller.getMarketState());
                controller.getVirtualView().marketAction(currentPlayer,null);
                break;
            case 1:
                controller.setCurrentState(controller.getDevCardSaleState());
                controller.getVirtualView().devCardSaleAction(currentPlayer);
                break;
            case 2:
                controller.setCurrentState(controller.getActivateProductionState());
                controller.getVirtualView().productionAction(currentPlayer, null);
                break;
        }
    }

    @Override
    public void visitStartTurnState(int move, Controller controller) { }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) { }

    @Override
    public void visitMarketState(int move, int index, Controller controller) { }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) { }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) { }

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
    public void visitEndGameState(String winner,Controller controller) { }
}
