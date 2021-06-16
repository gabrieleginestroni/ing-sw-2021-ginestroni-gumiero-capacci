package it.polimi.ingsw.server.controller.states;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;

import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.exceptions.*;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.games.Game;

import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.HashMap;
import java.util.Map;

public class DevCardSaleState implements MultiplayerState {


    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller){
        try {
            commonVisit(row, col, resToRemove, cardSlot, controller);
            if (!controller.getMediator().isLeaderActionDone()) {
                System.out.println("Multiplayer turn end!");
                controller.setCurrentState(controller.getMiddleTurnState());
                controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
            } else {
                controller.setCurrentState(controller.getEndTurnState());
                controller.getEndTurnState().visitEndTurnState(controller);
            }
        } catch(invalidMoveException e) {
            System.out.println(controller.getCurrentPlayer().getNickname() + " " + e.getErrorMessage());
            if(controller.getMediator().isLeaderActionDone()) {
                controller.setCurrentState(controller.getMainActionState());
                controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), e.getErrorMessage()+"\nPlease do a main action");
            } else {
                controller.setCurrentState(controller.getStartTurnState());
                controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(),e.getErrorMessage());
            }
        }
    }

    @Override
    public void visitMarketState(int move, int index, Controller controller) {

    }

    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) {

    }

    void commonVisit(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) throws invalidMoveException{
        Player currentPlayer = controller.getCurrentPlayer();
        Game model = controller.getModel();
        Board board = currentPlayer.getBoard();
        DevelopmentCard card;
        String strError;

        System.out.println(cardSlot + ", " + row + ", " + col + ", " + (new Gson()).toJson(resToRemove));

        try {
            card = model.getCardFromGrid(row, col);
        } catch (emptyDevCardGridSlotSelectedException e) {
            throw new invalidMoveException("The selected grid slot is empty");
        }catch (ArrayIndexOutOfBoundsException e) {
            throw new invalidMoveException("");
        }

        Map<Resource,Integer> cost = card.getCost();
        for(Map.Entry<Resource,Integer> entry:cost.entrySet())
            if(board.getResourceNumber(entry.getKey()) < entry.getValue()) {
                strError = "Not enough "+ entry.getKey()+", "+board.getResourceNumber(entry.getKey())+" ("+entry.getValue()+" needed)";
                throw new invalidMoveException(strError);
            }

        //check if can purchase the card and put it in the chosen cardSlot
        if(!board.canAddDevCard(cardSlot, card)) {

            strError = "Cannot place card inside slot " + cardSlot;
            throw new invalidMoveException(strError);

        }

        Map<Resource, Integer> tmpMap = new HashMap<>();  //map of total resource amount to remove
        for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
            for(Map.Entry<Resource,Integer> resourceEntry:entry.getValue().entrySet()) {
                tmpMap.merge(resourceEntry.getKey(), resourceEntry.getValue(), Integer::sum);
            }
        }

        //Applying discounts
        for(Resource r : board.getDiscount()){
            tmpMap.merge(r, 1, Integer::sum);
        }

        //Check if total amount of each resource to remove is correct
        for(Map.Entry<Resource,Integer> entry:cost.entrySet()){
            if(tmpMap.get(entry.getKey()) == null){
                strError = "Incorrect number of " + entry.getKey() + " to remove, 0 instead of " + entry.getValue();
                throw new invalidMoveException(strError);
            }else{
                if(!tmpMap.get(entry.getKey()).equals(entry.getValue())) {
                    strError = "Incorrect number of " + entry.getKey() + " to remove, " + tmpMap.get(entry.getKey()) + " instead of " + entry.getValue();
                    throw new invalidMoveException(strError);
                }
            }
        }

        //Check if there are unrequested resources
        for(Map.Entry<Resource,Integer> entry: tmpMap.entrySet()) {
            if(cost.get(entry.getKey()) == null){
                strError = "Unrequested resources found : " + entry.getKey();
                throw new invalidMoveException(strError);
            }
        }

        //check if each resource to remove is inside the depot chosen
        for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
            //Resource resourceToRemove = entry.getValue().keySet().stream().findFirst().orElseGet(null);
            //int quantityToRemove = entry.getValue().get(resourceToRemove);
            for (Map.Entry<Resource, Integer> resEntry : entry.getValue().entrySet()){
                int quantityToRemove = resEntry.getValue();
                Resource resourceToRemove = resEntry.getKey();

                if (entry.getKey() < 3) {
                    //warehouse depots
                    Resource resourceInDepot = board.getWarehouseDepotResourceType(entry.getKey());
                    if (resourceInDepot != resourceToRemove) {
                        strError = "Error warehouse depot " + entry.getKey() + " of " + resourceInDepot + " not " + resourceToRemove;
                        throw new invalidMoveException(strError);
                    } else {
                        int quantityInDepot = board.getWarehouseDepotResourceNumber(entry.getKey());
                        if (quantityInDepot < quantityToRemove) {
                            strError = "Error quantity  " + quantityInDepot + " in warehouse depot " + entry.getKey() + ", at least " + quantityToRemove;
                            throw new invalidMoveException(strError);
                        }
                    }
                } else if (entry.getKey() < 5) {
                    //leader depots
                    Resource resourceInDepot;
                    try {
                        resourceInDepot = board.getLeaderDepotResourceType(entry.getKey() - 3);
                    } catch (IndexOutOfBoundsException e) {
                        strError = "Leader depot does not exist yet";
                        throw new invalidMoveException(strError);
                    }
                    if (resourceInDepot != resourceToRemove) {
                        strError = "Error leader depot " + (entry.getKey() - 3) + " of " + resourceInDepot + " not " + resourceToRemove;
                        throw new invalidMoveException(strError);
                    } else {
                        int quantityInDepot = board.getLeaderDepotResourceNumber(entry.getKey() - 3);
                        if (quantityInDepot < quantityToRemove) {
                            strError = "Error quantity  " + quantityInDepot + " in leader depot " + (entry.getKey() - 3) + ", at least " + quantityToRemove;
                            throw new invalidMoveException(strError);
                        }
                    }
                } else {
                    //strongbox
                    int quantityInStrongbox = board.getStrongBoxResource(resourceToRemove);
                    if (quantityInStrongbox < quantityToRemove) {
                        strError = "Error quantity  " + quantityInStrongbox + " in strongbox, at least " + quantityToRemove;
                        throw new invalidMoveException(strError);
                    }
                }
            }
        }


        try {
            for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
                //Resource resourceToRemove = entry.getValue().keySet().stream().findFirst().orElseGet(null);
                for(Map.Entry<Resource,Integer> resEntry:entry.getValue().entrySet()) {
                    int quantityToRemove = resEntry.getValue();
                    Resource resourceToRemove = resEntry.getKey();
                    if (entry.getKey() < 3)
                        board.removeWarehouseDepotResource(resourceToRemove, quantityToRemove, entry.getKey());
                    else if (entry.getKey() < 5)
                        board.removeLeaderDepotResource(resourceToRemove, quantityToRemove, entry.getKey() - 3);
                    else
                        board.removeStrongboxResource(resourceToRemove, quantityToRemove);
                }
            }

            board.addDevelopmentCard(card, cardSlot);
            model.removeCardFromGrid(row, col);
        }  catch (invalidDevelopmentCardLevelPlacementException | invalidStrongBoxRemoveException | developmentCardSlotLimitExceededException | invalidResourceTypeException | removeResourceLimitExceededException | emptyDevCardGridSlotSelectedException e) {
            throw new invalidMoveException("Generic card purchase error");
        }

        System.out.println("Card added with success in slot "+cardSlot);
        controller.getMediator().setMainActionDone();
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

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) {

    }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) {

    }

    @Override
    public void visitWhiteMarbleState(Controller controller) {

    }

    @Override
    public void visitEndGameState(String winner,Controller controller) {

    }
}
