package it.polimi.ingsw.server.controller.states;

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

public class LeaderActionState implements MultiplayerState,SoloState {

    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot, Controller controller) {

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

    void commonVisit(Map<Integer, Integer> actionMap, Controller controller) {
        Player currentPlayer = controller.getCurrentPlayer();
        Board board = currentPlayer.getBoard();
        try {
            if (actionMap.isEmpty())
                throw new invalidMoveException("No leader action available");

            for (Map.Entry<Integer, Integer> entry : actionMap.entrySet()) {
                switch (entry.getValue()){
                    case 0: break;
                    case 1:
                        LeaderCard leader = board.getInactiveLeaderCard().get(entry.getKey());
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

                    case 2:
                        int activatedSection = board.discardLeaderCard(entry.getKey());
                        if(activatedSection != -1){
                            //TODO
                        }
                }
            }

        } catch (invalidMoveException e) {
            System.out.println(currentPlayer.getNickname() + " " + e.getErrorMessage());
            controller.setCurrentState(controller.getMiddleTurnState());
            controller.getVirtualView().middleTurn(currentPlayer.getNickname(),e.getErrorMessage());

        }
    }
}