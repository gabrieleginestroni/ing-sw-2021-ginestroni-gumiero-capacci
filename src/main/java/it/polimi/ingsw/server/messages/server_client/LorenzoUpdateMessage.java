package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class LorenzoUpdateMessage implements AnswerMessage {
    private final String updatedLorenzo;

    public LorenzoUpdateMessage(String updatedLorenzo) {
        this.updatedLorenzo = updatedLorenzo;
    }

    @Override
    public void selectView(View view) {
        view.visitLorenzoUpdate(updatedLorenzo);
    }


}
