package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.GUIController;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class GUI extends View{
    public static final String LOGIN = "login.fxml";
    public static final String SETUP_LEADER = "setupLeader.fxml";
    public static final String SETUP_RESOURCE = "setupResource.fxml";
    public static final String MAIN_GUI = "game.fxml";
    public static final String END_GAME = "endGame.fxml";
    public static final String DEVELOPMENT = "development.fxml";
    public static  Image[] leaderCardImg;
    public static  Image[] developmentCardImg;
    public static Map<String,Image> punchBoardImg = new HashMap<>();

    public static Stage stg;
    public final Map<String, GUIController> controllersMap = new HashMap<>();
    public final HashMap<String, Scene> scenesMap = new HashMap<>();


    public GUI() {
        super();

        leaderCardImg = new Image[super.leaderCards.length + 1];
        developmentCardImg = new Image[super.developmentCards.length + 1];

        for(int index = 1; index <= super.leaderCards.length; index++ )
            leaderCardImg[index] = new Image("/images/leaderCardsFront/leader" + index + ".png");

        developmentCardImg[0] = new Image("/images/devGridEmptySlot.png");
        for(int index = 1; index <= super.developmentCards.length; index++ )
            developmentCardImg[index] = new Image("/images/developmentCardsFront/development" + index + ".png");

        String[] tmpImgName = new String[] {"add2BlackCross","blackCross","boardBack","boardFront","discard2Blue",
        "discard2Green","discard2Purple","discard2Yellow","shuffleActionToken"};
        Arrays.stream(tmpImgName).forEach(s -> punchBoardImg.put(s,new Image("/images/punchboard/" + s + ".png")));
        punchBoardImg.put("faith",new Image("/images/resources/faith.png"));

        List<String> fxml = new ArrayList<>(Arrays.asList(SETUP_LEADER, SETUP_RESOURCE, MAIN_GUI, END_GAME,DEVELOPMENT));
        try {
            for (String path : fxml) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                scenesMap.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGUI(this);
                controllersMap.put(path, controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLoginController(GUIController loginController,Scene loginScene){
        controllersMap.put(LOGIN,loginController);
        scenesMap.put(LOGIN,loginScene);
    }

    @Override
    public void addNetworkHandler(NetworkHandler networkHandler){
        super.networkHandler = networkHandler;
        for(Map.Entry<String,GUIController> entry:controllersMap.entrySet())
            entry.getValue().setNetworkHandler(networkHandler);
    }

    @Override
    public void visitGameAbort() {
        //TODO
    }

    @Override
    public void visitPlayerDisconnection(String nickname) {
        //TODO
    }

    @Override
    public void visitPlayerReconnection(String nickname) {
        //TODO
    }

    public static void setStg(Stage stg) {
        GUI.stg = stg;
    }

    public void changeScene(Scene scene) {
        stg.setScene(scene);
    }

//---------------------LOGIN PHASE---------------------------------------------------------------
    @Override
    public void showMessage(String str) {

    }

    @Override
    public void visitNicknameAlreadyUsed(String str,String gameID) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitNicknameAlreadyUsed(str, gameID));
    }

    @Override
    public void visitLobbyFull(String str) {
        //TODO TEST SCENE
        Platform.runLater(() -> {
            controllersMap.get(LOGIN).visitLobbyFull(str);
        });
    }

    @Override
    public void visitLobbyNotReady(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLobbyNotReady(str));
    }

    @Override
    public void visitLoginSuccess(String currentPlayers) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLoginSuccess(currentPlayers));
    }

    @Override
    public void visitRequestLobbySize(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitRequestLobbySize(str));
    }

    //---------------------UPDATES-------------------------------------------------------------

    @Override
    public void visitBoardsUpdate(String personalBoard, List<String> otherBoards) {
        this.personalBoardView = new Gson().fromJson(personalBoard, BoardView.class);
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<>();
            otherBoards.forEach(s -> otherBoardsView.add(new Gson().fromJson(s, BoardView.class)));
        }
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitBoardsUpdate());
    }

    @Override
    public void visitDevGridUpdate(String updatedGrid) {
        this.devGrid = new Gson().fromJson(updatedGrid, GridView.class);
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitDevGridUpdate());
    }

    @Override
    public void visitLorenzoUpdate(String updatedLorenzo) {
        this.lorenzoView = new Gson().fromJson(updatedLorenzo, LorenzoView.class);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitLorenzoUpdate());
    }

    @Override
    public void visitMarketUpdate(String updatedMarket) {
        this.marketView = new Gson().fromJson(updatedMarket, MarketView.class);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMarketUpdate());
    }

    @Override
    public void visitForcedReconnectionUpdate(String personalBoard, List<String> otherBoards, String updatedGrid, String updatedMarket) {

    }

    @Override
    public void visitInkwell(String nickname) {
        if(this.personalBoardView.getNickname().equals(nickname))
            this.personalBoardView.setInkwell();
        else
            this.otherBoardsView.stream().filter(p -> p.getNickname().equals(nickname)).forEach(BoardView::setInkwell);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitInkwell(nickname));
    }

//--------------------GAME PHASE--------------------------------------------------------------------------

    @Override
    public void visitGameStarted(String str) {
        Platform.runLater(() -> {
            changeScene(scenesMap.get(MAIN_GUI));
            stg.setX(30);
            stg.setY(0);
            controllersMap.get(MAIN_GUI).visitGameStarted(str);
        });
    }

    @Override
    public void visitInitialResource(int quantity) {
        Platform.runLater(() -> {
            //changeScene(scenesMap.get(MAIN_GUI));
            controllersMap.get(SETUP_RESOURCE).visitInitialResource(quantity);
        });

    }

    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {
        Platform.runLater(() -> {
            changeScene(scenesMap.get(SETUP_LEADER));
            controllersMap.get(SETUP_LEADER).visitLeaderProposal(proposedLeaderCards);
        });
    }

    @Override
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitWhiteMarbleProposal(res1, res2));

    }

    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitStartTurn(currentPlayerNickname, errorMessage));
    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) {
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitDevCardSale(currentPlayerNickname));
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitDevCardSale(currentPlayerNickname));
    }

    @Override
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMiddleTurn(currentPlayerNickname,errorMessage));
    }

    @Override
    public void visitLeaderAction(String currentPlayerNickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitLeaderAction(currentPlayerNickname));

    }

    @Override
    public void visitMainActionState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMainActionState(currentPlayerNickname, errorMessage));
    }

    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitProductionState(currentPlayerNickname, errorMessage));

    }

    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitGameOverState(winner, gameResult));
    }

    @Override
    public void visitMarketState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMarketState(currentPlayerNickname, errorMessage));
    }

    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitSwapState(currentPlayerNickname, errorMessage));
    }

    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitResourceManagementState(res,currentPlayerNickname, errorMessage));
    }
}
