package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

public class InitialResourceChooseMessage implements AnswerMessage {
    private final int quantity;

    public InitialResourceChooseMessage(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void selectView(View view) {
        view.visitInitialResource(quantity);


    }


}
