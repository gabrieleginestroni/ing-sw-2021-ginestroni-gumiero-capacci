package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;

import java.util.ArrayList;
import java.util.List;

public class BoardsUpdateMessage implements AnswerMessage {
    private String personalBoard;
    private final List<String> otherBoards;

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
    public void selectView(View view) {
        view.visitBoardsUpdate(personalBoard,otherBoards);
    }





}