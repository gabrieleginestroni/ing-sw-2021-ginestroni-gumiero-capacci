package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.controller.MultiplayerController;
import it.polimi.ingsw.server.controller.SoloController;
import it.polimi.ingsw.server.controller.states.MultiplayerState;
import it.polimi.ingsw.server.controller.states.SoloState;
import it.polimi.ingsw.server.messages.client_server.Message;

public class DevCardSaleStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;

    public DevCardSaleStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    @Override
    public void selectView(View view) {
        view.visitDevCardSale(currentPlayerNickname);

    }
}
