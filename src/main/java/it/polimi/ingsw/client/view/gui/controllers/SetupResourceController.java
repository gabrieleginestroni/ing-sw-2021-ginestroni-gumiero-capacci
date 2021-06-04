package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SetupResourceController extends GUIController implements Initializable {
    @FXML
    private Rectangle rectangle;
    @FXML
    private StackPane popUp;
    @FXML
    private Button depot0;
    @FXML
    private Button depot1;
    @FXML
    private Button depot2;
    @FXML
    private Label message;

    private int requestedQty;
    private int chosenQty;

    private void chooseDepot(int ind){

        popUp.setBackground(new Background(new BackgroundImage(new Image("./images/login_background.png", 350, 180, true, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        popUp.setEffect(new DropShadow(20, Color.BLACK));
        popUp.setVisible(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestedQty = 0;
        chosenQty = 0;

        rectangle.setArcWidth(30.0);
        rectangle.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(
                new Image("./images/warehouse.png", 350, 360, true, true)
        );

        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.BLACK));
        rectangle.setVisible(true);
    }

    @Override
    public void visitInitialResource(int quantity) {
        String s = quantity > 1? "s" : "";
        message.setText("Choose "+ quantity +" resource" + s + " and the depot where to store it");

        requestedQty = quantity;

        depot0.setOnAction(ActionEvent -> Platform.runLater(()-> chooseDepot(0)));
        depot1.setOnAction(ActionEvent -> Platform.runLater(()-> chooseDepot(1)));
        depot2.setOnAction(ActionEvent -> Platform.runLater(()-> chooseDepot(2)));
    }

//-----------------------------------------------------------------

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
