package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the start of the Market Action.
 */
public class MarketStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;
    private final String errorMessage;

    /**
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public MarketStateMessage(String currentPlayerNickname, String errorMessage) {
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitMarketState(this.currentPlayerNickname,this.errorMessage);
    }
}
