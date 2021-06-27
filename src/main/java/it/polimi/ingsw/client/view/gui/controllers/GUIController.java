package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.model.Resource;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Abstract class that contains the definition of all the visit of the messages that arrives from network
 */
public abstract class GUIController {
    GUI view;
    NetworkHandler networkHandler;

    static int chosenCardSlot;
    static int chosenRow;
    static int chosenCol;
    static Map<Integer,Map<Resource,Integer>> resToRemove = new HashMap<>();
    static BooleanProperty devTextVisibleProperty;
    static ObjectProperty<Image> chosenDevImg;

    /**
     *
     * @param view set gui
     */
    public void setGUI(GUI view){
        this.view = view;
    }

    /**
     *
     * @param networkHandler set network handler
     */
    public void setNetworkHandler(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }

    //------------------updates of view internal state---------------------
    public void visitBoardsUpdate(){ }

    public void visitLorenzoUpdate(){ }

    public void visitMarketUpdate(){ }

    public void visitDevGridUpdate(){ }

    //--------------------- main menu updates---------------------------------

    /**
     *
     * @param str Error message
     */
    public void visitLobbyFull(String str){ }


    /**
     *
     * @param str Error message
     */
    public void visitLobbyNotReady(String str){ }

    /**
     *
     * @param currentPlayers current players that have logged in to the game
     */
    public void visitLoginSuccess(String currentPlayers){ }

    /**
     *
     * @param str Error message
     */
    public void visitRequestLobbySize(String str){ }

    /**
     *
     * @param str Error Message
     * @param gameID gameId chosen
     */
    public void visitNicknameAlreadyUsed(String str,String gameID){ }

    //------------------------- game updates--------------------------------------------

    /**
     *
     * @param str message of game started
     */
    public void visitGameStarted(String str){ }

    /**
     *
     * @param quantity number of resources to choose
     */
    public void visitInitialResource(int quantity){ }

    /**
     *
     * @param nickname player that owns the inkwell
     */
    public void visitInkwell(String nickname){ }

    /**
     *
     * @param proposedLeaderCards array of cardId leaders to choose from
     */
    public void visitLeaderProposal(int[] proposedLeaderCards){ }

    /**
     *
     * @param res1 First resource to choose from
     * @param res2 Second resource to choose from
     */
    public void visitWhiteMarbleProposal(Resource res1, Resource res2){ }

    /**
     *
     * @param currentPlayerNickname player that starts the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitStartTurn(String currentPlayerNickname, String errorMessage){ }

    /**
     *
     * @param currentPlayerNickname player that is buying the card
     */
    public void visitDevCardSale(String currentPlayerNickname){ }

    /**
     *
     * @param currentPlayerNickname player that is playing the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage){ }

    /**
     *
     * @param currentPlayerNickname player that is playing the turn
     */
    public void visitLeaderAction(String currentPlayerNickname){ }

    /**
     *
     * @param currentPlayerNickname player that is playing the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitMainActionState(String currentPlayerNickname, String errorMessage){ }

    /**
     *
     * @param currentPlayerNickname player that is playing the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitProductionState(String currentPlayerNickname, String errorMessage){ }

    /**
     *
     * @param winner game winner
     * @param gameResult map of all players score
     */
    public void visitGameOverState(String winner, Map<String, Integer> gameResult){ }

    /**
     *
     * @param currentPlayerNickname player that is playing the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitMarketState(String currentPlayerNickname, String errorMessage){ }

    /**
     *
     * @param currentPlayerNickname player that is playing the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitSwapState(String currentPlayerNickname, String errorMessage){ }

    /**
     *
     * @param res resource to manage
     * @param currentPlayerNickname player that is playing the turn
     * @param errorMessage error message to display, if previous action generated one
     */
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage){ }

    public void visitGameAbort(){ }

    public void visitForcedReconnectionUpdate(){ }

    /**
     *
     * @param nickname of the player that has disconnected
     */
    public void visitPlayerDisconnection(String nickname){ }

    /**
     *
     * @param nickname of the player that has reconnected
     */
    public void visitPlayerReconnection(String nickname){ }
}

