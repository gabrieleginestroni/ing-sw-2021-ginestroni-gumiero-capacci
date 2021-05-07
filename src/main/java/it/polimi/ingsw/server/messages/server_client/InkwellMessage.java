package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class InkwellMessage extends AnswerMessage {
    private final String nickname;
    private final String updatedBoard;

    public InkwellMessage(String nickname,String updatedBoard) {
        this.nickname = nickname;
        this.updatedBoard = updatedBoard;
    }

    @Override
    public void selectView(CLI cli) {
        cli.showMessage(nickname + " receives inkwell");
        cli.showMessage(updatedBoard);
    }

    @Override
    public void selectView(GUI gui) {

    }

    public String getNickname() {
        return nickname;
    }
}
