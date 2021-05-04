package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class MarketUpdateMessage extends AnswerMessage {
    private String updatedMarket;

    public MarketUpdateMessage(String updatedMarket) {
        this.updatedMarket = updatedMarket;
    }

    @Override
    public void selectView(CLI cli) {
        cli.showMessage(updatedMarket);
    }

    @Override
    public void selectView(GUI gui) {

    }
}