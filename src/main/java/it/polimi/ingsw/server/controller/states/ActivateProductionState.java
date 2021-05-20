package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.exceptions.invalidResourceTypeException;
import it.polimi.ingsw.server.exceptions.invalidStrongBoxRemoveException;
import it.polimi.ingsw.server.exceptions.removeResourceLimitExceededException;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import java.util.HashMap;
import java.util.Map;

public class ActivateProductionState implements MultiplayerState {
    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) {
        try {
            commonVisit(productionIndex, wareHouseMap, strongBoxMap, chosenResource, controller);
            if(productionIndex != 6) {
                controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), null);
            }else{
                if(!controller.getMediator().isLeaderActionDone()) {
                    controller.setCurrentState(controller.getMiddleTurnState());
                    controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
                }else{
                    controller.setCurrentState(controller.getEndTurnState());
                    controller.getEndTurnState().visitEndTurnState(controller);
                }
            }
        } catch (invalidMoveException e) {
            controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), e.getErrorMessage());
        }
    }

    void commonVisit(int productionIndex, Map<Integer, Integer> warehouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) throws invalidMoveException {
        Player currentPlayer = controller.getCurrentPlayer();
        Board board = currentPlayer.getBoard();

        if(chosenResource != null && chosenResource.equals(Resource.FAITH))
            throw new invalidMoveException("Cannot produce Faith Points!");

        if(productionIndex >= 0 && productionIndex <= 2){
            //card slot 0, 1 and 2
            DevelopmentCard card = board.getTopCard(productionIndex);
            if(card == null){
                throw new invalidMoveException("The selected card slot is empty!");
            } else {
                Map<Resource, Integer> prodInput = card.getProductionInput();

                //build a <Resource, Integer> map for checking the player chosen resources to remove
                Map<Resource, Integer> checkMap = new HashMap<>();
                for(Map.Entry<Integer, Integer> entry: warehouseMap.entrySet()){
                    int depotIndex = entry.getKey();
                    int prevQty = 0;
                    if(0 <= depotIndex && depotIndex <= 2){
                        Resource res = board.getWarehouseDepotResourceType(depotIndex);
                        if(res == null)
                            throw new invalidMoveException("Cannot remove resource from an empty warehouse depot!");
                        if(checkMap.get(res) != null)
                            prevQty = checkMap.get(res);
                        checkMap.put(res, prevQty + entry.getValue());
                        //checkMap.merge(res, entry.getValue(), Integer::sum);
                    }
                    if(depotIndex == 3 || depotIndex == 4){
                        Resource res;
                        try {
                            res = board.getLeaderDepotResourceType(depotIndex - 3);
                        }catch(IndexOutOfBoundsException e){
                            throw new invalidMoveException("Cannot remove resource from a non-existing leader depot!");
                        }
                        if(checkMap.get(res) != null)
                            prevQty = checkMap.get(res);
                        checkMap.put(res, prevQty + entry.getValue());
                    }
                }
                for(Map.Entry<Resource, Integer> entry:strongBoxMap.entrySet()){
                    int prevQty = 0;
                    Resource res = entry.getKey();
                    if(checkMap.get(res) != null)
                        prevQty = checkMap.get(res);
                    checkMap.put(res, prevQty + entry.getValue());
                }

                //check if the total amount of checkMap resources is the same of the one required by the card
                for(Map.Entry<Resource, Integer> entry:checkMap.entrySet()){
                    if(!entry.getValue().equals(prodInput.get(entry.getKey())))
                        throw new invalidMoveException("Wrong number of resources selected");
                }

                //check if the selected quantity of resources is present
                for(Map.Entry<Integer, Integer> entry: warehouseMap.entrySet()){
                    int depotIndex = entry.getKey();
                    if(0 <= depotIndex && depotIndex <= 2){
                        if(board.getWarehouseDepotResourceNumber(depotIndex) < entry.getValue())
                            throw new invalidMoveException("Insufficient resources in warehouse depot " + depotIndex);
                    }
                    if(depotIndex == 3 || depotIndex == 4){
                        if(board.getLeaderDepotResourceNumber(depotIndex - 3) < entry.getValue())
                            throw new invalidMoveException("Insufficient resources in leader depot " + (depotIndex - 3));
                    }
                }
                for(Map.Entry<Resource, Integer> entry:strongBoxMap.entrySet()){
                    if(board.getStrongBoxResource(entry.getKey()) < entry.getValue())
                        throw new invalidMoveException("Insufficient number of " + entry.getKey() + "in strongbox");
                }

                //remove resources
                try {
                    for (Map.Entry<Integer, Integer> entry : warehouseMap.entrySet()) {
                        int depotIndex = entry.getKey();
                        if (0 <= depotIndex && depotIndex <= 2)
                            board.removeWarehouseDepotResource(board.getWarehouseDepotResourceType(depotIndex), entry.getValue(), depotIndex);
                        if(depotIndex == 3 || depotIndex == 4)
                            board.removeLeaderDepotResource(board.getLeaderDepotResourceType(depotIndex - 3), entry.getValue(), depotIndex - 3);
                    }
                    for(Map.Entry<Resource, Integer> entry:strongBoxMap.entrySet()){
                        board.removeStrongboxResource(entry.getKey(), entry.getValue());
                    }
                } catch (invalidResourceTypeException | removeResourceLimitExceededException | invalidStrongBoxRemoveException e) {
                    throw new invalidMoveException("Generic development card production error");
                }

                controller.getMediator().addProductionOutputs(card.getProductionOutput());
            }

        }

        if(productionIndex == 3 || productionIndex == 4){
            //leader production 0 and 1
            LeaderCard card = board.getActiveLeaderCard().get(productionIndex - 3);
            if(card == null || !card.getPower().equals("production")){
                throw new invalidMoveException("No such active production leader card exist!");
            }

            //check if total amount of resource is 1
            int totalQty = 0;
            for (Map.Entry<Integer, Integer> entry : warehouseMap.entrySet())
                totalQty += entry.getValue();
            for(Map.Entry<Resource, Integer> entry:strongBoxMap.entrySet())
                totalQty += entry.getValue();
            if(totalQty != 1)
                throw new invalidMoveException("Too many resources selected!");

            //check resource type
            Resource res = null;
            if(warehouseMap.isEmpty()){
                res = strongBoxMap.entrySet().iterator().next().getKey();
                if(board.getStrongBoxResource(res) < 1)
                    throw new invalidMoveException("Insufficient number of " + res + " in strongbox");
            }else{
                int depotIndex = warehouseMap.entrySet().iterator().next().getKey();
                if(0 <= depotIndex && depotIndex <= 2) {
                    res = board.getWarehouseDepotResourceType(depotIndex);
                    if (board.getWarehouseDepotResourceNumber(depotIndex) < 1)
                        throw new invalidMoveException("Insufficient number of " + res + " in warehouse depot " + depotIndex);
                }
                if(depotIndex == 3 || depotIndex == 4) {
                    res = board.getLeaderDepotResourceType(depotIndex - 3);
                    if(board.getLeaderDepotResourceNumber(depotIndex - 3) < 1)
                        throw new invalidMoveException("Insufficient number of " + res + " in leader depot " + (depotIndex - 3));
                }
            }
            if(!res.equals(card.getResource()))
                throw new invalidMoveException("Wrong type of resource selected");

            //remove resource
            try{
                if(warehouseMap.isEmpty()){
                    board.removeStrongboxResource(res, 1);
                }else{
                    int depotIndex = warehouseMap.entrySet().iterator().next().getKey();
                    if(0 <= depotIndex && depotIndex <= 2){
                        board.removeWarehouseDepotResource(res, 1, depotIndex);
                    }
                    if(depotIndex == 3 || depotIndex == 4){
                        board.removeLeaderDepotResource(res, 1, depotIndex - 3);
                    }

                }
            } catch (invalidStrongBoxRemoveException | invalidResourceTypeException | removeResourceLimitExceededException e) {
                throw new invalidMoveException("Generic leader card production error");
            }

            Map<Resource, Integer> resMap = new HashMap<>();
            resMap.put(chosenResource, 1);
            resMap.put(Resource.FAITH, 1);

            controller.getMediator().addProductionOutputs(resMap);
        }

        if(productionIndex == 5){
            //board production

            //check if total amount of resource is 2
            int totalQty = 0;
            for (Map.Entry<Integer, Integer> entry : warehouseMap.entrySet())
                totalQty += entry.getValue();
            for(Map.Entry<Resource, Integer> entry:strongBoxMap.entrySet())
                totalQty += entry.getValue();
            if(totalQty != 2)
                throw new invalidMoveException("Wrong resources amount selected!");

            //check if owned resources are enough
            Resource res = null;
            for (Map.Entry<Resource, Integer> entry : strongBoxMap.entrySet()) {
                if (board.getStrongBoxResource(entry.getKey()) < entry.getValue())
                    throw new invalidMoveException("Insufficient number of " + entry.getKey() + " in strongbox");
            }
            for (Map.Entry<Integer, Integer> entry : warehouseMap.entrySet()) {
                int depotIndex = entry.getKey();
                if(0 <= depotIndex && depotIndex <= 2) {
                    res = board.getWarehouseDepotResourceType(depotIndex);
                    if (board.getWarehouseDepotResourceNumber(depotIndex) < entry.getValue())
                        throw new invalidMoveException("Insufficient number of " + res + " in warehouse depot " + depotIndex);
                }
                if(depotIndex == 3 || depotIndex == 4) {
                    res = board.getLeaderDepotResourceType(depotIndex - 3);
                    if(board.getLeaderDepotResourceNumber(depotIndex - 3) < entry.getValue())
                        throw new invalidMoveException("Insufficient number of " + res + " in leader depot " + (depotIndex - 3));
                }
            }

            //Remove resources
            try{
                for (Map.Entry<Resource, Integer> entry : strongBoxMap.entrySet())
                    board.removeStrongboxResource(entry.getKey(), entry.getValue());
                for (Map.Entry<Integer, Integer> entry : warehouseMap.entrySet()) {
                    int depotIndex = entry.getKey();
                    res = board.getWarehouseDepotResourceType(depotIndex);
                    if(0 <= depotIndex && depotIndex <= 2)
                        board.removeWarehouseDepotResource(res, entry.getValue(), depotIndex);
                    else
                        board.removeLeaderDepotResource(res, entry.getValue(), depotIndex-3);
                }
            }catch (invalidStrongBoxRemoveException | invalidResourceTypeException | removeResourceLimitExceededException e) {
                throw new invalidMoveException("Generic board production error");
            }

            Map<Resource, Integer> resMap = new HashMap<>();
            resMap.put(chosenResource, 1);

            controller.getMediator().addProductionOutputs(resMap);
        }

        if(productionIndex == 6){
            //give production output and exit state
            Map<Resource, Integer> outputProduction = controller.getMediator().getProductionOutputs();
            for (Map.Entry<Resource, Integer> entry : outputProduction.entrySet()){
                if(entry.getKey() != Resource.FAITH)
                    board.addStrongboxResource(entry.getKey(), entry.getValue());
                else {
                    int activatedSectionIndex = board.giveFaithPoints(entry.getValue());
                    if(activatedSectionIndex != -1)
                        controller.getModel().vaticanReport(activatedSectionIndex);
                }
            }

            controller.getMediator().setMainActionDone();
        }

    }

    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitMainActionState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

    }

    @Override
    public void visitMarketState(int move, int index, Controller controller) {

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
    public void visitEndGameState(String winner, Controller controller) {

    }
}
