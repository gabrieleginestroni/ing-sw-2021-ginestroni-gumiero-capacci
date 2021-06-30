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

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the state in which the current player chose to perform an ActivateProduction Action and
 * now has to choose the productions to activate.
 */
public class ActivateProductionState implements MultiplayerState {

    /**
     * This method is used to perform the code in common between the multiplayer and the solo version of this state:
     * the controller checks the action chose by the player if it is possible to apply it and, in case this
     * check resolves positively, applies that action to the model.
     * @param productionIndex The integer that represents the choice of the player: 0 - 2 for the activation of the
     *                        production of the DevelopmentCard placed at the top of the relative CardSlot in the board
     *                        of the current player, 3 - 4 for the production power of the relative active Leader Cards,
     *                        5 for the base production of every board and 6 to receive outputs. Every chosen index is
     *                        saved in a data structure in the CommunicationMediator: in this way every time this visit
     *                        is called it checks if the specified productionIndex has been already activated, in order
     *                        not to allow multiple activations of the same production in the same turn.
     * @param warehouseMap The map that contains the indexes of the Warehouse or Leader Depots the current player
     *                     chose to get resources from, mapped to the number of resources to remove: 0 - 2 for
     *                     Warehouse Depots, 3 - 4 for Leader Depots.
     * @param strongBoxMap The map that contains the resources the current player chose to get from the Strongbox,
     *                     each one mapped to the quantity of that specific resource to remove.
     * @param chosenResource This parameter is nullable because it is only used for those production that produce as
     *                       output a resource at player's choice (Board and Leader productions): in case it is not
     *                       null it can only be COIN, SERVANT, SHIELD or STONE, not FAITH.
     * @param controller The controller that handles the current game.
     * @throws invalidMoveException Thrown when the current player requests any kind of unacceptable move: this exception
     *                              contains a error message string that will be shown to the current player in the
     *                              next state he will navigate to.
     */
    void commonVisit(int productionIndex, Map<Integer, Integer> warehouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) throws invalidMoveException {
        Player currentPlayer = controller.getCurrentPlayer();
        Board board = currentPlayer.getBoard();

        if(controller.getMediator().getProductionHistory().contains(productionIndex))
            throw new invalidMoveException("Cannot activate a production twice in the same turn!");

        if(chosenResource != null && chosenResource.equals(Resource.FAITH))
            throw new invalidMoveException("Cannot produce Faith Points!");

        if(chosenResource != null && chosenResource.equals(Resource.WHITE))
            throw new invalidMoveException("Cannot produce white marbles!");

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
                for(Map.Entry<Resource, Integer> entry:prodInput.entrySet()){
                    if(!entry.getValue().equals(checkMap.get(entry.getKey())))
                        throw new invalidMoveException("Wrong number of resources selected");
                }
                //check unrequested resources
                for(Map.Entry<Resource, Integer> entry:checkMap.entrySet()){
                    if(!entry.getValue().equals(prodInput.get(entry.getKey())))
                        throw new invalidMoveException("Unrequested resource found: " + entry.getKey());
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
                controller.getMediator().setMainActionDone();
                controller.getMediator().getProductionHistory().add(productionIndex);
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
            controller.getMediator().setMainActionDone();
            controller.getMediator().getProductionHistory().add(productionIndex);
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
                    if(0 <= depotIndex && depotIndex <= 2) {
                        res = board.getWarehouseDepotResourceType(depotIndex);
                        board.removeWarehouseDepotResource(res, entry.getValue(), depotIndex);
                    }
                    else {
                        res = board.getLeaderDepotResourceType(depotIndex - 3);
                        board.removeLeaderDepotResource(res, entry.getValue(), depotIndex - 3);
                    }
                }
            }catch (invalidStrongBoxRemoveException | invalidResourceTypeException | removeResourceLimitExceededException e) {
                throw new invalidMoveException("Generic board production error");
            }

            Map<Resource, Integer> resMap = new HashMap<>();
            resMap.put(chosenResource, 1);

            controller.getMediator().addProductionOutputs(resMap);
            controller.getMediator().setMainActionDone();
            controller.getMediator().getProductionHistory().add(productionIndex);
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
        }
    }

    /**
     * This method is used in a Multiplayer Game to perform the activation of the specified production and then the right state
     * transition on the base of the past choices of the current player: the controller continues to cycle on the ActivationProduction
     * state until the current player asks to terminate this process and, after that, if the current player has already done
     * a Leader Action the controller automatically terminates his turn, otherwise the controller switches to the MiddleTurn state.
     * In the case the activation of the productions doesn't resolve positively it appears that the player hasn't done
     * a Main Action yet, so the controller switches to the StartTurn state if he has neither done a Leader Action,
     * otherwise switches to the MainAction state.
     * @param productionIndex The integer that represents the choice of the player: 0 - 2 for the activation of the
     *                        production of the DevelopmentCard placed at the top of the relative CardSlot in the board
     *                        of the current player, 3 - 4 for the production power of the relative active Leader Cards,
     *                        5 for the base production of every board and 6 to receive outputs. Every chosen index is
     *                        saved in a data structure in the CommunicationMediator: in this way every time this visit
     *                        is called it checks if the specified productionIndex has been already activated, in order
     *                        not to allow multiple activations of the same production in the same turn.
     * @param wareHouseMap The map that contains the indexes of the Warehouse or Leader Depots the current player
     *                     chose to get resources from, mapped to the number of resources to remove: 0 - 2 for
     *                     Warehouse Depots, 3 - 4 for Leader Depots.
     * @param strongBoxMap The map that contains the resources the current player chose to get from the Strongbox,
     *                     each one mapped to the quantity of that specific resource to remove.
     * @param chosenResource This parameter is nullable because it is only used for those production that produce as
     *                       output a resource at player's choice (Board and Leader productions): in case it is not
     *                       null it can only be COIN, SERVANT, SHIELD or STONE, not FAITH.
     * @param controller The controller that handles the current game.
     */
    @Override
    public void visitActivateProductionState(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource, Controller controller) {
        try {
            commonVisit(productionIndex, wareHouseMap, strongBoxMap, chosenResource, controller);
            if(productionIndex != 6) {
                controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), null);
            }else {
                if (!controller.getMediator().isMainActionDone()) { //this means that no production has been correctly activated
                    if(controller.getMediator().isLeaderActionDone()) {
                        controller.setCurrentState(controller.getMainActionState());
                        controller.getVirtualView().mainAction(controller.getCurrentPlayer().getNickname(), "Please do a main action");
                    } else {
                        controller.setCurrentState(controller.getStartTurnState());
                        controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(),null);
                    }

                } else {
                    if (!controller.getMediator().isLeaderActionDone()) {
                        controller.setCurrentState(controller.getMiddleTurnState());
                        controller.getVirtualView().middleTurn(controller.getCurrentPlayer().getNickname(), null);
                    } else {
                        controller.setCurrentState(controller.getEndTurnState());
                        controller.getEndTurnState().visitEndTurnState(controller);
                    }
                }
            }
        } catch (invalidMoveException e) {
            controller.getVirtualView().productionAction(controller.getCurrentPlayer().getNickname(), e.getErrorMessage());
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
    public void visitMiddleTurnState(int move, Controller controller) { }

    @Override
    public void visitEndTurnState(Controller controller) { }

    @Override
    public void visitLeaderActionState(Map<Integer, Integer> actionMap, Controller controller) { }

    @Override
    public void visitResourceManagementState(String errorMessage,Controller controller) { }

    @Override
    public void visitSwapState(int dep1,int dep2,Controller controller) { }

    @Override
    public void visitWhiteMarbleState(Controller controller) { }

    @Override
    public void visitEndGameState(String winner, Controller controller) { }
}
