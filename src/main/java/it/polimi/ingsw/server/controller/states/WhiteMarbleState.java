package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.CommunicationMediator;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.List;
import java.util.Map;

public class WhiteMarbleState implements MultiplayerState,SoloState {
    @Override
    public void visitWhiteMarbleState(Controller controller) {
        List<Resource> whiteMarbles = controller.getCurrentPlayer().getBoard().getWhiteMarbles();
        CommunicationMediator mediator = controller.getMediator();
        if(whiteMarbles.isEmpty())
            mediator.discardWhiteMarbles();
        if(whiteMarbles.size() == 1){
            Resource res = whiteMarbles.get(0);
            mediator.substituteAllWhiteMarble(res);
        }
        if(whiteMarbles.size() == 2) {
            int whiteMarbleNumber = mediator.getMarketResources().getOrDefault(Resource.WHITE,0);
            for(int i = 0; i < whiteMarbleNumber; i++){
                Resource chosenRes = controller.getVirtualView().proposeWhiteMarble(whiteMarbles.get(0),whiteMarbles.get(1),controller.getCurrentPlayer());
                mediator.substitute1WhiteMarble(chosenRes);
            }
        }
        //handling faith resource
        int faith = mediator.removeFaith();
        int activatedSection = controller.getCurrentPlayer().getBoard().giveFaithPoints(faith);
        controller.getModel().vaticanReport(activatedSection);
        if(controller.isGameOver())
            mediator.setPlayerWon();
        controller.setCurrentState(controller.getSwapState());
        controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(),null);
    }

    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {

    }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) {

    }

    @Override
    public void visitEndTurnState(Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

    }

    @Override
    public void visitMarketState(int move, int index, Controller controller) {

    }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) {

    }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {

    }

    @Override
    public void visitResourceManagementState(Controller controller) {

    }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) {

    }


}
