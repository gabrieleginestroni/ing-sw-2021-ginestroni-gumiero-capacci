package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;

import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.exceptions.*;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.games.Game;

import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.HashMap;
import java.util.Map;

public class DevCardSaleState implements MultiplayerState,SoloState {


    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {
        VirtualView virtualView = controller.getVirtualView();
        Player currentPlayer = controller.getCurrentPlayer();
        Game model = controller.getModel();
        DevelopmentCard card;
        String strError;
        try {
            try {
                card = model.getCardFromGrid(row, col);
            } catch (emptyDevCardGridSlotSelectedException e) {
                throw new invalidMoveException("The selected grid slot is empty");
            }

            Map<Resource,Integer> cost = card.getCost();
            boolean canPurchase = true;
            for(Map.Entry<Resource,Integer> entry:cost.entrySet())
                if(currentPlayer.getBoard().getResourceNumber(entry.getKey()) < entry.getValue()) {
                    strError = "Not enough "+ entry.getKey()+", "+currentPlayer.getBoard().getResourceNumber(entry.getKey())+" ("+entry.getValue()+" needed)";
                    throw new invalidMoveException(strError);
                }

            //check if can purchase the card and put it in the chosen cardSlot
            if(!currentPlayer.getBoard().canAddDevCard(cardSlot, card)) {

                strError = "Cannot place card inside slot "+cardSlot;
                throw new invalidMoveException(strError);

            }

            Map<Resource, Integer> tmpMap = new HashMap<>();  //map of total resource amount to remove
            for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
                for(Map.Entry<Resource,Integer> resourceEntry:entry.getValue().entrySet()) {
                    tmpMap.merge(resourceEntry.getKey(), resourceEntry.getValue(), Integer::sum);
                }
            }

            //Applying discounts
            for(Resource r : currentPlayer.getBoard().getDiscount()){
                tmpMap.merge(r, 1, Integer::sum);
            }


            //Check if total amount of each resource to remove is correct
            for(Map.Entry<Resource,Integer> entry:cost.entrySet())
                if(!tmpMap.get(entry.getKey()).equals(entry.getValue())) {
                    strError = "Incorrect number of " + entry.getKey() + " to remove, " + tmpMap.get(entry.getKey()) + " instead of " + entry.getValue();
                    throw new invalidMoveException(strError);
                }


            //check if each resource to remove is inside the depot chosen
            for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
                Resource resourceToRemove = entry.getValue().keySet().stream().findFirst().orElseGet(null);
                int quantityToRemove = entry.getValue().get(resourceToRemove);
                if(entry.getKey() < 3) {
                    //warehouse depots
                    Resource resourceInDepot = currentPlayer.getBoard().getWarehouseDepotResourceType(entry.getKey());
                    if(resourceInDepot != resourceToRemove) {
                        strError = "Error warehouse depot "+entry.getKey()+" of " + resourceInDepot + " not " + resourceToRemove;
                        throw new invalidMoveException(strError);
                    }else{
                        int quantityInDepot = currentPlayer.getBoard().getWarehouseDepotResourceNumber(entry.getKey());
                        if(quantityInDepot < quantityToRemove) {
                            strError = "Error quantity  " + quantityInDepot + " in warehouse depot "+entry.getKey()+", at least " + quantityToRemove;
                            throw new invalidMoveException(strError);
                        }
                    }
                }else if(entry.getKey() < 5){
                    //leader depots
                    Resource resourceInDepot;
                    try {
                        resourceInDepot = currentPlayer.getBoard().getLeaderDepotResourceType(entry.getKey() - 3);
                    } catch (IndexOutOfBoundsException e){
                        strError = "Leader depot does not exist yet";
                        throw new invalidMoveException(strError);
                    }
                    if(resourceInDepot != resourceToRemove) {
                        strError = "Error leader depot "+(entry.getKey()-3)+" of "+ resourceInDepot + " not " + resourceToRemove;
                        throw new invalidMoveException(strError);
                    }else{
                        int quantityInDepot = currentPlayer.getBoard().getLeaderDepotResourceNumber(entry.getKey()-3);
                        if(quantityInDepot < quantityToRemove) {
                            strError = "Error quantity  " + quantityInDepot + " in leader depot "+(entry.getKey()-3)+", at least " + quantityToRemove;
                            throw new invalidMoveException(strError);
                        }
                    }
                }else{
                    //strongbox
                    int quantityInStrongbox = currentPlayer.getBoard().getStrongBoxResource(resourceToRemove);
                    if(quantityInStrongbox < quantityToRemove){
                        strError = "Error quantity  " + quantityInStrongbox + " in strongbox, at least " + quantityToRemove;
                        throw new invalidMoveException(strError);
                    }
                }
            }


            try {
                for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
                    Resource resourceToRemove = entry.getValue().keySet().stream().findFirst().orElseGet(null);
                    int quantityToRemove = entry.getValue().get(resourceToRemove);
                    if(entry.getKey() < 3)
                        currentPlayer.getBoard().removeWarehouseDepotResource(resourceToRemove, quantityToRemove, entry.getKey());
                    else if(entry.getKey() < 5)
                        currentPlayer.getBoard().removeLeaderDepotResource(resourceToRemove, quantityToRemove, entry.getKey()-3);
                    else
                        currentPlayer.getBoard().removeStrongboxResource(resourceToRemove, quantityToRemove);
                }
                currentPlayer.getBoard().addDevelopmentCard(card, cardSlot);
                model.removeCardFromGrid(row, col);
            }  catch (invalidDevelopmentCardLevelPlacementException | invalidStrongBoxRemoveException | developmentCardSlotLimitExceededException | invalidResourceTypeException | removeResourceLimitExceededException | emptyDevCardGridSlotSelectedException e) {
                throw new invalidMoveException("Generic card purchase error");
            }
            //TODO toMiddleTurnState
            System.out.println("Card added with success in slot "+cardSlot);
            controller.setCurrentState(controller.getStartTurnState());


        }  catch(invalidMoveException e) {
            System.out.println(currentPlayer.getNickname() + " " + e.getErrorMessage());
            controller.setCurrentState(controller.getStartTurnState());
            virtualView.startTurn(currentPlayer.getNickname(),e.getErrorMessage());
        }
    }
}
