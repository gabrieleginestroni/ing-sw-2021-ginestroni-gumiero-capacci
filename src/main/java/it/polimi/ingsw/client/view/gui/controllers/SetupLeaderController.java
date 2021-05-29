package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Map;

public class SetupLeaderController extends GUIController {
    @FXML
    private ImageView leader0;
    @FXML
    private ImageView leader1;
    @FXML
    private ImageView leader2;
    @FXML
    private ImageView leader3;

    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {

        int i = 0;
        for(int cardId : proposedLeaderCards){
            switch(i){
                case(0):
                    leader0.setImage(new Image("./images/leaderCardsFront/leader" + cardId + ".png"));
                    leader0.setVisible(true);
                    break;
                case(1):
                    leader1.setImage(new Image("./images/leaderCardsFront/leader" + cardId + ".png"));
                    leader1.setVisible(true);
                    break;
                case(2):
                    leader2.setImage(new Image("./images/leaderCardsFront/leader" + cardId + ".png"));
                    leader2.setVisible(true);
                    break;
                case(3):
                    leader3.setImage(new Image("./images/leaderCardsFront/leader" + cardId + ".png"));
                    leader3.setVisible(true);
                    break;
            }
            i++;
        }
    }

//-----------------------------------------------------------------------------------

    @Override
    public void visitBoardsUpdate(GUI view) {

    }

    @Override
    public void visitLorenzoUpdate(GUI view) {

    }

    @Override
    public void visitMarketUpdate(GUI view) {

    }

    @Override
    public void visitDevGridUpdate(GUI view) {

    }
//-------------------------------------------------------------------------------------
    @Override
    public void visitLobbyFull(String str) {

    }

    @Override
    public void visitLobbyNotReady(String str) {

    }

    @Override
    public void visitLoginSuccess(String currentPlayers) {

    }

    @Override
    public void visitRequestLobbySize(String str) {

    }

    @Override
    public void visitNicknameAlreadyUsed(String str, String gameID) {

    }
//--------------------------------------------------------------------------------------------------------
    @Override
    public void visitGameStarted(String str) {

    }

    @Override
    public void visitInitialResource(int quantity) {

    }

    @Override
    public void visitInkwell(String nickname) {

    }

    @Override
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {

    }

    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) {

    }

    @Override
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitLeaderAction(String currentPlayerNickname) {

    }

    @Override
    public void visitMainActionState(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {

    }

    @Override
    public void visitMarketState(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {

    }
}
