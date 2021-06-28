package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate to every other players than the first that he must choose a certain
 * number of resources to complete the setup of the game.
 */
public class InitialResourceChooseMessage implements AnswerMessage {
    private final int quantity;

    /**
     * @param quantity The number of resources to choose.
     */
    public InitialResourceChooseMessage(int quantity) {
        this.quantity = quantity;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitInitialResource(quantity);
    }
}
