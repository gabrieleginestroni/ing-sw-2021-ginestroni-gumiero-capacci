package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.MultiplayerController;
import it.polimi.ingsw.server.controller.SoloController;
import it.polimi.ingsw.server.messages.client_server.ChosenFirstMoveMessage;
import it.polimi.ingsw.server.messages.client_server.Message;

public class StartTurnState implements MultiplayerState,SoloState {

    @Override
    public void visitStartTurnState(int move, Controller controller) {


        String currentPlayer = controller.getCurrentPlayer().getNickname();
        switch (move){
            case 0:
                controller.setCurrentState(new MarketState());
                controller.getVirtualView().marketAction(currentPlayer);

                break;
            case 1:
                controller.setCurrentState(new DevCardSaleState());
                controller.getVirtualView().devCardSaleAction(currentPlayer);
                break;
            case 2:
                controller.setCurrentState(new ActivateProductionState());
                controller.getVirtualView().productionAction(currentPlayer);
                break;
            default:
                controller.setCurrentState(new LeaderActionState());
                controller.getVirtualView().leaderAction(currentPlayer);
                break;

        }



    }
}
