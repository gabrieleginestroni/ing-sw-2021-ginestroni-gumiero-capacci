package it.polimi.ingsw.client.view.gui.controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.BoardView;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GridView;
import it.polimi.ingsw.client.view.MarketView;
import it.polimi.ingsw.server.model.Resource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;

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

        BackgroundImage backgroundImage = new BackgroundImage(new Image("./images/table_background.jpg",1490.0,810.0,false,true),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));
        //StackPane player = (StackPane) pane.lookup("#player");
        //player.getChildren().add(new ImageView(new Image("./images/punchboard/boardFront.png",698.0,497.0,true,true)));
        for(int i = 0; i < 3; i++) {
            ImageView otherPlayer = (ImageView) pane.lookup("#otherplayer_"+i);
            otherPlayer.setImage(new Image("./images/punchboard/boardFront.png", 372.0, 265.0, true, true));
        }

        //otherPlayer.getChildren().removeAll();
        //otherPlayer.getChildren().add(new ImageView(new Image("./images/punchboard/boardFront.png", 372.0, 265.0, true, true)));
        ImageView img = (ImageView)pane.lookup("#otherplayer_0_warehouse_0_0");
        img.setImage(new Image("./images/resources/stone.png",14.0,17.0,false,false));




    }


    @Override
    public void visitBoardsUpdate() {
        //updating player board
        BoardView player = view.getPersonalBoardView();
        List<BoardView> otherPlayer = view.getOtherBoardsView();
        //updating strongbox
        for(Map.Entry<String,Integer> strongbox: player.getStrongBox().entrySet()){
            Label label = (Label)pane.lookup("#player_"+strongbox.getKey().toLowerCase());
            label.setText(strongbox.getValue().toString());
        }
        //updating warehouse
        List<String> warehouseDepotResource = player.getWarehouseDepotResource();
        List<Integer> warehouseDepotQuantity = player.getWarehouseDepotQuantity();
        for(int i = 0; i < 3; i++) {
            for(int j = 0 ; j <= i; j++){
                ImageView resImg =(ImageView) pane.lookup("#player_warehouse_"+i+"_"+j);
                if(j < warehouseDepotQuantity.get(i)) {
                    resImg.setImage(new Image("./images/resources/" + warehouseDepotResource.get(i).toLowerCase() + ".png", 32, 26, true, true));
                    resImg.setVisible(true);
                } else
                    resImg.setVisible(false);
            }
        }

        //TODO leader depots

        //updating hidden hand
        for(int i = 0; i < 2; i++){
            ImageView leaderImg = (ImageView) pane.lookup("#player_hidden_"+i);
            if(i < player.getHiddenHand().size()){
                leaderImg.setImage(new Image("./images/leaderCardsFront/leader" + player.getHiddenHand().get(i) + ".png", 126, 193, false, true));
                leaderImg.setVisible(true);
            } else
                leaderImg.setVisible(false);
        }
        //updating active hand
        for(int i = 0; i < 2; i++){
            ImageView leaderImg = (ImageView) pane.lookup("#player_leader_"+i);
            if(i < player.getActiveLeaders().size()){
                leaderImg.setImage(new Image("./images/leaderCardsFront/leader" + player.getActiveLeaders().get(i) + ".png", 126, 193, false, true));
                leaderImg.setVisible(true);
            } else
                leaderImg.setVisible(false);
        }
        //updating faith
        for(int i = 1; i < 25; i++){
            ImageView cellImg = (ImageView) pane.lookup("#player_faith_"+i);
            if(i == player.getFaithTrackMarker())
                cellImg.setVisible(true);
            else
                cellImg.setVisible(false);
        }
        // updating pope tiles
        boolean[] popes = player.getPopeTiles();
        for(int i = 0; i < 3; i++){
            ImageView popeImg = (ImageView) pane.lookup("#player_pope_"+i);
            if(popes[i])
                popeImg.setVisible(true);
            else
                popeImg.setVisible(false);
        }
        // updating card slots
        ArrayList<Integer>[] cardslots = player.getCardSlot();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                ImageView devImg = (ImageView) pane.lookup("#player_cardslot_"+i+"_"+j);
                if(j < cardslots[i].size()){
                    devImg.setImage(new Image("./images/developmentCardsFront/development" + cardslots[i].get(j) + ".png", 125, 237, true, true));
                    devImg.setVisible(true);
                } else
                    devImg.setVisible(false);
           }
        }
        //TODO other boards
    }

    @Override
    public void visitLorenzoUpdate() {
        for(int i = 1; i < 25; i++){
            ImageView cellImg = (ImageView) pane.lookup("#lorenzo_faith_"+i);
            if(i == view.getLorenzoView().getBlackCrossMarker())
                cellImg.setVisible(true);
            else
                cellImg.setVisible(false);
        }
        String token = view.getLorenzoView().getLastDrawnActionToken();
        ImageView tokenImg = (ImageView) pane.lookup("#lorenzo_token");
        if(token != null) {
            String path = "shuffleActionToken";

            if (token.contains("Added"))
                path = "add2BlackCross";
            else if (token.contains("Blue"))
                path = "discard2Blue";
            else if (token.contains("Green"))
                path = "discard2Green";
            else if (token.contains("Purple"))
                path = "discard2Purple";
            else if (token.contains("Yellow"))
                path = "discard2Yellow";
            tokenImg.setImage(new Image("./images/punchboard/" + path + ".png", 45, 45, true, true));
            tokenImg.setVisible(true);
        } else
            tokenImg.setVisible(false);
    }

    @Override
    public void visitMarketUpdate() {
        String[][] market = view.getMarketView().getMarket();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                ImageView marbleImg = (ImageView) pane.lookup("#marble_"+i+"_"+j);
                marbleImg.setImage(new Image("./images/marbles/" + market[i][j].toLowerCase() + "Marble.png", 38, 34, true, true));
            }
        }
        ImageView marbleImg = (ImageView) pane.lookup("#freemarble");
        marbleImg.setImage(new Image("./images/marbles/" + view.getMarketView().getFreeMarble().toLowerCase() + "Marble.png", 38, 34, true, true));

    }

    @Override
    public void visitDevGridUpdate() {

    }

    @Override
    public void visitInkwell(String nickname) {

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
