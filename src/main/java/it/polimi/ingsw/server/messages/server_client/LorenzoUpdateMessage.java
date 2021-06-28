package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class that represents all the information needed to update the only client Lorenzo view.
 */
public class LorenzoUpdateMessage implements AnswerMessage {
    private final String updatedLorenzo;

    /**
     * @param updatedLorenzo The JSON file that represents the updated LorenzoView class.
     */
    public LorenzoUpdateMessage(String updatedLorenzo) {
        this.updatedLorenzo = updatedLorenzo;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitLorenzoUpdate(updatedLorenzo);
    }
}
