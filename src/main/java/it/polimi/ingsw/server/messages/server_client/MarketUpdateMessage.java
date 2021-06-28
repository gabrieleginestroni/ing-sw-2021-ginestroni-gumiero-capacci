package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class that represents all the information needed to update all the client's Market view.
 */
public class MarketUpdateMessage implements AnswerMessage {
    private final String updatedMarket;

    /**
     * @param updatedMarket The JSON file that represents the updated MarketView class at the actual state of the game.
     */
    public MarketUpdateMessage(String updatedMarket) {
        this.updatedMarket = updatedMarket;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     */
    @Override
    public void selectView(View view) {
        view.visitMarketUpdate(updatedMarket);
    }
}