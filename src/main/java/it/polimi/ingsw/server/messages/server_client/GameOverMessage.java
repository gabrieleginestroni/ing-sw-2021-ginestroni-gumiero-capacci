package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate all the relevant information about the end of the game.
 */
public class GameOverMessage implements AnswerMessage {
    private final String winner;
    private final Map<String, Integer> gameResult;

    /**
     * @param winner Winner's nickname.
     * @param gameResult The map that contains the nicknames of every player
     *                  mapped to the number of Victory Points they obtained.
     */
    public GameOverMessage(String winner, Map<String, Integer> gameResult) {
        this.winner = winner;
        this.gameResult = gameResult;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     */
    @Override
    public void selectView(View view) {
        view.visitGameOverState(winner, gameResult);
    }
}
