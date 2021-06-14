package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

import java.util.ArrayList;
import java.util.List;

public class ForcedReconnectionUpdateMessage implements AnswerMessage {
    private String personalBoard;
    private final List<String> otherBoards;
    private final String updatedGrid;
    private final String updatedMarket;


    public ForcedReconnectionUpdateMessage(String updatedGrid,String updatedMarket) {
        this.otherBoards = new ArrayList<>();
        this.updatedGrid = updatedGrid;
        this.updatedMarket = updatedMarket;
    }

    public void addPersonalBoard(String personalBoard) {
        this.personalBoard = personalBoard;
    }

    public void addOtherBoard(String otherBoard) {
        this.otherBoards.add(otherBoard);
    }


    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitForcedReconnectionUpdate(personalBoard,otherBoards,updatedGrid,updatedMarket);
    }
}
