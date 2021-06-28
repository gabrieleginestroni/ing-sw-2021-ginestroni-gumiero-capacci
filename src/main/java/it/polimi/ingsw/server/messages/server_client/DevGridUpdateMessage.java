package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class that represents all the information needed to update all the client's Development Card Grid view.
 */
public class DevGridUpdateMessage implements AnswerMessage {
    private final String updatedGrid;

    /**
     * @param updatedGrid The JSON file that represents the updated GridView class at the actual state of the game.
     */
    public DevGridUpdateMessage(String updatedGrid) {
        this.updatedGrid = updatedGrid;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     */
    @Override
    public void selectView(View view) {
        view.visitDevGridUpdate(updatedGrid);
    }
}
