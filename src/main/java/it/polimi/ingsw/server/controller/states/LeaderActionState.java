package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.MultiplayerController;
import it.polimi.ingsw.server.controller.SoloController;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class LeaderActionState implements MultiplayerState,SoloState {

    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Resource, Map<Integer,Integer>> resToRemove, Controller controller) {

    }
}
