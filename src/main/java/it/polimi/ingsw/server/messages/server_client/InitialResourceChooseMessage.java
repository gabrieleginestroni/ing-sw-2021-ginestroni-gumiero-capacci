package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

public class InitialResourceChooseMessage implements AnswerMessage {
    private int quantity;

    public InitialResourceChooseMessage(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void selectView(View view) {
        view.visitInitialResource(quantity);


    }


}
