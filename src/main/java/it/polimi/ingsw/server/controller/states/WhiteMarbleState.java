package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.CommunicationMediator;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Resource;

import java.util.List;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player has performed a Market Action and the controller is now
 * checking what to do with all non-resource Marbles (Faith and White) obtained with the move.
 */
public class WhiteMarbleState implements MultiplayerState,SoloState {

    /**
     * This method is used to give all the obtained Faith points to the current player and, if the player has some
     * WhiteMarble power activated, transforms all the White Marbles: if the player has only one power activated simply
     * converts all the White Marbles in the right resource, otherwise if he has 2 powers of that kind activated
     * asks for each one of the White Marbles in which resource convert it. Finally, if the current player hasn't got any
     * White Marble power activated simply removes all White Marbles. At the end of this process the controller
     * switches his current state to the ResourceManagement state.
     * @param controller The controller that handles the current game.
     */
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
        if(faith != 0) {
            int activatedSection = controller.getCurrentPlayer().getBoard().giveFaithPoints(faith);
            if (activatedSection != -1)
                controller.getModel().vaticanReport(activatedSection);
        }
        if(controller.isGameOver())
            mediator.setPlayerWon();

        controller.setCurrentState(controller.getResourceManagementState());
        controller.getResourceManagementState().visitResourceManagementState(null, controller);
        /*
        String nextRes = null;
        if(!controller.getMediator().getMarketResources().isEmpty())
            nextRes = "(Next resource: "+ controller.getMediator().getMarketResources().entrySet().iterator().next().getKey()+")";
        controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(),nextRes);
         */
    }

    @Override
    public void visitStartTurnState(int move, Controller controller) { }

    @Override
    public void visitMainActionState(int move, Controller controller) { }

    @Override
    public void visitMiddleTurnState(int move, Controller controller) { }

    @Override
    public void visitEndTurnState(Controller controller) { }

    @Override
    public void visitEndGameState(String winner, Controller controller) { }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) { }

    @Override
    public void visitMarketState(int move, int index, Controller controller) { }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) { }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) { }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) { }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) { }
}
