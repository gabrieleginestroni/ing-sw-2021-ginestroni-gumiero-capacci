package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.CommunicationMediator;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.exceptions.addResourceLimitExceededException;
import it.polimi.ingsw.server.exceptions.duplicatedWarehouseTypeException;
import it.polimi.ingsw.server.exceptions.invalidResourceTypeException;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player performed a Market Action and now has to choose what to do
 * with each one of the obtained placeable resources.
 */
public class ResourceManagementState implements MultiplayerState {

    /**
     * This method is used to perform the code in common between the multiplayer and the solo version of this state:
     * the controller checks the action chose by the player if it is possible to apply it and, in case this
     * check resolves positively, applies that action to the model (only performs the placement of a single obtained
     * resource).
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     * @param controller The controller that handles the current game.
     * @throws invalidMoveException Thrown when the current player requests any kind of unacceptable move: this exception
     *                              contains a error message string that will be shown to the current player in the
     *                              next state he will navigate to.
     */
    void commonVisit(String errorMessage, Controller controller) throws invalidMoveException {
        CommunicationMediator mediator = controller.getMediator();
        Map<Resource,Integer> resMap = mediator.getMarketResources();
        Board board = controller.getCurrentPlayer().getBoard();
        Resource res; //resource to propose to the player
        if(!resMap.isEmpty()) {
            res = resMap.entrySet().iterator().next().getKey();
            int chosenDepot = controller.getVirtualView().proposeMarketResource(res,controller.getCurrentPlayer(),errorMessage);
            mediator.setChosenDepot(chosenDepot);
            if(chosenDepot > -2) {
                if (chosenDepot >= 0 && chosenDepot <= 4) {
                    if (chosenDepot <= 2) {
                        Resource resDepot = controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(chosenDepot);
                        if (resDepot != null && resDepot != res)
                            throw new invalidMoveException("Cannot place resource " + res + " in warehouse depot containing " + resDepot);
                        try {
                            board.addWarehouseDepotResource(res, 1, chosenDepot);

                        } catch (addResourceLimitExceededException | duplicatedWarehouseTypeException | invalidResourceTypeException e) {
                            throw new invalidMoveException("Invalid resource warehouse placement");
                        }

                    }
                    if (chosenDepot == 3 || chosenDepot == 4) {
                        try {
                            Resource resDepot = controller.getCurrentPlayer().getBoard().getLeaderDepotResourceType(chosenDepot - 3);
                            if (resDepot != res)
                                throw new invalidMoveException("Wrong depot resource type, trying to place " + res + " into a " + resDepot + " depot");
                            try {
                                board.addLeaderDepotResource(res, 1, chosenDepot - 3);

                            } catch (addResourceLimitExceededException | invalidResourceTypeException e) {
                                throw new invalidMoveException("Invalid resource leader placement");
                            }
                        } catch (IndexOutOfBoundsException e) {
                            throw new invalidMoveException("Cannot place resource into a non-existing leader depot!");
                        }
                    }
                }
                mediator.remove1Resource(res);
            }
        } else {
            mediator.setMainActionDone();
            mediator.setMarketStateEnded();
        }
    }

    /**
     * This method is used in a Multiplayer Game to perform the action chose by the current player and then the right state transition
     * on the base of the choices of the current player: the controller continues to cycle on the same
     * state until the current player asks to do a swap, in which case the controller switches to the Swap state.
     * After the Market Action end, if the current player has already done a Leader Action the controller automatically
     * terminates his turn, otherwise the controller switches to the MiddleTurn state.
     * In the case the action chose doesn't resolve positively it appears that the player hasn't done
     * a Main Action yet, so the controller switches to the StartTurn state if he has neither done a Leader Action,
     * otherwise switches to the MainAction state.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller){
        try{
            commonVisit(errorMessage,controller);
            int chosenDepot = controller.getMediator().getChosenDepot();
            if(chosenDepot == -2){ //swap depot
                controller.setCurrentState(controller.getSwapState());
                String nextRes = null;
                if(!controller.getMediator().getMarketResources().isEmpty())
                    nextRes = "(Picked resource: "+ controller.getMediator().getMarketResources().entrySet().iterator().next().getKey()+")";
                controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(),nextRes );
            }else {
                if (chosenDepot == -1) { //discard
                    controller.othersPlayers().stream().forEach(p -> {
                        int activatedSection = p.getBoard().giveFaithPoints(1);
                        if (activatedSection != -1)
                            controller.getModel().vaticanReport(activatedSection);
                    });
                }
                if (controller.getMediator().isMarketStateEnded()) { //market action ended
                    if (!controller.getMediator().isLeaderActionDone()) { //player can do a leader action
                        controller.setCurrentState(controller.getMiddleTurnState());
                        controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
                    } else { //player can't do a leader action
                        controller.setCurrentState(controller.getEndTurnState());
                        controller.getEndTurnState().visitEndTurnState(controller);
                    }
                } else {//market action not ended
                    controller.getMediator().setChosenDepot(-3);
                    controller.setCurrentState(controller.getResourceManagementState());
                    controller.getResourceManagementState().visitResourceManagementState(null, controller);
                }
            }
        } catch (invalidMoveException e) {
            controller.getResourceManagementState().visitResourceManagementState(e.getErrorMessage(),controller);
        }
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
    public void visitSwapState(int dep1,int dep2,Controller controller) { }

    @Override
    public void visitWhiteMarbleState(Controller controller) { }
}
