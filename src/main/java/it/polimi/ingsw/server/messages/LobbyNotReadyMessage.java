package it.polimi.ingsw.server.messages;

public class LobbyNotReadyMessage extends AnswerMessage {
    @Override
    public void selectView() {
        System.out.println("Lobby it's being creating, but not ready yet. Please choose another game ID:");
    }
}
