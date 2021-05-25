package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Resource;

import java.util.List;
import java.util.Map;

public interface GUIController {

    public void setGUI(GUI view);
    //------------------updates of view internal state---------------------
    public void visitBoardsUpdate(GUI view);

    public void visitLorenzoUpdate(GUI view);

    public void visitMarketUpdate(GUI view);

    public void visitDevGridUpdate(GUI view);

    //--------------------- main menu updates---------------------------------
    public void visitLobbyFull(String str);

    public void visitLobbyNotReady(String str);

    public void visitLoginSuccess(String currentPlayers);

    public void visitRequestLobbySize(String str);

    public void visitNicknameAlreadyUsed(String str,String gameID);
    //------------------------- game updates--------------------------------------------
    public void visitGameStarted(String str); 

    public void visitInitialResource(int quantity); 

    public void visitInkwell(String nickname); 

    public void visitLeaderProposal(int[] proposedLeaderCards); 

    public void visitWhiteMarbleProposal(Resource res1, Resource res2); 

    public void visitStartTurn(String currentPlayerNickname, String errorMessage);

    public void visitDevCardSale(String currentPlayerNickname); 

    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage); 

    public void visitLeaderAction(String currentPlayerNickname); 

    public void visitMainActionState(String currentPlayerNickname, String errorMessage); 

    public void visitProductionState(String currentPlayerNickname, String errorMessage); 

    public void visitGameOverState(String winner, Map<String, Integer> gameResult); 

    public void visitMarketState(String currentPlayerNickname, String errorMessage); 

    public void visitSwapState(String currentPlayerNickname, String errorMessage); 

    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage); 

}
