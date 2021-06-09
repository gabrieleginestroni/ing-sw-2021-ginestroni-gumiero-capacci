package it.polimi.ingsw.client.view.gui.controllers;
import it.polimi.ingsw.client.view.BoardView;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class GameController extends GUIController implements Initializable {

    @FXML
    private GridPane market;
    @FXML
    private Pane pane;
    @FXML
    private Button changeSceneButton;
    @FXML
    private Rectangle popUpEffect;
    @FXML
    private StackPane popUp;
    @FXML
    private Button leftButton;
    @FXML
    private Button centerButton;
    @FXML
    private Button rightButton;
    @FXML
    private Label textMessage;
    @FXML
    private Label popUpTextMessage;
    @FXML
    private Button sendButton;
    @FXML
    private Label leaderAction_0;
    @FXML
    private Label leaderAction_1;
    @FXML
    private ImageView res0;
    @FXML
    private ImageView res1;
    @FXML
    private Button res0Button;
    @FXML
    private Button res1Button;

    private final Map<Integer, Integer> leaderMap = new HashMap<>();

    private void sendMarketMessage(int move, int index){
        networkHandler.sendMessage(new ChosenMarketMoveMessage(move,index));
        this.disableMarketButtons();
    }

    private void disableMarketButtons(){
        for(int i = 0; i <= 1; i++)
            for(int j = 0; j <= (i == 0 ? 2 : 3); j++)
                ((Button) pane.lookup("#market_" + i + "_" + j)).setDisable(true);
    }

    private void setLeaderAction(int index, int move){
        leaderMap.put(index, move);
        if(move == 1)
            ((Label) pane.lookup("#leaderAction_" + index)).setText("Activate");
        else
            ((Label) pane.lookup("#leaderAction_" + index)).setText("Discard");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BackgroundImage backgroundImage = new BackgroundImage(new Image("./images/table_background.jpg",1490.0,810.0,false,true),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        pane.setBackground(new Background(backgroundImage));

        textMessage.setLineSpacing(-10);
        popUpTextMessage.setLineSpacing(-10);

        for(int i = 0; i <= 1; i++){
            for(int j = 0; j <= (i == 0 ? 2 : 3); j++){
                int move = i;
                int index = j;
                ((Button) pane.lookup("#market_" + i + "_" + j)).setOnAction(actionEvent -> sendMarketMessage(move, index) );
            }
        }

        for(int i = 0; i <= 1; i++){
            int index = i;
            ((Button) pane.lookup("#discard_" + i)).setOnAction(actionEvent -> setLeaderAction(index, 2));
            ((Button) pane.lookup("#activate_" + i)).setOnAction(actionEvent -> setLeaderAction(index, 1));
        }

        for(int i = 0; i < 3; i++) {
            ImageView otherPlayer = (ImageView) pane.lookup("#otherplayer_"+i);
            otherPlayer.setImage(GUI.punchBoardImg.get("boardBack"));
        }

        changeSceneButton.setOnAction(actionEvent -> Platform.runLater(()-> view.changeScene(view.scenesMap.get(GUI.DEVELOPMENT))));

    }

//----------------------------------UPDATE---------------------------------------------------------------------
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
                    resImg.setImage(null);
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
                            resImg.setImage(null);
                    }
                    offset++;
                }
            }
        }


        //updating hidden hand
        for(int i = 0; i < 2; i++){
            ImageView leaderImg = (ImageView) pane.lookup("#player_hidden_"+i);
            if(i < player.getHiddenHand().size()){
                leaderImg.setImage(GUI.leaderCardImg[player.getHiddenHand().get(i)]);
                leaderImg.setVisible(true);
            } else
                leaderImg.setImage(null);
        }

        //updating active hand
        for(int i = 0; i < 2; i++){
            ImageView leaderImg = (ImageView) pane.lookup("#player_leader_"+i);
            if(i < player.getActiveLeaders().size()){
                leaderImg.setImage(GUI.leaderCardImg[player.getActiveLeaders().get(i)]);
                leaderImg.setVisible(true);
            } else
                leaderImg.setImage(null);
        }

        //updating faith
        for(int i = 1; i < 25; i++){
            ImageView cellImg = (ImageView) pane.lookup("#player_faith_"+i);
            if(i == player.getFaithTrackMarker()) {
                cellImg.setImage(GUI.punchBoardImg.get("faith"));
                cellImg.setVisible(true);
            }
            else
                cellImg.setImage(null);
        }

        // updating pope tiles
        boolean[] popes = player.getPopeTiles();
        for(int i = 0; i < 3; i++){
            ImageView popeImg = (ImageView) pane.lookup("#player_pope_"+i);
            popeImg.setVisible(popes[i]);
        }

        // updating card slots
        ArrayList<Integer>[] cardslots = player.getCardSlot();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                ImageView devImg = (ImageView) pane.lookup("#player_cardslot_"+i+"_"+j);
                if(j < cardslots[i].size()){
                    devImg.setImage(GUI.developmentCardImg[cardslots[i].get(j)]);
                    devImg.setVisible(true);
                } else
                    devImg.setImage(null);
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
                            resImg.setImage(null);
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
                                    resImg.setImage(null);
                            }
                            offset++;
                        }
                    }
                }

                //updating active hand
                for(int i = 0; i < 2; i++){
                    ImageView leaderImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_leader_"+i);
                    if(i < otherPlayer.getActiveLeaders().size()){
                        leaderImg.setImage(GUI.leaderCardImg[otherPlayer.getActiveLeaders().get(i)]);
                        leaderImg.setVisible(true);
                    } else
                        leaderImg.setImage(null);
                }

                //updating faith
                for(int i = 1; i < 25; i++){
                    ImageView cellImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_faith_"+i);
                    if(i == otherPlayer.getFaithTrackMarker()) {
                        cellImg.setImage(GUI.punchBoardImg.get("faith"));
                        cellImg.setVisible(true);
                    } else
                        cellImg.setImage(null);
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
                            devImg.setImage(GUI.developmentCardImg[cardslots[i].get(j)]);
                            devImg.setVisible(true);
                        } else
                            devImg.setImage(null);
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
            if(i == view.getLorenzoView().getBlackCrossMarker()) {
                cellImg.setImage(GUI.punchBoardImg.get("blackCross"));
                cellImg.setVisible(true);
            } else
                cellImg.setImage(null);
        }
        String token = view.getLorenzoView().getLastDrawnActionToken();
        ImageView tokenImg = (ImageView) pane.lookup("#lorenzo_token");
        if(token != null) {
            String action = "shuffleActionToken";

            if (token.contains("Added"))
                action = "add2BlackCross";
            else if (token.contains("Blue"))
                action = "discard2Blue";
            else if (token.contains("Green"))
                action = "discard2Green";
            else if (token.contains("Purple"))
                action = "discard2Purple";
            else if (token.contains("Yellow"))
                action = "discard2Yellow";
            tokenImg.setImage(GUI.punchBoardImg.get(action));
            tokenImg.setVisible(true);
        } else
            tokenImg.setImage(null);
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

//--------------------------GAME PHASES-----------------------------------------------------------------------------
    @Override
    public void visitGameStarted(String str) {
        if (view.getOtherBoardsView() != null) {
            for (int i = 0; i < view.getOtherBoardsView().size(); i++) {
                ImageView otherPlayer = (ImageView) pane.lookup("#otherplayer_" + i);
                otherPlayer.setImage(GUI.punchBoardImg.get("boardFront"));
                StackPane otherPlayerPane = (StackPane) pane.lookup("#otherplayer_pane_" + i);
                otherPlayerPane.setVisible(true); //enabling other players images overlay

            }
        }
    }

    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {
        if(currentPlayerNickname.equals(view.getNickname())) {
            popUpEffect.setVisible(true);
            textMessage.setText("");

            //TODO check if works
            leaderMap.put(0, 0);
            leaderMap.put(1, 0);

            String str = errorMessage == null? "" : errorMessage + "\n";
            popUpTextMessage.setText(str + "Choose an action");

            leftButton.setText("Main");
            leftButton.setVisible(true);
            leftButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenFirstMoveMessage(0)));
            centerButton.setVisible(false);
            rightButton.setText("Leader");
            rightButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenFirstMoveMessage(1)));
            rightButton.setVisible(true);
            popUp.setVisible(true);

        } else
            textMessage.setText(currentPlayerNickname + " is choosing an action");
    }

    @Override
    public void visitMainActionState(String currentPlayerNickname, String errorMessage) {
        if(currentPlayerNickname.equals(view.getNickname())) {
            popUpEffect.setVisible(true);

            String str = errorMessage == null? "" : errorMessage + "\n";
            popUpTextMessage.setText(str + "Choose a main action");

            leftButton.setText("Market");
            leftButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenMainMoveMessage(0)));
            leftButton.setVisible(true);

            centerButton.setText("Purchase Card");
            centerButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenMainMoveMessage(1)));
            centerButton.setVisible(true);

            rightButton.setText("Production");
            rightButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenMainMoveMessage(2)));
            rightButton.setVisible(true);

            popUp.setVisible(true);
        } else
            textMessage.setText(currentPlayerNickname + " is choosing main action");
    }

    @Override
    public void visitMarketState(String currentPlayerNickname, String errorMessage) {
        if(currentPlayerNickname.equals(view.getNickname())){
            popUpEffect.setVisible(false);
            popUp.setVisible(false);

            String str = errorMessage == null? "" : errorMessage + "\n";
            textMessage.setText(str + "Please choose a market move");

            for (int i = 0; i <= 1; i++)
                for (int j = 0; j <= (i == 0 ? 2 : 3); j++)
                    pane.lookup("#market_" + i + "_" + j).setDisable(false);

        }else
            textMessage.setText(currentPlayerNickname + " is choosing a market move");
    }

    @Override
    public void visitLeaderAction(String currentPlayerNickname) {
        if(currentPlayerNickname.equals(view.getNickname())){
            List<Integer> hiddenHand = view.getPersonalBoardView().getHiddenHand();

            popUpEffect.setVisible(false);
            popUp.setVisible(false);

            sendButton.setDisable(false);
            sendButton.setOnAction(actionEvent -> {
                networkHandler.sendMessage(new ChosenLeaderActionMessage(leaderMap));

                int i = 0;
                for(Integer cardId : hiddenHand){
                    pane.lookup("#discard_" + i).setVisible(false);
                    pane.lookup("#activate_" + i).setVisible(false);
                    i++;
                }

                leaderAction_0.setText("");
                leaderAction_1.setText("");

                sendButton.setDisable(true);
            });

            int i = 0;
            for(Integer cardId : hiddenHand){
                pane.lookup("#discard_" + i).setVisible(true);
                pane.lookup("#activate_" + i).setVisible(true);
                i++;
            }
        }else
            textMessage.setText(currentPlayerNickname + " is doing a leader action");
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
    public void visitInitialResource(int quantity) {

    }


    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {

    }

    @Override
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {

            popUpEffect.setVisible(true);

            String str = "";
            popUpTextMessage.setText(str + "Choose which white marble power you want to use");

            leftButton.setVisible(false);
            centerButton.setVisible(false);
            rightButton.setVisible(false);

            res0Button.setOnAction(actionEvent -> {
                networkHandler.sendMessage(new ChosenWhiteMarbleMessage(res1));
                this.res0.setVisible(false);
                this.res0Button.setDisable(true);
            });
            res1Button.setOnAction(actionEvent -> {
                networkHandler.sendMessage(new ChosenWhiteMarbleMessage(res2));
                this.res1.setVisible(false);
                this.res1Button.setDisable(true);
            });
            res0Button.setDisable(false);
            res1Button.setDisable(false);
            this.res0.setImage(new Image("./images/resources/" + res1.toString().toLowerCase() + ".png"));
            this.res1.setImage(new Image("./images/resources/" + res2.toString().toLowerCase() + ".png"));
            this.res0.setVisible(true);
            this.res1.setVisible(true);

            popUp.setVisible(true);

    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) {

    }

    @Override
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {

    }

    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {

    }

    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {

    }


}
