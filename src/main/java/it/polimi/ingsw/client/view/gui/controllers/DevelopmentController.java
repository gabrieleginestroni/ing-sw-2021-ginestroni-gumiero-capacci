package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;



public class DevelopmentController extends GUIController implements Initializable {

    @FXML
    private Button changeSceneButton;
    @FXML
    private Button buyButton;
    @FXML
    private BorderPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("./images/table_background.jpg",1490.0,810.0,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

        changeSceneButton.setOnAction( actionEvent -> Platform.runLater(()-> view.changeScene(view.scenesMap.get(GUI.MAIN_GUI))));

    }

    @Override
    public void visitDevGridUpdate() {
        int[][] devGrid = view.getDevGridView().getGrid();
        StackPane devPane;
        ImageView devImg;
        for(int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 4; j++) {
                 devPane = (StackPane) pane.lookup("#dev_"+i+"_"+j);
                 devImg = (ImageView) devPane.getChildren().get(0);
                 if(devGrid[i][j] != 0){  //0 means that no cards a remaining in that slot
                     devImg.setImage(new Image("./images/developmentCardsFront/development"+devGrid[i][j]+".png"));
                 } else
                     devImg.setImage(new Image("./images/devGridEmptySlot.png"));
            }
        }
    }

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
    public void visitLeaderProposal(int[] proposedLeaderCards) {

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
