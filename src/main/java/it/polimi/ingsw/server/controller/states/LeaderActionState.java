package it.polimi.ingsw.server.controller.states;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.Controller;

import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.CardRequirement;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.ResourceRequirement;

import java.util.List;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player chose to perform a Leader Action and now has to choose the action
 * to apply to every Leader Card in his HiddenHand.
 */
public class LeaderActionState implements MultiplayerState {

    /**
     * This method is used to perform the code in common between the multiplayer and the solo version of this state:
     * the controller checks for every action chose by the player if it is possible to apply it and, in case this
     * check resolves positively, applies that action to the model.
     * @param actionMap The map that contains the relative indexes of every Leader Card present in the HiddenHand of the current
     *                  player mapped to the integer that represents the action requested by the player for that card: 0 to do
     *                  nothing, 1 to activate and 2 to discard.
     * @param controller The controller that handles the current game.
     * @throws invalidMoveException Thrown when the current player requests any kind of unacceptable move: this exception
     *                              contains a error message string that will be shown to the current player in the
     *                              next state he will navigate to.
     */
    void commonVisit(Map<Integer, Integer> actionMap, Controller controller) throws invalidMoveException{
        Player currentPlayer = controller.getCurrentPlayer();
        Board board = currentPlayer.getBoard();

        if (actionMap.isEmpty())
            throw new invalidMoveException("No leader action available");

        //offset for shifting indexes
        int offset = 0;
        System.out.println(new Gson().toJson(actionMap));
        for (Map.Entry<Integer, Integer> entry : actionMap.entrySet()) {
            switch (entry.getValue()){
                case 0: break;
                case 1:
                    LeaderCard leader = board.getInactiveLeaderCard().get(entry.getKey() - offset);
                    List<ResourceRequirement> resMap = leader.getResourceRequirements();
                    List<CardRequirement> cardMap = leader.getCardRequirements();
                    for(ResourceRequirement resReq: resMap){
                        if(board.getResourceNumber(resReq.getResource()) < resReq.getQuantity())
                            throw new invalidMoveException("Insufficient resource to activate the leader card");
                    }
                    for(CardRequirement cardReq: cardMap){
                        if(board.getCardNumber(cardReq.getLevel(),cardReq.getColor()) < cardReq.getQuantity())
                            throw new invalidMoveException("Card requirements to activate the leader card not met ");
                    }
                    board.activateLeaderCard(entry.getKey() - offset);
                    if(entry.getKey() == 0)
                        offset++;
                    controller.getMediator().setLeaderActionDone();
                    break;
                case 2:
                    int activatedSection = board.discardLeaderCard(entry.getKey() - offset);
                    if(activatedSection != -1)
                        controller.getModel().vaticanReport(activatedSection);
                    if(entry.getKey() == 0)
                        offset++;
                    controller.getMediator().setLeaderActionDone();
                    break;
            }
        }
    }

    /**
     * This method is used in a Multiplayer Game to perform the specified Leader action and then the right state transition on
     * the base of the past choices of the current player: after the application of the Leader Action, if the current player has
     * already done a Main Action and also a Leader Action the controller automatically terminates his turn, otherwise if
     * he has not done a Main Action, the controller switches to the MainAction state. In the case the application of the
     * Leader Card doesn't resolve positively it appears that the player hasn't done a Leader Action yet, so the controller
     * switches to the MiddleTurn state.
     * @param actionMap The map that contains the relative indexes of every Leader Card present in the HiddenHand of the current
     *                  player mapped to the integer that represents the action requested by the player for that card: 0 to do
     *                  nothing, 1 to activate and 2 to discard.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) {
        try {
            commonVisit(actionMap, controller);
            if (!controller.getMediator().isMainActionDone()) {
                controller.setCurrentState(controller.getMainActionState());
                controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), null);
            } else {
                controller.setCurrentState(controller.getEndTurnState());
                controller.getEndTurnState().visitEndTurnState(controller);
            }
        } catch (invalidMoveException e) {
            System.out.println(controller.getCurrentPlayer().getNickname() + " " + e.getErrorMessage());
            controller.setCurrentState(controller.getMiddleTurnState());
            controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
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
    public void visitEndTurnState(Controller controller) { }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) { }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) { }

    @Override
    public void visitWhiteMarbleState(Controller controller) { }

    @Override
    public void visitEndGameState(String winner,Controller controller) { }
}