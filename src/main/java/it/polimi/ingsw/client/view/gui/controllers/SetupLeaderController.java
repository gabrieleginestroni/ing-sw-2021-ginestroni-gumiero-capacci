package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.messages.client_server.ChosenLeaderMessage;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.List;

public class SetupLeaderController extends GUIController {
    @FXML
    private StackPane leader0;
    @FXML
    private StackPane leader1;
    @FXML
    private StackPane leader2;
    @FXML
    private StackPane leader3;
    @FXML
    private Label leaderText;
    @FXML
    private Button sendLeader;

    private final List<Integer> chosenLeader = new ArrayList<>();

    public void sendLeaderMessage(){
        Platform.runLater(()-> {
            int[] arr = new int[2];
            int i = 0;
            for (Integer leader : chosenLeader) {
                arr[i] = leader;
                i++;
            }
            this.networkHandler.sendMessage(new ChosenLeaderMessage(arr));
            if(view.getOtherBoardsView() != null)
                view.changeScene(view.scenesMap.get(GUI.SETUP_RESOURCE));
            else
                view.changeScene(view.scenesMap.get(GUI.MAIN_GUI));
        });
    }

    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {

        setLeaderImage(proposedLeaderCards[0], leader0);
        setLeaderImage(proposedLeaderCards[1], leader1);
        setLeaderImage(proposedLeaderCards[2], leader2);
        setLeaderImage(proposedLeaderCards[3], leader3);

        leader0.setOnMouseClicked(ActionEvent -> setChosenLeader(0));
        leader1.setOnMouseClicked(ActionEvent -> setChosenLeader(1));
        leader2.setOnMouseClicked(ActionEvent -> setChosenLeader(2));
        leader3.setOnMouseClicked(ActionEvent -> setChosenLeader(3));

    }

    public void setLeaderImage(int cardId, StackPane pane){
        ImageView img = new ImageView(new Image("./images/leaderCardsFront/leader" + cardId + ".png", 170.0, 260.0, false, true));
        pane.getChildren().add(img);
    }

    private void setChosenLeader(int i){
        if(chosenLeader.size() == 0 || i != chosenLeader.get(chosenLeader.size()-1))
            chosenLeader.add(i);
        if(chosenLeader.size() >= 2) {
            sendLeader.setDisable(false);
            if(chosenLeader.size() > 2)
                chosenLeader.remove(0);
        }
        StringBuilder txt = new StringBuilder("You chose: \n ");
        for(Integer leader: chosenLeader){
            txt.append(leader);
            txt.append(" ");
        }
        leaderText.setText(String.valueOf(txt));
        leaderText.setVisible(true);
    }

//-----------------------------------------------------------------------------------

    @Override
    public void visitBoardsUpdate() {

    }

    @Override
    public void visitLorenzoUpdate() {

    }

    @Override
    public void visitMarketUpdate() {

    }

    @Override
    public void visitDevGridUpdate() {

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
