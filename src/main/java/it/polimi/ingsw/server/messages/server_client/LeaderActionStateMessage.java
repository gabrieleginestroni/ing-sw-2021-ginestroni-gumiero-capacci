package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class LeaderActionStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;


    public LeaderActionStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public void selectView(View view) {
        view.visitLeaderAction(this.currentPlayerNickname);
    }
}
