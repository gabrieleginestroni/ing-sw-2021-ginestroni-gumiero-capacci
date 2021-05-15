package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class DevGridUpdateMessage implements AnswerMessage {
    private final String updatedGrid;

    public DevGridUpdateMessage(String updatedGrid) {
        this.updatedGrid = updatedGrid;
    }

    @Override
    public void selectView(View view) {
        view.visitDevGridUpdate(updatedGrid);
    }


}
