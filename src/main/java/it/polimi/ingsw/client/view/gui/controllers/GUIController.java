package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GUIController {
    GUI view;
    NetworkHandler networkHandler;

    static int chosenCardSlot;
    static int chosenRow;
    static int chosenCol;
    static Map<Integer,Map<Resource,Integer>> resToRemove = new HashMap<>();

    public void setGUI(GUI view){
        this.view = view;
    }
    public void setNetworkHandler(NetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }

    //------------------updates of view internal state---------------------
    public abstract void visitBoardsUpdate();

    public abstract void visitLorenzoUpdate();

    public abstract void visitMarketUpdate();

    public abstract void visitDevGridUpdate();

    //--------------------- main menu updates---------------------------------
    public abstract void visitLobbyFull(String str);

    public abstract void visitLobbyNotReady(String str);

    public abstract void visitLoginSuccess(String currentPlayers);

    public abstract void visitRequestLobbySize(String str);

    public abstract void visitNicknameAlreadyUsed(String str,String gameID);

    //------------------------- game updates--------------------------------------------

    public abstract void visitGameStarted(String str);

    public abstract void visitInitialResource(int quantity); 

    public abstract void visitInkwell(String nickname); 

    public abstract void visitLeaderProposal(int[] proposedLeaderCards); 

    public abstract void visitWhiteMarbleProposal(Resource res1, Resource res2); 

    public abstract void visitStartTurn(String currentPlayerNickname, String errorMessage);

    public abstract void visitDevCardSale(String currentPlayerNickname); 

    public abstract void visitMiddleTurn(String currentPlayerNickname, String errorMessage); 

    public abstract void visitLeaderAction(String currentPlayerNickname); 

    public abstract void visitMainActionState(String currentPlayerNickname, String errorMessage); 

    public abstract void visitProductionState(String currentPlayerNickname, String errorMessage); 

    public abstract void visitGameOverState(String winner, Map<String, Integer> gameResult); 

    public abstract void visitMarketState(String currentPlayerNickname, String errorMessage); 

    public abstract void visitSwapState(String currentPlayerNickname, String errorMessage); 

    public abstract void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage);

    public abstract void visitGameAbort();

    public abstract void visitForcedReconnectionUpdate(String personalBoard, List<String> otherBoards,String updatedGrid,String updatedMarket);
}

