package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public interface State {
    void visitStartTurnState(int move, Controller controller);
    void visitDevCardSaleState(int row, int col, Map<Resource, Map<Integer,Integer>> resToRemove, Controller controller);

}
