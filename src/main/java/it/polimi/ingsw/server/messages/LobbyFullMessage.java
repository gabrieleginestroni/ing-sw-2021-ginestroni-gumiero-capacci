package it.polimi.ingsw.server.messages;

public class LobbyFullMessage extends AnswerMessage {
    @Override
    public void selectView() {
        System.out.println("Game full. Please choose another game ID:");
    }
}
