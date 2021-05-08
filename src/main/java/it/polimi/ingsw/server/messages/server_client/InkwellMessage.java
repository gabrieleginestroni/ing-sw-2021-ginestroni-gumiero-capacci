package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class InkwellMessage implements AnswerMessage {
    private final String nickname;
    private final String updatedBoard;

    public InkwellMessage(String nickname,String updatedBoard) {
        this.nickname = nickname;
        this.updatedBoard = updatedBoard;
    }

    @Override
    public void selectView(View view) {
        view.visitInkwell(nickname,updatedBoard);
    }



    public String getNickname() {
        return nickname;
    }
}
