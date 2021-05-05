package it.polimi.ingsw.server.messages.client_server;

public class ChosenLeaderMessage extends Message{
    private final int[] chosenLeaderIndex;

    public ChosenLeaderMessage(int[] chosenLeaderIndex) {
        this.chosenLeaderIndex = chosenLeaderIndex;
    }

    public int[] getChosenLeaderIndex() {
        return chosenLeaderIndex;
    }
}
