package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.client.ClientView;

public class RequestLobbySizeMessage extends AnswerMessage {
    @Override
    public void selectView() {
        System.out.println("A game with the given ID doesn't exist. Type the size of the game you'd like:");
    }
}
