package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.model.Resource;

public class InitialResourceChooseMessage extends AnswerMessage {
    private int quantity;

    public InitialResourceChooseMessage(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void selectView(CLI cli) {

        StringBuilder str = new StringBuilder("(");

        for(int i=0;i< Resource.values().length-2; i++) {
            str.append(i).append(" for ").append(Resource.values()[i]);
            if(i != Resource.values().length - 3 )
                str.append(", ");
            else
                str.append(")");

        }
        if(quantity == 1)
            cli.showMessage("Choose a resource and a warehouse depot where to store it: " + str );

        if(quantity == 2)
            cli.showMessage("(x2) Choose a resource and a warehouse depot where to store it: " + str );

    }

    @Override
    public void selectView(GUI gui) {

    }

    public int getQuantity() {
        return quantity;
    }
}
