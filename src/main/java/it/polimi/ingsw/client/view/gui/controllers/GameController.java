package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GridView;
import it.polimi.ingsw.server.model.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class GameController extends GUIController implements Initializable {

    @FXML
    private GridPane market;
    @FXML
    private Pane pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO finalize
        int row=0;
        int col=0;
        ImageView marble =(ImageView) market.lookup("#marble_"+row+"_"+col);
        marble.setImage(new Image("./images/warehouse.png"));

        BackgroundImage backgroundImage = new BackgroundImage(new Image("./images/table_background.jpg",1490.0,810.0,false,true),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));
        StackPane player = (StackPane) pane.lookup("#player");
        player.getChildren().add(new ImageView(new Image("./images/punchboard/boardFront.png",698.0,497.0,true,true)));
        for(int i = 0; i < 3; i++) {
            StackPane otherPlayer = (StackPane) pane.lookup("#otherplayer_"+i);
            otherPlayer.getChildren().add(new ImageView(new Image("./images/punchboard/boardBack.png", 372.0, 265.0, true, true)));
        }


    }


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
        if (view.getOtherBoardsView() != null) {
            for (int i = 0; i < view.getOtherBoardsView().size(); i++) {
                StackPane otherPlayer = (StackPane) pane.lookup("#otherplayer_" + i);
                otherPlayer.getChildren().removeAll();
                otherPlayer.getChildren().add(new ImageView(new Image("./images/punchboard/boardFront.png", 372.0, 265.0, true, true)));
            }
        }
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
