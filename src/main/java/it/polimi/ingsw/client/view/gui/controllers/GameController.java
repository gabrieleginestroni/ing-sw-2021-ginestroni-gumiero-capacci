package it.polimi.ingsw.client.view.gui.controllers;
import it.polimi.ingsw.client.view.BoardView;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button changeSceneButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BackgroundImage backgroundImage = new BackgroundImage(new Image("./images/table_background.jpg",1490.0,810.0,false,true),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));
        for(int i = 0; i < 3; i++) {
            ImageView otherPlayer = (ImageView) pane.lookup("#otherplayer_"+i);
            otherPlayer.setImage(new Image("./images/punchboard/boardBack.png"));
        }

        changeSceneButton.setOnMouseClicked(actionEvent -> Platform.runLater(()-> view.changeScene(view.scenesMap.get(GUI.DEVELOPMENT))));


    }


    @Override
    public void visitBoardsUpdate() {

        //updating player board
        BoardView player = view.getPersonalBoardView();
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
                    resImg.setImage(new Image("./images/resources/" + warehouseDepotResource.get(i).toLowerCase() + ".png"));
                    resImg.setVisible(true);
                } else
                    resImg.setVisible(false);
            }
        }

        // updating leader depot resource
        List<String> leaderDepotResource = player.getLeaderDepotResource();
        List<Integer> leaderDepotQuantity = player.getLeaderDepotQuantity();
        List<Integer> activeLeaders = player.getActiveLeaders();
        int offset = 0;
        if(activeLeaders != null) {
            for (int i = 0; i < 2; i++){
                if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("depots")){
                    for(int j = 0; j < 2; j++ ) {
                        ImageView resImg = (ImageView) pane.lookup("#player_leader_" + i + "_"+j);
                        if(j < leaderDepotQuantity.get(offset)){
                            resImg.setImage(new Image("./images/resources/" + leaderDepotResource.get(offset).toLowerCase() + ".png"));
                            resImg.setVisible(true);
                        } else
                            resImg.setVisible(false);
                    }
                    offset++;
                }
            }
        }


        //updating hidden hand
        for(int i = 0; i < 2; i++){
            ImageView leaderImg = (ImageView) pane.lookup("#player_hidden_"+i);
            if(i < player.getHiddenHand().size()){
                leaderImg.setImage(new Image("./images/leaderCardsFront/leader" + player.getHiddenHand().get(i) + ".png"));
                leaderImg.setVisible(true);
            } else
                leaderImg.setVisible(false);
        }

        //updating active hand
        for(int i = 0; i < 2; i++){
            ImageView leaderImg = (ImageView) pane.lookup("#player_leader_"+i);
            if(i < player.getActiveLeaders().size()){
                leaderImg.setImage(new Image("./images/leaderCardsFront/leader" + player.getActiveLeaders().get(i) + ".png"));
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
                    devImg.setImage(new Image("./images/developmentCardsFront/development" + cardslots[i].get(j) + ".png"));
                    devImg.setVisible(true);
                } else
                    devImg.setVisible(false);
           }
        }

        //updating other boards
        List<BoardView> otherPlayers = view.getOtherBoardsView();
        if(otherPlayers != null){
            int playerIndex = 0;
            for(BoardView otherPlayer:otherPlayers){

                //updating strongbox
                for(Map.Entry<String,Integer> strongbox: otherPlayer.getStrongBox().entrySet()){
                    Label label = (Label)pane.lookup("#otherplayer_"+playerIndex+"_"+strongbox.getKey().toLowerCase());
                    label.setText(strongbox.getValue().toString());
                }

                //updating warehouse
                warehouseDepotResource = otherPlayer.getWarehouseDepotResource();
                warehouseDepotQuantity = otherPlayer.getWarehouseDepotQuantity();
                for(int i = 0; i < 3; i++) {
                    for(int j = 0 ; j <= i; j++){
                        ImageView resImg =(ImageView) pane.lookup("#otherplayer_"+playerIndex+"_warehouse_"+i+"_"+j);
                        if(j < warehouseDepotQuantity.get(i)) {
                            resImg.setImage(new Image("./images/resources/" + warehouseDepotResource.get(i).toLowerCase() + ".png"));
                            resImg.setVisible(true);
                        } else
                            resImg.setVisible(false);
                    }
                }

                // updating leader depot resource
                 leaderDepotResource = otherPlayer.getLeaderDepotResource();
                 leaderDepotQuantity = otherPlayer.getLeaderDepotQuantity();
                 activeLeaders = otherPlayer.getActiveLeaders();
                 offset = 0;
                if(activeLeaders != null) {
                    for (int i = 0; i < 2; i++){
                        if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("depots")){
                            for(int j = 0; j < 2; j++ ) {
                                ImageView resImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_leader_" + i + "_"+j);
                                if(j < leaderDepotQuantity.get(offset)){
                                    resImg.setImage(new Image("./images/resources/" + leaderDepotResource.get(offset).toLowerCase() + ".png"));
                                    resImg.setVisible(true);
                                } else
                                    resImg.setVisible(false);
                            }
                            offset++;
                        }
                    }
                }

                //updating active hand
                for(int i = 0; i < 2; i++){
                    ImageView leaderImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_leader_"+i);
                    if(i < otherPlayer.getActiveLeaders().size()){
                        leaderImg.setImage(new Image("./images/leaderCardsFront/leader" + otherPlayer.getActiveLeaders().get(i) + ".png"));
                        leaderImg.setVisible(true);
                    } else
                        leaderImg.setVisible(false);
                }

                //updating faith
                for(int i = 1; i < 25; i++){
                    ImageView cellImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_faith_"+i);
                    if(i == otherPlayer.getFaithTrackMarker())
                        cellImg.setVisible(true);
                    else
                        cellImg.setVisible(false);
                }

                // updating pope tiles
                popes = otherPlayer.getPopeTiles();
                for(int i = 0; i < 3; i++){
                    ImageView popeImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_pope_"+i);
                    if(popes[i])
                        popeImg.setVisible(true);
                    else
                        popeImg.setVisible(false);
                }

                // updating card slots
                cardslots = otherPlayer.getCardSlot();
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        ImageView devImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_cardslot_"+i+"_"+j);
                        if(j < cardslots[i].size()){
                            devImg.setImage(new Image("./images/developmentCardsFront/development" + cardslots[i].get(j) + ".png"));
                            devImg.setVisible(true);
                        } else
                            devImg.setVisible(false);
                    }
                }
                playerIndex++;
            }
        }
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
            tokenImg.setImage(new Image("./images/punchboard/" + path + ".png"));
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
                marbleImg.setImage(new Image("./images/marbles/" + market[i][j].toLowerCase() + "Marble.png"));
            }
        }
        ImageView marbleImg = (ImageView) pane.lookup("#freemarble");
        marbleImg.setImage(new Image("./images/marbles/" + view.getMarketView().getFreeMarble().toLowerCase() + "Marble.png"));

    }

    @Override
    public void visitInkwell(String nickname) {

        List<BoardView> otherPlayers = view.getOtherBoardsView();
            int playerIndex = 0;
            ImageView inkwellImg;
        if (view.getNickname().equals(nickname)) {   //player gets inkwell
            inkwellImg = (ImageView) pane.lookup("#player_inkwell");
            inkwellImg.setVisible(true);
            if(otherPlayers != null) {
                for (BoardView otherPlayer : otherPlayers) {
                    inkwellImg = (ImageView) pane.lookup("#otherplayer_" + playerIndex + "_inkwell");
                    inkwellImg.setVisible(false);
                    playerIndex++;
                }
            }
        } else {                  //other player gets inkwell
            inkwellImg = (ImageView) pane.lookup("#player_inkwell");
            inkwellImg.setVisible(false);
            for (BoardView otherPlayer : otherPlayers) {
                inkwellImg = (ImageView) pane.lookup("#otherplayer_" + playerIndex + "_inkwell");
                if (otherPlayer.getNickname().equals(nickname))
                    inkwellImg.setVisible(true);
                else
                    inkwellImg.setVisible(false);
                playerIndex++;
            }
        }
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
        if (view.getOtherBoardsView() != null) {
            for (int i = 0; i < view.getOtherBoardsView().size(); i++) {
                ImageView otherPlayer = (ImageView) pane.lookup("#otherplayer_" + i);
                otherPlayer.setImage(new Image("./images/punchboard/boardFront.png"));
                StackPane otherPlayerPane = (StackPane) pane.lookup("#otherplayer_pane_" + i);
                otherPlayerPane.setVisible(true); //enabling other players images overlay

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
