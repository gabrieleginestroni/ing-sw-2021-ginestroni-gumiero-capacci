package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.Player;

import it.polimi.ingsw.server.exceptions.emptyDevCardGridSlotSelectedException;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.games.Game;

import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.Map;

public class DevCardSaleState implements MultiplayerState,SoloState {


    @Override
    public void visitStartTurnState(int move, Controller controller) {

    }

    @Override
    public void visitDevCardSaleState(int row, int col,Map<Resource,Map<Integer,Integer>> resToRemove, Controller controller) {
        VirtualView virtualView = controller.getVirtualView();
        Player currentPlayer = controller.getCurrentPlayer();
        Game model = controller.getModel();
        DevelopmentCard card;
        try {
            card = model.getCardFromGrid(row,col);
            Map<Resource,Integer> cost = card.getCost();
            boolean canPurchase = true;
            for(Map.Entry<Resource,Integer> entry:cost.entrySet())
                if(currentPlayer.getBoard().getResourceNumber(entry.getKey()) < entry.getValue())
                    canPurchase = false;

            if(!canPurchase) {
                //TODO send undo message to client
                controller.setCurrentState(controller.getStartTurnState());
                virtualView.startTurn(currentPlayer.getNickname());
            } else {
                    //TODO remove resource
            }




        } catch (emptyDevCardGridSlotSelectedException e) {
            controller.getVirtualView().devCardSaleAction(currentPlayer.getNickname());
        }





    }
}
