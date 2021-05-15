package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class MarketUpdateMessage implements AnswerMessage {
    private final String updatedMarket;

    public MarketUpdateMessage(String updatedMarket) {
        this.updatedMarket = updatedMarket;
    }

    @Override
    public void selectView(View view) {
        view.visitMarketUpdate(updatedMarket);
    }


}