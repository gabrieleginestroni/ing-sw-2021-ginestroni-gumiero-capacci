package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.CommunicationMediator;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.exceptions.addResourceLimitExceededException;
import it.polimi.ingsw.server.exceptions.duplicatedWarehouseTypeException;
import it.polimi.ingsw.server.exceptions.invalidResourceTypeException;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;

import java.util.Map;

public class ResourceManagementState implements MultiplayerState {

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller){
        try{
            commonVisit(errorMessage,controller);
            int chosenDepot = controller.getMediator().getChosenDepot();
            if(chosenDepot == -1){ //discard
                controller.othersPlayers().stream().forEach(p -> {
                    int activatedSection = p.getBoard().giveFaithPoints(1);
                    if(activatedSection != -1)
                        controller.getModel().vaticanReport(activatedSection);
                });
            }
            if(controller.getMediator().isMarketStateEnded()) { //market action not ended
                if(!controller.getMediator().isLeaderActionDone()){ //player can do a leader action
                    controller.setCurrentState(controller.getMiddleTurnState());
                    controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(),null);
                } else { //player can't do a leader action
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }
            } else { //market action not ended
                controller.getMediator().setChosenDepot(-2);
                controller.setCurrentState(controller.getSwapState());
                controller.getVirtualView().proposeSwap(controller.getCurrentPlayer().getNickname(), null);
            }

        } catch (invalidMoveException e) {
            controller.getResourceManagementState().visitResourceManagementState(e.getErrorMessage(),controller);
        }

    }


    void commonVisit(String errorMessage, Controller controller) throws invalidMoveException {
        CommunicationMediator mediator = controller.getMediator();
        Map<Resource,Integer> resMap = mediator.getMarketResources();
        Board board = controller.getCurrentPlayer().getBoard();
        Resource res; //resource to propose to the player
        if(resMap.entrySet().iterator().hasNext()) {
            res = resMap.entrySet().iterator().next().getKey();
            int chosenDepot = controller.getVirtualView().proposeMarketResource(res,controller.getCurrentPlayer(),errorMessage);
            mediator.setChosenDepot(chosenDepot);
            if(chosenDepot >= 0 && chosenDepot <= 4) {
                if(chosenDepot <= 2){
                    Resource resDepot = controller.getCurrentPlayer().getBoard().getWarehouseDepotResourceType(chosenDepot);
                    if(resDepot == null || resDepot != res)
                        throw new invalidMoveException("Cannot place resource in the warehouse depot");
                    try {
                        board.addWarehouseDepotResource(res,1,chosenDepot);

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
                        throw new invalidMoveException("Cannot remove resource from a non-existing leader depot!");
                    }
                }
            }

            mediator.remove1Resource(res);
        } else {
            mediator.setMainActionDone();
            mediator.setMarketStateEnded();
        }
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
    public void visitSwapState(int dep1,int dep2,Controller controller) {

    }

    @Override
    public void visitWhiteMarbleState(Controller controller) {

    }
}
