package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class MarketState implements MultiplayerState,SoloState{


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
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

    }


    @Override
    public void visitActivateProductionState(int productionIndex,  Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) {

    }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) {

    }

    @Override
    public void visitEndTurnState(Controller controller) {

    }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) {

    }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) {

    }

    @Override
    public void visitWhiteMarbleState(Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner,Controller controller) {

    }
}
