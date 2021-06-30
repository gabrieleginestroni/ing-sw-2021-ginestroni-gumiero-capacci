package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player chose to perform a Market Action and
 * now has to choose the move to execute.
 */
public class MarketState implements MultiplayerState,SoloState{

    /**
     * This method is used to perform the move requested by the current player on the Market, save the obtained
     * resources on a data structure in the CommunicationMediator and switch the current state to the WhiteMarble state.
     * @param move The integer that represents the type of the move requested by the current player: 0 for horizontal,
     *             1 for vertical.
     * @param index The index of the row or column involved by the move requested by the current player.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitMarketState(int move, int index, Controller controller) {
      try {
          Map<Resource,Integer> marketMap;
          if (move != 0 && move != 1)
              throw new invalidMoveException("Invalid move");
          if (move == 0) {//horizontal
              if (index >= 0 && index <= 2) {
                  marketMap = controller.getModel().doHorizontalMoveMarket(index);
                  controller.getMediator().setMarketResources(marketMap);

              } else
                  throw new invalidMoveException("Incorrect index for an horizontal move");

          }
          if (move == 1) { //vertical
              if (index >= 0 && index <= 3) {
                  marketMap = controller.getModel().doVerticalMoveMarket(index);
                  controller.getMediator().setMarketResources(marketMap);
              } else
                  throw new invalidMoveException("Incorrect index for a vertical move");
          }
          controller.setCurrentState(controller.getWhiteMarbleState());
          controller.getWhiteMarbleState().visitWhiteMarbleState(controller);

      } catch (invalidMoveException e) {
          controller.getVirtualView().marketAction(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
      }
    }

    @Override
    public void visitStartTurnState(int move, Controller controller) { }

    @Override
    public void visitMainActionState(int move, Controller controller) { }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) { }

    @Override
    public void visitActivateProductionState(int productionIndex,  Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) { }

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
