package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;

import java.util.ArrayList;
import java.util.List;

public class BoardsUpdateMessage extends AnswerMessage {
    private String personalBoard;
    private List<String> otherBoards;

    public BoardsUpdateMessage() {
        this.otherBoards = new ArrayList<String>();
    }

    public void addPersonalBoard(String personalBoard) {
        this.personalBoard = personalBoard;
    }

    public void addOtherBoard(String otherBoard) {
        this.otherBoards.add(otherBoard);
    }

    @Override
    public void selectView(CLI cli) {
        cli.showMessage("La tua Board è: "+this.personalBoard);
    }

    @Override
    public void selectView(GUI gui) {

    }
}