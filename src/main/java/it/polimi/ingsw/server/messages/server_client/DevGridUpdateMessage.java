package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

public class DevGridUpdateMessage extends AnswerMessage {
    private String updateGrid;

    public DevGridUpdateMessage(String updateGrid) {
        this.updateGrid = updateGrid;
    }

    @Override
    public void selectView(CLI cli) {
        cli.showMessage(updateGrid);
    }

    @Override
    public void selectView(GUI gui) {

    }
}
