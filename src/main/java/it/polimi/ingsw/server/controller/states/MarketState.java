package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class MarketState implements MultiplayerState,SoloState {

    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

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
}
