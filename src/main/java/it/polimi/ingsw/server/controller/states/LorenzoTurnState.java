package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the Solo version of the EndTurn state.
 */
public class LorenzoTurnState extends EndTurnState implements SoloState {

    /**
     * This method is used in a Solo Game to perform Lorenzo's action and, after that, the right state transition
     * on the base of the actual state of the current game: after the application of the
     * effect of the last drawn Action Token, if the game is over the controller switches
     * to the EndGame state (Lorenzo won), otherwise the turn is passed to the player.
     * @param controller The controller that handles the current game.
     */
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
