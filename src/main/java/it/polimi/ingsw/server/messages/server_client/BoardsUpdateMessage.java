package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class that represents all the information needed to update a single client's boards view.
 */
public class BoardsUpdateMessage implements AnswerMessage {
    private String personalBoard;
    private final List<String> otherBoards;

    /**
     * All private attributes are empty.
     */
    public BoardsUpdateMessage() {
        this.otherBoards = new ArrayList<>();
    }

    /**
     * This method is used to set the information regarding the view of the personal board of the player.
     * @param personalBoard The JSON file that represents the updated BoardView of a player's
     *                      PersonalBoard at the actual state of the game.
     */
    public void addPersonalBoard(String personalBoard) {
        this.personalBoard = personalBoard;
    }

    /**
     * This method is used to append to a list the information regarding the view of the personal board of one of the other players.
     * @param otherBoard The JSON file that represents the updated HiddenHand-free BoardView of the PersonalBoard
     *                   of one of the other players at the actual state of the game.
     */
    public void addOtherBoard(String otherBoard) {
        this.otherBoards.add(otherBoard);
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitBoardsUpdate(personalBoard,otherBoards);
    }
}