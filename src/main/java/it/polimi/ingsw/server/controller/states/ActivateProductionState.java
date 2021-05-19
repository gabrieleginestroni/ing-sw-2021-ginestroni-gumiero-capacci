package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.controller.states.exceptions.invalidMoveException;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;

import java.util.HashMap;
import java.util.Map;

public class ActivateProductionState implements MultiplayerState {
    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Controller controller) {

    }

    void commonVisit(int productionIndex, Map<Integer, Integer> warehouseMap, Map<Resource, Integer> strongBoxMap, Controller controller) throws invalidMoveException {
        Player currentPlayer = controller.getCurrentPlayer();
        Board board = currentPlayer.getBoard();

        if(productionIndex <= 2){
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

                //TODO remove resources and put results in mediator
            }

        }
        if(productionIndex == 3 || productionIndex == 4){
            //leader production 0 and 1
        }
        if(productionIndex == 5){
            //board production
        }
        if(productionIndex == 6){
            //give production output and exit state
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
