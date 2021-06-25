package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

public class LorenzoTurnState extends EndTurnState implements SoloState {
    @Override
    public void visitEndTurnState(Controller controller) {
        int activatedSection = controller.getModel().drawFromTokenPile();
        if(activatedSection != -1)
            controller.getModel().vaticanReport(activatedSection);

        if(!controller.isGameOver()) {
            controller.setCurrentState(controller.getStartTurnState());
            controller.getVirtualView().startTurn(controller.getCurrentPlayer().getNickname(), "Lorenzo has done his move!");
            controller.getMediator().refresh();
        }else{
            controller.setCurrentState(controller.getEndGameState());
            controller.getEndGameState().visitEndGameState(null, controller);
        }
    }
}
