package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

import java.util.Map;

public class GameOverMessage implements AnswerMessage {
    private final String winner;
    private final Map<String, Integer> gameResult;

    public GameOverMessage(String winner, Map<String, Integer> gameResult) {
        this.winner = winner;
        this.gameResult = gameResult;
    }

    @Override
    public void selectView(View view) {
        view.visitGameOverState(winner, gameResult);
    }
}
