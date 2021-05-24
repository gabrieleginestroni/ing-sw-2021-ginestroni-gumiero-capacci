package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

public class DevCardSaleStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;

    public DevCardSaleStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitDevCardSale(currentPlayerNickname);
    }
}
