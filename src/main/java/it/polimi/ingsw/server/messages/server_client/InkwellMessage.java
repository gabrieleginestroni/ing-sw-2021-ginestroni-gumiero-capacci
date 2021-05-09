package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class InkwellMessage implements AnswerMessage {
    private final String nickname;

    public InkwellMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void selectView(View view) {
        view.visitInkwell(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}
