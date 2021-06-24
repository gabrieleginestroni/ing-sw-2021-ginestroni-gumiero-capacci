package it.polimi.ingsw.client.view.gui.controllers;
import com.google.gson.Gson;
import it.polimi.ingsw.client.view.BoardView;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
    private ImageView res2;
    @FXML
    private ImageView res3;
    @FXML
    private Button res0Button;
    @FXML
    private Button res1Button;
    @FXML
    private Button res2Button;
    @FXML
    private Button res3Button;
    @FXML
    private Button leaderButton_0;
    @FXML
    private Button leaderButton_1;
    @FXML
    private Button warehouseButton_0;
    @FXML
    private Button warehouseButton_1;
    @FXML
    private Button warehouseButton_2;
    @FXML
    private Button strongboxButton_coin;
    @FXML
    private Button strongboxButton_servant;
    @FXML
    private Button strongboxButton_shield;
    @FXML
    private Button strongboxButton_stone;
    @FXML
    private Button strongboxButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button cardslot_0;
    @FXML
    private Button cardslot_1;
    @FXML
    private Button cardslot_2;

    private int connectedOtherPlayersNumber;
    private List<Integer> depotToSwap = new ArrayList<>();
    private Map<Integer, Integer> leaderMap;
    private Map<Integer, Integer> wareHouseMap;
    private Map<Resource, Integer> strongBoxMap;
    private Resource chosenResource;
    private double popupX;
    private double popupY;

    private void sendMarketMessage(int move, int index){
        networkHandler.sendMessage(new ChosenMarketMoveMessage(move,index));
        this.disableMarketButtons();
    }
    private void setAllCardSlotButtonsDisable(boolean boo){
        for(int i = 0; i <= 2; i++)
            ((Button) pane.lookup("#cardslot_"+i)).setDisable(boo);
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


    private void setChosenResource(Resource res){
        chosenResource = res;
        this.res0.setVisible(false);
        this.res1.setVisible(false);
        this.res2.setVisible(false);
        this.res3.setVisible(false);
        this.res0Button.setDisable(true);
        this.res1Button.setDisable(true);
        this.res2Button.setDisable(true);
        this.res3Button.setDisable(true);
        popUpEffect.setVisible(false);
        popUp.setVisible(false);
    }

    private void enablePickOutputProduction() {
        popUpEffect.setVisible(true);

        String str = "";
        popUpTextMessage.setText(str + "Choose resource to produce");

        leftButton.setVisible(false);
        centerButton.setVisible(false);
        rightButton.setVisible(false);
        rightButton.setDisable(false);

        res0Button.setOnAction(actionEvent -> setChosenResource(Resource.COIN));
        res1Button.setOnAction(actionEvent -> setChosenResource(Resource.STONE));
        res2Button.setOnAction(actionEvent -> setChosenResource(Resource.SHIELD));
        res3Button.setOnAction(actionEvent -> setChosenResource(Resource.SERVANT));

        res0Button.setDisable(false);
        res1Button.setDisable(false);
        res2Button.setDisable(false);
        res3Button.setDisable(false);
        res0Button.setVisible(true);
        res1Button.setVisible(true);
        res2Button.setVisible(true);
        res3Button.setVisible(true);
        this.res0.setImage(new Image("/images/resources/coin.png"));
        this.res1.setImage(new Image("/images/resources/stone.png"));
        this.res2.setImage(new Image("/images/resources/shield.png"));
        this.res3.setImage(new Image("/images/resources/servant.png"));
        this.res0.setVisible(true);
        this.res1.setVisible(true);
        this.res2.setVisible(true);
        this.res3.setVisible(true);

        popUp.setVisible(true);
    }

    private void enablePickResourceForProduction() {
        for (int i = 0; i <= 2; i++) {
            int index = i;
            Button depotButton = (Button) pane.lookup("#warehouseButton_" + i);
            if (view.getPersonalBoardView().getWarehouseDepotQuantity().get(index) > 0) {
                depotButton.setOnAction(actionEvent -> {
                    wareHouseMap.merge(index, 1, Integer::sum);
                    printChosenResource(getResourceToPickForProduction());
                });
                depotButton.setDisable(false);
            }
        }

        List<Integer> activeLeaders = view.getPersonalBoardView().getActiveLeaders();
        int leaderOffset = 0;
        for (int i = 0; i <= 1; i++) {
            Button leaderBtn = (Button) pane.lookup("#leaderButton_" + i);
            int finalLeaderOffset = 3 + leaderOffset;
            if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("depots")) {
                if (view.getPersonalBoardView().getLeaderDepotQuantity().get(leaderOffset) > 0) {
                    leaderBtn.setOnAction(actionEvent -> {
                        wareHouseMap.merge(finalLeaderOffset, 1, Integer::sum);
                        printChosenResource(getResourceToPickForProduction());
                    });
                    leaderBtn.setDisable(false);
                }
                leaderOffset++;
            }
        }

        List<String> resources = new ArrayList<>(Arrays.asList("coin", "servant", "stone", "shield"));
        for (String resStr : resources) {
            if (view.getPersonalBoardView().getStrongBox().get(resStr.toUpperCase()) > 0) {
                Button strongboxBtn = (Button) pane.lookup("#strongboxButton_" + resStr);
                strongboxBtn.setOnAction(actionEvent -> {
                    Resource res = Resource.valueOf(resStr.toUpperCase());
                    strongBoxMap.merge(res, 1, Integer::sum);
                    printChosenResource(getResourceToPickForProduction());
                });
                strongboxBtn.setDisable(false);
            }
        }
    }

    private int getTotalSelectedResources(Resource res) {
        int tot = 0;
        for (int i = 0; i < 3; i++){
            if (!view.getPersonalBoardView().getWarehouseDepotResource().get(i).equals("NULL") && Resource.valueOf(view.getPersonalBoardView().getWarehouseDepotResource().get(i)) == res) {
                tot += (wareHouseMap != null && wareHouseMap.get(i) != null) ? wareHouseMap.get(i) : 0;
                tot += (resToRemove != null && resToRemove.get(i) != null && resToRemove.get(i).get(res) != null) ? resToRemove.get(i).get(res) : 0;
            }
        }

        for(int i = 0; i < 2; i++) {
            if (!view.getPersonalBoardView().getLeaderDepotResource().get(i).equals("NULL") && Resource.valueOf(view.getPersonalBoardView().getLeaderDepotResource().get(i)) == res) {
                tot += (wareHouseMap != null && wareHouseMap.get(3 + i) != null) ? wareHouseMap.get(3 + i) : 0;
                tot += (resToRemove != null && resToRemove.get(3 + i) != null && resToRemove.get(3 + i).get(res) != null) ? resToRemove.get(3 + i).get(res) : 0;
            }
        }
        tot += (strongBoxMap != null && strongBoxMap.get(res) != null) ? strongBoxMap.get(res) : 0;
        tot += (resToRemove != null && resToRemove.get(5) != null && resToRemove.get(5).get(res) != null) ? resToRemove.get(5).get(res) : 0;

        return tot;
    }

    private Map<Resource, Integer> getResourceToPickForProduction(){
        if(0 <= chosenCardSlot && chosenCardSlot <= 2)
            return view.getDevelopmentCardByID(view.getPersonalBoardView().getTopCardSlot(chosenCardSlot)).getProductionInput();
        else if(chosenCardSlot < 5)
            return new HashMap<>(){{put(view.getLeaderCardByID(view.getPersonalBoardView().getActiveLeaders().get(chosenCardSlot - 3)).getResource(), 1);}};
        return new HashMap<>();
    }

    private void printChosenResource(Map<Resource, Integer> tmpMap) {
        String str = "Resource to choose:\n";
        if(chosenCardSlot == 5){
            int tot = 0;
            for(int i = 0; i < Resource.values().length-2; i++)
                tot += getTotalSelectedResources(Resource.values()[i]);
            str += "TOT: "+ tot + "/2\n";
        }else
            for (Map.Entry<Resource, Integer> entry : tmpMap.entrySet())
                str += entry.getKey() + ": " + getTotalSelectedResources(entry.getKey()) + "/" + entry.getValue() + "\n";
        textMessage.setText(textMessage.getText().split("\n")[0] + "\nChosen card slot: " + chosenCardSlot + "\n" + str);
        if(chosenCardSlot > 2 && chosenResource != null)
            textMessage.setText(textMessage.getText()+"\nChosen resource to produce: "+chosenResource.toString());
        //textMessage.setText("Chosen card slot: " + chosenCardSlot + "\n" + str);
        //textMessage.setText(textMessage.getText().split("\n")[0] + "\n Chosen card slot: " + chosenCardSlot + "\nR2R:" + new Gson().toJson(resToRemove)+ "\nW:" + new Gson().toJson(wareHouseMap) + "\nS:" + new Gson().toJson(strongBoxMap));
        System.out.println(textMessage.getText());
    }

    private void disableAllDepotButtons(){
        warehouseButton_0.setDisable(true);
        warehouseButton_1.setDisable(true);
        warehouseButton_2.setDisable(true);
        strongboxButton_coin.setDisable(true);
        strongboxButton_servant.setDisable(true);
        strongboxButton_shield.setDisable(true);
        strongboxButton_stone.setDisable(true);
        leaderButton_0.setDisable(true);
        leaderButton_1.setDisable(true);
    }

    private void updateOtherPlayer(int playerIndex, BoardView otherPlayer){

        Label label = (Label) pane.lookup("#otherplayer_"+playerIndex+"_nickname");
        label.setText(otherPlayer.getNickname());

        //updating strongbox
        for(Map.Entry<String,Integer> strongbox: otherPlayer.getStrongBox().entrySet()){
            label = (Label) pane.lookup("#otherplayer_"+playerIndex+"_"+strongbox.getKey().toLowerCase());
            label.setText(strongbox.getValue().toString());
        }

        //updating warehouse
        List<String> warehouseDepotResource = otherPlayer.getWarehouseDepotResource();
        List<Integer> warehouseDepotQuantity = otherPlayer.getWarehouseDepotQuantity();
        for(int i = 0; i < 3; i++) {
            for(int j = 0 ; j <= i; j++){
                ImageView resImg =(ImageView) pane.lookup("#otherplayer_"+playerIndex+"_warehouse_"+i+"_"+j);
                if(j < warehouseDepotQuantity.get(i)) {
                    resImg.setImage(new Image("/images/resources/" + warehouseDepotResource.get(i).toLowerCase() + ".png"));
                    resImg.setVisible(true);
                } else
                    resImg.setImage(null);
            }
        }

        // updating leader depot resource
        List<String> leaderDepotResource = otherPlayer.getLeaderDepotResource();
        List<Integer> leaderDepotQuantity = otherPlayer.getLeaderDepotQuantity();
        List<Integer> activeLeaders = otherPlayer.getActiveLeaders();
        int offset = 0;
        if(activeLeaders != null) {
            for (int i = 0; i < 2; i++){
                if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("depots")){
                    for(int j = 0; j < 2; j++ ) {
                        ImageView resImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_leader_" + i + "_"+j);
                        if(j < leaderDepotQuantity.get(offset)){
                            resImg.setImage(new Image("/images/resources/" + leaderDepotResource.get(offset).toLowerCase() + ".png"));
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
        boolean[] popes = otherPlayer.getPopeTiles();
        for(int i = 0; i < 3; i++){
            ImageView popeImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_pope_"+i);
            if(popes[i])
                popeImg.setVisible(true);
            else
                popeImg.setVisible(false);
        }

        // updating card slots
        ArrayList<Integer>[] cardslots = otherPlayer.getCardSlot();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                ImageView devImg = (ImageView) pane.lookup("#otherplayer_"+playerIndex+"_cardslot_"+i+"_"+j);
                if(j < cardslots[i].size()){
                    devImg.setImage(GUI.developmentCardImgSmall[cardslots[i].get(j)]);
                    devImg.setVisible(true);
                } else
                    devImg.setImage(null);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/table_background.jpg",1490.0,810.0,false,true),BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        pane.setBackground(new Background(backgroundImage));

        textMessage.setLineSpacing(-15);
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

        popUp.setOnMousePressed(event -> {
            popupX = event.getX();
            popupY = event.getY();
            event.consume();
        });
        popUp.setOnMouseDragged(event -> {
            popUp.setTranslateX(event.getX() + popUp.getTranslateX() - popupX);
            popUp.setTranslateY(event.getY() + popUp.getTranslateY() - popupY);
            event.consume();
        });

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
                    resImg.setImage(new Image("/images/resources/" + warehouseDepotResource.get(i).toLowerCase() + ".png"));
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
                            resImg.setImage(new Image("/images/resources/" + leaderDepotResource.get(offset).toLowerCase() + ".png"));
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
            try {
                for (BoardView otherPlayer : otherPlayers) {
                    int finalPlayerIndex = playerIndex;
                    Platform.runLater(() -> updateOtherPlayer(finalPlayerIndex, otherPlayer));
                    playerIndex++;
                }
            } catch (Exception ignored){ }
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
                marbleImg.setImage(new Image("/images/marbles/" + market[i][j].toLowerCase() + "Marble.png"));
            }
        }
        ImageView marbleImg = (ImageView) pane.lookup("#freemarble");
        marbleImg.setImage(new Image("/images/marbles/" + view.getMarketView().getFreeMarble().toLowerCase() + "Marble.png"));

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


//--------------------------GAME PHASES-----------------------------------------------------------------------------
    @Override
    public void visitGameStarted(String str) {
        if (view.getOtherBoardsView() != null) {
            this.connectedOtherPlayersNumber = view.getOtherBoardsView().size();
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


            String str = errorMessage == null? "" : errorMessage + "\n";
            popUpTextMessage.setText(str + "Choose an action");

            leftButton.setText("Main");
            leftButton.setVisible(true);
            leftButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenFirstMoveMessage(0)));
            centerButton.setVisible(false);

            rightButton.setText("Leader");
            if(view.getPersonalBoardView().getHiddenHand().isEmpty())
                rightButton.setDisable(true);
            else
                rightButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenFirstMoveMessage(1)));
            rightButton.setVisible(true);

            popUp.setVisible(true);

        } else {
            sendButton.setDisable(true);
            exitButton.setDisable(true);
            textMessage.setText(currentPlayerNickname + " is choosing an action");
            popUpEffect.setVisible(false);
            popUp.setVisible(false);
        }
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
            rightButton.setDisable(false);

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

            //TODO check if works
            leaderMap = new HashMap<>();
            leaderMap.put(0, 0);
            leaderMap.put(1, 0);

            popUpEffect.setVisible(false);
            popUp.setVisible(false);

            sendButton.setDisable(false);
            sendButton.setOnAction(actionEvent -> {
                System.out.println(new Gson().toJson(leaderMap));
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
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {

        popUpEffect.setVisible(true);

        String str = "";
        popUpTextMessage.setText(str + "Choose which white marble power you want to use");

        leftButton.setVisible(false);
        centerButton.setVisible(false);
        rightButton.setVisible(false);
        rightButton.setDisable(false);

        res0Button.setOnAction(actionEvent -> {
            networkHandler.sendMessage(new ChosenWhiteMarbleMessage(res1));
            this.res0.setVisible(false);
            this.res1.setVisible(false);
            this.res0Button.setDisable(true);
            this.res1Button.setDisable(true);
        });
        res1Button.setOnAction(actionEvent -> {
            networkHandler.sendMessage(new ChosenWhiteMarbleMessage(res2));
            this.res0.setVisible(false);
            this.res1.setVisible(false);
            this.res0Button.setDisable(true);
            this.res1Button.setDisable(true);
        });
        res0Button.setDisable(false);
        res1Button.setDisable(false);
        this.res0.setImage(new Image("/images/resources/" + res1.toString().toLowerCase() + ".png"));
        this.res1.setImage(new Image("/images/resources/" + res2.toString().toLowerCase() + ".png"));
        this.res0.setVisible(true);
        this.res1.setVisible(true);

        popUp.setVisible(true);

    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) {
        if(currentPlayerNickname.equals(view.getNickname())) {
            Platform.runLater(() -> view.changeScene(view.scenesMap.get(GUI.DEVELOPMENT)));

            resToRemove = new HashMap<>();
            setAllCardSlotButtonsDisable(false);
            popUpEffect.setVisible(false);
            popUp.setVisible(false);
            sendButton.setOnAction(actionEvent -> {
                devTextVisibleProperty.setValue(false);
                this.networkHandler.sendMessage(new ChosenDevCardToPurchaseMessage(chosenRow, chosenCol, resToRemove, chosenCardSlot));
                disableAllDepotButtons();
                setAllCardSlotButtonsDisable(true);
            });
            sendButton.setDisable(false);
            textMessage.setText("Choose a card slot and resources to buy the card\n");
            if(chosenRow >= 0)
                printChosenResource(view.getDevelopmentCardByID(view.getDevGridView().getGridId(chosenRow, chosenCol)).getCost());

            for(int i = 0; i <= 2; i++){
                int index = i;
                ((Button) pane.lookup("#cardslot_"+i)).setOnAction(actionEvent -> {
                    chosenCardSlot = index;
                    if(chosenRow >= 0)
                        printChosenResource(view.getDevelopmentCardByID(view.getDevGridView().getGridId(chosenRow, chosenCol)).getCost());
                });
                pane.lookup("#cardslot_" + i).setDisable(false);
            }

            for (int i = 0; i <= 2; i++) {
                int index = i;
                Button depotButton = (Button) pane.lookup("#warehouseButton_" + i);
                if (view.getPersonalBoardView().getWarehouseDepotQuantity().get(index) > 0) {
                    depotButton.setOnAction(actionEvent -> {
                        Resource res = Resource.valueOf(view.getPersonalBoardView().getWarehouseDepotResource().get(index));
                        Map<Resource, Integer> tmpMap;
                        int addedQuantity = 1;
                        if (resToRemove.get(index) != null) {
                            tmpMap = resToRemove.get(index);
                            if (resToRemove.get(index).get(res) != null)
                                addedQuantity += resToRemove.get(index).get(res);
                        } else
                            tmpMap = new HashMap<>();
                        tmpMap.put(res, addedQuantity);
                        resToRemove.put(index, tmpMap);
                        if(chosenRow >= 0)
                            printChosenResource(view.getDevelopmentCardByID(view.getDevGridView().getGridId(chosenRow, chosenCol)).getCost());
                    });
                    depotButton.setDisable(false);
                }
            }

            List<Integer> activeLeaders = view.getPersonalBoardView().getActiveLeaders();
            int leaderOffset = 0;
            for (int i = 0; i <= 1; i++) {
                if (i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("depots")) {
                    if (view.getPersonalBoardView().getLeaderDepotQuantity().get(leaderOffset) > 0) {
                        Button leaderBtn = (Button) pane.lookup("#leaderButton_" + i);
                        int finalLeaderOffset = leaderOffset;
                        leaderBtn.setOnAction(actionEvent -> {
                            Resource res = Resource.valueOf(view.getPersonalBoardView().getLeaderDepotResource().get(finalLeaderOffset));
                            Map<Resource, Integer> tmpMap;
                            int addedQuantity = 1;
                            //TODO
                            int index = finalLeaderOffset + 3;
                            if (resToRemove.get(index) != null) {
                                tmpMap = resToRemove.get(index);
                                if (resToRemove.get(index).get(res) != null)
                                    addedQuantity += resToRemove.get(index).get(res);
                            } else
                                tmpMap = new HashMap<>();
                            tmpMap.put(res, addedQuantity);
                            resToRemove.put(index, tmpMap);
                            if(chosenRow >= 0)
                                printChosenResource(view.getDevelopmentCardByID(view.getDevGridView().getGridId(chosenRow, chosenCol)).getCost());
                        });
                        leaderBtn.setDisable(false);
                    }
                    leaderOffset++;
                }
            }

            List<String> resources = new ArrayList<>(Arrays.asList("coin", "servant", "stone", "shield"));
            for (String resStr : resources) {
                int index = 5;
                if (view.getPersonalBoardView().getStrongBox().get(resStr.toUpperCase()) > 0) {
                    Button strongboxBtn = (Button) pane.lookup("#strongboxButton_" + resStr);
                    strongboxBtn.setOnAction(actionEvent -> {
                        Resource res = Resource.valueOf(resStr.toUpperCase());
                        Map<Resource, Integer> tmpMap;
                        int addedQuantity = 1;
                        if (resToRemove.get(index) != null) {
                            tmpMap = resToRemove.get(index);
                            if (resToRemove.get(index).get(res) != null)
                                addedQuantity += resToRemove.get(index).get(res);
                        } else
                            tmpMap = new HashMap<>();
                        tmpMap.put(res, addedQuantity);
                        resToRemove.put(index, tmpMap);
                        if(chosenRow >= 0)
                            printChosenResource(view.getDevelopmentCardByID(view.getDevGridView().getGridId(chosenRow, chosenCol)).getCost());
                    });
                    strongboxBtn.setDisable(false);
                }
            }
        } else
            textMessage.setText(currentPlayerNickname + " is purchasing a development card");
    }

    @Override
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage) {
        if(currentPlayerNickname.equals(view.getNickname())) {


            popUpEffect.setVisible(true);
            textMessage.setText("");
            String str = errorMessage == null? "" : errorMessage + "\n";
            popUpTextMessage.setText(str + "Choose an action");

            leftButton.setText("Skip leader");
            leftButton.setVisible(true);
            leftButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenMiddleMoveMessage(0)));
            centerButton.setVisible(false);
            rightButton.setText("Leader action");
            if(view.getPersonalBoardView().getHiddenHand().isEmpty())
                rightButton.setDisable(true);
            else
                rightButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenMiddleMoveMessage(1)));
            rightButton.setVisible(true);
            popUp.setVisible(true);

        } else
            textMessage.setText(currentPlayerNickname + " is in middle turn");
    }

    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {
        if(currentPlayerNickname.equals(view.getNickname())) {
            depotToSwap = new ArrayList<>();
            sendButton.setDisable(true);
            String str = errorMessage == null? "" : errorMessage + "\n";
            textMessage.setText(str + "Swap depots");

            Button button;
            for(int i = 0; i < 3; i++) {
                button = (Button) pane.lookup("#warehouseButton_" + i);
                int j = i;
                button.setOnAction(actionEvent -> {
                    if(depotToSwap.size() == 2)
                        depotToSwap.remove(0);
                    depotToSwap.add(j);

                    StringBuilder txt = new StringBuilder("You chose: \n ");
                    for(Integer depot: depotToSwap){
                        txt.append(depot);
                        txt.append(" ");
                    }
                    textMessage.setText(String.valueOf(txt));

                    if(depotToSwap.size() == 2) {
                        sendButton.setOnAction(newActionEvent -> {
                            disableAllDepotButtons();
                            this.networkHandler.sendMessage(new ChosenSwapDepotMessage(depotToSwap.get(0), depotToSwap.get(1)));
                        });
                        sendButton.setDisable(false);
                    }

                } );
                button.setDisable(false);
            }
            exitButton.setOnAction(actionEvent -> {
                exitButton.setDisable(true);
                disableAllDepotButtons();
                this.networkHandler.sendMessage(new ChosenSwapDepotMessage(-1,-1));
            });
            exitButton.setDisable(false);

        } else
            textMessage.setText(currentPlayerNickname + " is swapping depots");

    }

    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {
        if(currentPlayerNickname.equals(view.getNickname())) {
            sendButton.setDisable(true);
            popUpEffect.setVisible(true);
            textMessage.setText("");

            String str = errorMessage == null? "" : errorMessage + "\n";
            popUpTextMessage.setText(str + "Choose an action for "+res.toString());

            leftButton.setText("Discard");
            leftButton.setVisible(true);
            leftButton.setOnAction(actionEvent -> this.networkHandler.sendMessage(new ChosenMarketDepotMessage(-1)));

            centerButton.setText("Swap");
            centerButton.setVisible(true);
            centerButton.setOnAction(actionEvent -> {

                popUpEffect.setVisible(false);
                popUp.setVisible(false);

                this.networkHandler.sendMessage(new ChosenMarketDepotMessage(-2));

            });

            rightButton.setText("Place");
            rightButton.setVisible(true);
            rightButton.setDisable(false);
            rightButton.setOnAction(actionEvent -> {
                popUpEffect.setVisible(false);
                popUp.setVisible(false);

                Button button;
                for(int i = 0; i < 3; i++) {
                    button = (Button) pane.lookup("#warehouseButton_" + i);
                    int j = i;
                    button.setOnAction(newActionEvent -> {
                        this.networkHandler.sendMessage(new ChosenMarketDepotMessage(j));
                        disableAllDepotButtons();
                    });
                    button.setDisable(false);
                }

                List<Integer> activeLeaders = view.getPersonalBoardView().getActiveLeaders();
                //enabling leader depots buttons

                int offset = 0;
                if(activeLeaders != null) {
                    for (int i = 0; i < 2; i++){
                        if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("depots")){
                            for(int j = 0; j < 2; j++ ) {
                                Button leaderBtn = (Button) pane.lookup("#leaderButton_" + i);
                                int finalOffset = offset;
                                leaderBtn.setOnAction(newActionEvent ->{
                                    this.networkHandler.sendMessage(new ChosenMarketDepotMessage(3 + finalOffset));
                                    disableAllDepotButtons();
                                });
                                leaderBtn.setDisable(false);
                            }
                            offset++;
                        }
                    }
                }

            });
            popUp.setVisible(true);

        } else
            textMessage.setText(currentPlayerNickname + " is placing resources in depots");
    }

    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {
        if (currentPlayerNickname.equals(view.getNickname())) {
            wareHouseMap = new HashMap<>();
            strongBoxMap = new HashMap<>();
            resToRemove = new HashMap<>();

            popUpEffect.setVisible(false);
            popUp.setVisible(false);

            String str = errorMessage == null ? "" : errorMessage + "\n";
            textMessage.setText(str + "Please choose a production");

            //enabling card slots
            for (int j = 0; j <= 2; j++) {
                int card = view.getPersonalBoardView().getTopCardSlot(j);
                if (card != 0) {
                    int finalJ = j;
                    ((Button) pane.lookup("#cardslot_"+j)).setOnAction(actionEvent -> {
                        chosenCardSlot = finalJ;
                        enablePickResourceForProduction();
                        printChosenResource(getResourceToPickForProduction());
                    });
                    pane.lookup("#cardslot_" + j).setDisable(false);
                }
            }
            //enabling leader
            List<Integer> activeLeaders = view.getPersonalBoardView().getActiveLeaders();
            for (int i = 0; i <= 1; i++) {
                Button leaderBtn = (Button) pane.lookup("#leaderButton_" + i);
                int finalI = 3 + i;
                if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("production")){
                    leaderBtn.setOnAction(actionEvent -> {
                        chosenCardSlot = finalI;
                        enablePickResourceForProduction();
                        enablePickOutputProduction();
                        printChosenResource(getResourceToPickForProduction());
                    });
                    leaderBtn.setDisable(false);
                }
            }


            Button baseProdBtn = (Button) pane.lookup("#baseProduction");
            baseProdBtn.setOnAction(actionEvent -> {
                chosenCardSlot = 5;
                enablePickResourceForProduction();
                enablePickOutputProduction();
                printChosenResource(getResourceToPickForProduction());
                baseProdBtn.setDisable(true);

            });
            baseProdBtn.setDisable(false);


            sendButton.setOnAction(actionEvent -> {
                this.networkHandler.sendMessage(new ChosenProductionMessage(chosenCardSlot, wareHouseMap, strongBoxMap, chosenResource));
                disableAllDepotButtons();
                setAllCardSlotButtonsDisable(true);
            });
            sendButton.setDisable(false);

            exitButton.setOnAction(actionEvent -> {
                exitButton.setDisable(true);
                disableAllDepotButtons();

                // disabling cardslot
                for (int j = 0; j <= 2; j++) {
                    int card = view.getPersonalBoardView().getTopCardSlot(j);
                    if (card != 0)
                        pane.lookup("#cardslot_"+j).setDisable(true);
                }

                //disabling leader
                for (int i = 0; i <= 1; i++)
                    if(i < activeLeaders.size() && view.getLeaderCardByID(activeLeaders.get(i)).getPower().equals("production"))
                        pane.lookup("#leaderButton_" + i).setDisable(true);
                //disabling board production
                baseProdBtn.setDisable(true);

                this.networkHandler.sendMessage(new ChosenProductionMessage(6, null, null, null));
            });
            exitButton.setDisable(false);

        }else
            textMessage.setText(currentPlayerNickname + " is activating production");
    }

    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {
        popUpEffect.setVisible(true);

        String str = "\n";
        int i = 1;
        for(Map.Entry<String, Integer> entry : gameResult.entrySet()){
            str += i+": "+entry.getKey()+" ===> "+entry.getValue()+" POINTS\n";
            i++;
        }

        if(view.getNickname().equals(winner))
            popUpTextMessage.setText("YOU WIN!!!"+str);
        else
            popUpTextMessage.setText(winner+" WINS!!!"+str);

        centerButton.setTranslateY(centerButton.getTranslateY() + 20);
        centerButton.setVisible(true);
        centerButton.setDisable(false);

        leftButton.setVisible(false);
        leftButton.setDisable(true);
        rightButton.setVisible(false);
        rightButton.setDisable(true);

        centerButton.setText("Close");
        centerButton.setOnAction(actionEvent -> {
            System.exit(0);
        });

        popUp.setVisible(true);
    }


    @Override
    public void visitGameAbort() {
        leftButton.setVisible(false);
        leftButton.setDisable(true);
        rightButton.setVisible(false);
        rightButton.setDisable(true);

        centerButton.setVisible(true);
        centerButton.setDisable(false);

        centerButton.setText("Close");
        centerButton.setOnAction(actionEvent -> {
            System.exit(0);
        });

        popUpTextMessage.setText("Insufficient players number to continue the match or invalid game setup");
        popUp.setVisible(true);
        popUpEffect.setVisible(true);
    }

    @Override
    public void visitForcedReconnectionUpdate() {
        this.connectedOtherPlayersNumber = view.getOtherBoardsView().size();
        Platform.runLater(() -> this.visitBoardsUpdate());
        Platform.runLater(() -> this.visitMarketUpdate());
        for (int i = 0; i < view.getOtherBoardsView().size(); i++) {
            ImageView otherPlayer = (ImageView) pane.lookup("#otherplayer_" + i);
            otherPlayer.setImage(GUI.punchBoardImg.get("boardFront"));
            StackPane otherPlayerPane = (StackPane) pane.lookup("#otherplayer_pane_" + i);
            otherPlayerPane.setVisible(true); //enabling other players images overlay
        }
    }

    @Override
    public void visitPlayerDisconnection(String nickname) {
        this.connectedOtherPlayersNumber -= 1;

        Label label = (Label) pane.lookup("#otherplayer_"+connectedOtherPlayersNumber+"_nickname");
        label.setText(nickname + " (DISCONNECTED)");

        ImageView disconnectedBoard = (ImageView) pane.lookup("#otherplayer_" + connectedOtherPlayersNumber);
        disconnectedBoard.setImage(GUI.punchBoardImg.get("boardBack"));
        StackPane otherPlayerPane = (StackPane) pane.lookup("#otherplayer_pane_" + connectedOtherPlayersNumber);
        otherPlayerPane.setVisible(false); //disabling other players images overlay

    }



    @Override
    public void visitPlayerReconnection(String nickname) {
        this.connectedOtherPlayersNumber += 1;
        ImageView reconnectedBoard = (ImageView) pane.lookup("#otherplayer_" + (connectedOtherPlayersNumber - 1));
        reconnectedBoard.setImage(GUI.punchBoardImg.get("boardFront"));
        StackPane otherPlayerPane = (StackPane) pane.lookup("#otherplayer_pane_" + (connectedOtherPlayersNumber - 1));
        otherPlayerPane.setVisible(true); //enabling other players images overlay
    }
}
