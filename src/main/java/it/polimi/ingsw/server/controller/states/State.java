package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * This interface represents the behaviour of the states of a generic Controller.
 */
public interface State {

    void visitStartTurnState(int move, Controller controller);
    void visitMainActionState(int move, Controller controller);
    void visitMiddleTurnState(int move,Controller controller);
    void visitEndTurnState(Controller controller);
    void visitEndGameState(String winner, Controller controller);

    void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller);
    void visitMarketState(int move, int index, Controller controller);
    void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller);
    void visitLeaderActionState(Map<Integer,Integer> actionMap,Controller controller);
    void visitResourceManagementState(String errorMessage, Controller controller);
    void visitSwapState(int dep1,int dep2, Controller controller);
    void visitWhiteMarbleState(Controller controller);

}
