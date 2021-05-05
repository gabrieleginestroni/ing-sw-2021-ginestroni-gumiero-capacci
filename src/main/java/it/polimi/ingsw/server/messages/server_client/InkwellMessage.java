package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class InkwellMessage extends AnswerMessage {
    private final String nickname;

    public InkwellMessage(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void selectView(CLI cli) {
        cli.showMessage(nickname + " receives inkwell");
    }

    @Override
    public void selectView(GUI gui) {

    }
}
