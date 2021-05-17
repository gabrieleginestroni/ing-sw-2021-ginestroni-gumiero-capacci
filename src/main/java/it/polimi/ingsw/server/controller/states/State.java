package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public interface State {
    void visitStartTurnState(int move, Controller controller);
    void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller);
    void visitMiddleTurnState(int move,Controller controller);
    void visitEndTurnState(Controller controller);
    void visitLeaderActionState(Map<Integer,Integer> actionMap,Controller controller);

}
