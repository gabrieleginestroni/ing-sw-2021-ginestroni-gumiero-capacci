package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;

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
        try {
            card = model.getCardFromGrid(row, col);

            Map<Resource,Integer> cost = card.getCost();
            boolean canPurchase = true;
            for(Map.Entry<Resource,Integer> entry:cost.entrySet())
                if(currentPlayer.getBoard().getResourceNumber(entry.getKey()) < entry.getValue())
                    canPurchase = false;

            //check if can purchase the card and put it in the chosen cardSlot
            if(!canPurchase || !currentPlayer.getBoard().canAddDevCard(cardSlot, card)) {
                //TODO send undo message to client
                System.out.println("Cannot purchase card");
                controller.setCurrentState(controller.getStartTurnState());
                virtualView.startTurn(currentPlayer.getNickname());
            } else {
                Map<Resource, Integer> tmpMap = new HashMap<>();  //map of total resource amount to remove
                for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
                    for(Map.Entry<Resource,Integer> resourceEntry:entry.getValue().entrySet()) {
                        if(tmpMap.get(resourceEntry.getKey()) != null)
                            tmpMap.put(resourceEntry.getKey(), tmpMap.get(resourceEntry.getKey())+resourceEntry.getValue());
                        else
                            tmpMap.put(resourceEntry.getKey(), resourceEntry.getValue());
                    }
                }

                //Applying discounts
                for(Resource r : currentPlayer.getBoard().getDiscount()){
                    if(tmpMap.get(r) != null)
                        tmpMap.put(r, tmpMap.get(r)+1);
                    else
                        tmpMap.put(r, 1);
                }

                boolean error = false;

                //Check if total amount of each resource to remove is correct
                for(Map.Entry<Resource,Integer> entry:cost.entrySet()){
                    if(tmpMap.get(entry.getKey()) != entry.getValue()) {
                        System.out.println("Incorrect number of " + entry.getKey() + " to remove, " + tmpMap.get(entry.getKey()) + "instead of " + entry.getValue());
                        error = true;
                        break;
                    }
                }

                //check if each resource to remove is inside the depot chosen
                for(Map.Entry<Integer, Map<Resource,Integer>> entry:resToRemove.entrySet()) {
                    Resource resourceToRemove = entry.getValue().keySet().stream().findFirst().orElseGet(null);
                    int quantityToRemove = entry.getValue().get(resourceToRemove);
                    if(entry.getKey() < 3) {
                        //warehouse depots
                        Resource resourceInDepot = currentPlayer.getBoard().getWarehouseDepotResourceType(entry.getKey());
                        if(resourceInDepot != resourceToRemove) {
                            System.out.println("Error warehouse depot "+entry.getKey()+" of " + resourceInDepot + " not " + resourceToRemove);
                            error = true;
                            break;
                        }else{
                            int quantityInDepot = currentPlayer.getBoard().getWarehouseDepotResourceNumber(entry.getKey());
                            if(quantityInDepot < quantityToRemove) {
                                System.out.println("Error quantity  " + quantityInDepot + " in warehouse depot "+entry.getKey()+", at least " + quantityToRemove);
                                error = true;
                                break;
                            }
                        }
                    }else if(entry.getKey() < 5){
                        //leader depots
                        Resource resourceInDepot = currentPlayer.getBoard().getLeaderDepotResourceType(entry.getKey()-3);
                        if(resourceInDepot != resourceToRemove) {
                            System.out.println("Error leader depot "+entry.getKey()+" of "+ resourceInDepot + " not " + resourceToRemove);
                            error = true;
                            break;
                        }else{
                            int quantityInDepot = currentPlayer.getBoard().getLeaderDepotResourceNumber(entry.getKey()-3);
                            if(quantityInDepot < quantityToRemove) {
                                System.out.println("Error quantity  " + quantityInDepot + " in leader depot "+(entry.getKey()-3)+", at least " + quantityToRemove);
                                error = true;
                                break;
                            }
                        }
                    }else{
                        //strongbox
                        int quantityInStrongbox = currentPlayer.getBoard().getStrongBoxResource(resourceToRemove);
                        if(quantityInStrongbox < quantityToRemove){
                            System.out.println("Error quantity  " + quantityInStrongbox + " in strongbox, at least " + quantityToRemove);
                            error = true;
                            break;
                        }
                    }
                }

                if(error) {
                    controller.setCurrentState(controller.getStartTurnState());
                    virtualView.startTurn(currentPlayer.getNickname());
                }else{
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
                    //TODO toMiddleTurnState
                    System.out.println("Card added with success in slot "+cardSlot);
                    controller.setCurrentState(controller.getStartTurnState());
                    virtualView.startTurn(currentPlayer.getNickname());
                }
            }
        } catch (emptyDevCardGridSlotSelectedException e) {
            controller.getVirtualView().devCardSaleAction(currentPlayer.getNickname());
        } catch (invalidResourceTypeException e) {
            e.printStackTrace();
        } catch (removeResourceLimitExceededException e) {
            e.printStackTrace();
        } catch (invalidStrongBoxRemoveException e) {
            e.printStackTrace();
        } catch (developmentCardSlotLimitExceededException e) {
            e.printStackTrace();
        } catch (invalidDevelopmentCardLevelPlacementException e) {
            e.printStackTrace();
        }
    }
}
