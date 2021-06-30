package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player's turn ends.
 */
public class EndTurnState implements MultiplayerState {

    /**
     * This method is used in a Multiplayer Game to perform the right state transition on the base of the actual state of
     * the current game: if the game is over and the current player is also the last in the round the controller switches
     * to the End Game state, otherwise the turn is passed to the next player (which is the first in the round).
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitEndTurnState(Controller controller) {
        if(controller.isGameOver() && controller.isRoundOver()){
            controller.setCurrentState(controller.getEndGameState());
            controller.getEndGameState().visitEndGameState(null, controller);
        }else{
            String prevPlayer = controller.getCurrentPlayer().getNickname();
            controller.nextPlayer();
            controller.setCurrentState(controller.getStartTurnState());
            controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(), prevPlayer+" turn ended");
            controller.getMediator().refresh();
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
    public void visitMiddleTurnState(int move, Controller controller) { }

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
