package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.model.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SetupResourceController extends GUIController implements Initializable {
    @FXML
    private Rectangle rectangle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rectangle.setArcWidth(30.0);   // Corner radius
        rectangle.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(
                new Image("./images/warehouse.png", 350, 360, false, false) // Resizing
        );

        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.BLACK));  // Shadow
        rectangle.setVisible(true);
    }

    @Override
    public void visitInitialResource(int quantity) {

    }

//-----------------------------------------------------------------

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
