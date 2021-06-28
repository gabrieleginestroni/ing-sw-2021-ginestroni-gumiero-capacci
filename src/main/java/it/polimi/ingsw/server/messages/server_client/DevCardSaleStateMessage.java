package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the start of the Development Card Purchase Action.
 */
public class DevCardSaleStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;

    /**
     * @param currentPlayerNickname Current player's nickname.
     */
    public DevCardSaleStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     * @throws invalidClientInputException Thrown when the current player tries to perform an illegal action.
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitDevCardSale(currentPlayerNickname);
    }
}
