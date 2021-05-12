package it.polimi.ingsw.server.messages.client_server;

public class ChosenFirstMoveMessage extends Message {
    private final int move;

    public ChosenFirstMoveMessage(int move) {
        this.move = move;
    }

    public int getMove() {
        return move;
    }
}
