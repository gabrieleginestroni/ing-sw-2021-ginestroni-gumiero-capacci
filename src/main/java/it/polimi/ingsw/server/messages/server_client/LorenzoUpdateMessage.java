package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class LorenzoUpdateMessage extends AnswerMessage {
    private String updatedLorenzo;

    public LorenzoUpdateMessage(String updatedLorenzo) {
        this.updatedLorenzo = updatedLorenzo;
    }

    @Override
    public void selectView(CLI cli) {
        cli.showMessage(updatedLorenzo);
    }

    @Override
    public void selectView(GUI gui) {

    }
}
