package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class that represents all the information needed to update a client's entire view after his reconnection to the game.
 */
public class ForcedReconnectionUpdateMessage implements AnswerMessage {
    private String personalBoard;
    private final List<String> otherBoards;
    private final String updatedGrid;
    private final String updatedMarket;

    /**
     * @param updatedGrid The JSON file that represents the updated GridView class at the actual state of the game.
     * @param updatedMarket The JSON file that represents the updated MarketView class at the actual state of the game.
     */
    public ForcedReconnectionUpdateMessage(String updatedGrid,String updatedMarket) {
        this.otherBoards = new ArrayList<>();
        this.updatedGrid = updatedGrid;
        this.updatedMarket = updatedMarket;
    }

    /**
     * This method is used to set the information regarding the view of the personal board of the player.
     * @param personalBoard The JSON file that represents the updated BoardView class of the relative
     *                      PersonalBoard at the actual state of the game.
     */
    public void addPersonalBoard(String personalBoard) {
        this.personalBoard = personalBoard;
    }

    /**
     * This method is used to append to a list the information regarding the view of the personal board of one of the other players.
     * @param otherBoard The JSON file that represents the updated HiddenHand-free BoardView class of the PersonalBoard
     *                   of one of the other players at the actual state of the game.
     */
    public void addOtherBoard(String otherBoard) {
        this.otherBoards.add(otherBoard);
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     * @throws invalidClientInputException Thrown when the current player tries to perform an illegal action.
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitForcedReconnectionUpdate(personalBoard,otherBoards,updatedGrid,updatedMarket);
    }
}
