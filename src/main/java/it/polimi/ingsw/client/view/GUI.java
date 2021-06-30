package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.GUIController;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that represents the GUI version of the view.
 */
public class GUI extends View{
    public static final String LOGIN = "login.fxml";
    public static final String SETUP_LEADER = "setupLeader.fxml";
    public static final String SETUP_RESOURCE = "setupResource.fxml";
    public static final String MAIN_GUI = "game.fxml";
    public static final String DEVELOPMENT = "development.fxml";
    public static  Image[] leaderCardImg;
    public static  Image[] developmentCardImg;
    public static  Image[] developmentCardImgSmall;
    public static Map<String,Image> punchBoardImg = new HashMap<>();

    public static Stage stg;
    public final Map<String, GUIController> controllersMap = new HashMap<>();
    public final HashMap<String, Scene> scenesMap = new HashMap<>();

    public GUI() {
        super();

        leaderCardImg = new Image[super.leaderCards.length + 1];
        developmentCardImg = new Image[super.developmentCards.length + 1];
        developmentCardImgSmall = new Image[super.developmentCards.length + 1];

        for(int index = 1; index <= super.leaderCards.length; index++ )
            leaderCardImg[index] = new Image("/images/leaderCardsFront/leader" + index + ".png");

        developmentCardImg[0] = new Image("/images/devGridEmptySlot.png");
        for(int index = 1; index <= super.developmentCards.length; index++ )
            developmentCardImg[index] = new Image("/images/developmentCardsFront/development" + index + ".png");

        developmentCardImgSmall[0] = new Image("/images/devGridEmptySlot.png");
        for(int index = 1; index <= super.developmentCards.length; index++ )
            developmentCardImgSmall[index] = new Image("/images/developmentCardsFront/small/development" + index + ".png");

        String[] tmpImgName = new String[] {"add2BlackCross","blackCross","boardBack","boardFront","discard2Blue",
        "discard2Green","discard2Purple","discard2Yellow","shuffleActionToken"};
        Arrays.stream(tmpImgName).forEach(s -> punchBoardImg.put(s,new Image("/images/punchboard/" + s + ".png")));
        punchBoardImg.put("faith",new Image("/images/resources/faith.png"));

        List<String> fxml = new ArrayList<>(Arrays.asList(SETUP_LEADER, SETUP_RESOURCE, MAIN_GUI,DEVELOPMENT));
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

    /**
     * Method used to add the controller and the scene of the login phase to the data structures of the GUI class.
     * @param loginController Login scene's controller.
     * @param loginScene Login scene.
     */
    public void addLoginController(GUIController loginController,Scene loginScene){
        controllersMap.put(LOGIN,loginController);
        scenesMap.put(LOGIN,loginScene);
    }

    public static void setStg(Stage stg) {
        GUI.stg = stg;
    }

    /**
     * Method used to change scene in the current stage.
     * @param scene The new scene to show.
     */
    public void changeScene(Scene scene) {
        stg.setScene(scene);
    }

    /**
     * Method used to set the reference to a specific NetworkHandler in the GUI class and in every scene controller.
     * @param networkHandler The new NetworkHandler.
     */
    @Override
    public void addNetworkHandler(NetworkHandler networkHandler){
        super.networkHandler = networkHandler;
        for(Map.Entry<String,GUIController> entry:controllersMap.entrySet())
            entry.getValue().setNetworkHandler(networkHandler);
    }

    //------------------------------RECONNECTION FA ----------------------------------------------------------

    /**
     * Method used to show in the view the contents of the GameAbort message.
     */
    @Override
    public void visitGameAbort() {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitGameAbort());
        Platform.runLater(() -> controllersMap.get(SETUP_LEADER).visitGameAbort());
        Platform.runLater(() -> controllersMap.get(SETUP_RESOURCE).visitGameAbort());
    }

    /**
     * Method used to show in the view the contents of the PlayerDisconnection message.
     */
    @Override
    public void visitPlayerDisconnection(String nickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitPlayerDisconnection(nickname));
    }

    /**
     * Method used to show in the view the contents of the PlayerReconnection message.
     */
    @Override
    public void visitPlayerReconnection(String nickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitPlayerReconnection(nickname));
    }

    /**
     * Method used to show in the view the contents of the ForcedReconnectionUpdate message.
     */
    @Override
    public void visitForcedReconnectionUpdate(String personalBoard, List<String> otherBoards, String updatedGrid, String updatedMarket) {
        Gson gson = new Gson();
        this.marketView = gson.fromJson(updatedMarket, MarketView.class);
        this.personalBoardView = gson.fromJson(personalBoard, BoardView.class);
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<>();
            otherBoards.forEach(s -> otherBoardsView.add(gson.fromJson(s, BoardView.class)));
        }
        this.devGrid = gson.fromJson(updatedGrid, GridView.class);

        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitForcedReconnectionUpdate());
        Platform.runLater(() -> controllersMap.get(LOGIN).visitForcedReconnectionUpdate());
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitForcedReconnectionUpdate());
    }

    //---------------------LOGIN PHASE---------------------------------------------------------------
   
    @Override
    public void showMessage(String str) { }

    /**
     * Method used to show in the view the contents of the NicknameAlreadyUsed message.
     */
    @Override
    public void visitNicknameAlreadyUsed(String str,String gameID) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitNicknameAlreadyUsed(str, gameID));
    }

    /**
     * Method used to show in the view the contents of the LobbyFull message.
     */
    @Override
    public void visitLobbyFull(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLobbyFull(str));
    }

    /**
     * Method used to show in the view the contents of the LobbyNotReady message.
     */
    @Override
    public void visitLobbyNotReady(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLobbyNotReady(str));
    }

    /**
     * Method used to show in the view the contents of the LoginSuccess message.
     */
    @Override
    public void visitLoginSuccess(String currentPlayers) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLoginSuccess(currentPlayers));
    }

    /**
     * Method used to show in the view the contents of the RequestLobbySize message.
     */
    @Override
    public void visitRequestLobbySize(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitRequestLobbySize(str));
    }
    
    //---------------------UPDATES-------------------------------------------------------------

    /**
     * Method used to show in the view the contents of the BoardsUpdate message.
     */
    @Override
    public void visitBoardsUpdate(String personalBoard, List<String> otherBoards) {
        this.personalBoardView = new Gson().fromJson(personalBoard, BoardView.class);
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<>();
            otherBoards.forEach(s -> otherBoardsView.add(new Gson().fromJson(s, BoardView.class)));
        }
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitBoardsUpdate());
    }

    /**
     * Method used to show in the view the contents of the DevGridUpdate message.
     */
    @Override
    public void visitDevGridUpdate(String updatedGrid) {
        this.devGrid = new Gson().fromJson(updatedGrid, GridView.class);
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitDevGridUpdate());
    }

    /**
     * Method used to show in the view the contents of the LorenzoUpdate message.
     */
    @Override
    public void visitLorenzoUpdate(String updatedLorenzo) {
        this.lorenzoView = new Gson().fromJson(updatedLorenzo, LorenzoView.class);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitLorenzoUpdate());
    }

    /**
     * Method used to show in the view the contents of the MarketUpdate message.
     */
    @Override
    public void visitMarketUpdate(String updatedMarket) {
        this.marketView = new Gson().fromJson(updatedMarket, MarketView.class);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMarketUpdate());
    }

    /**
     * Method used to show in the view the contents of the Inkwell message.
     */
    @Override
    public void visitInkwell(String nickname) {
        if(this.personalBoardView.getNickname().equals(nickname))
            this.personalBoardView.setInkwell();
        else
            this.otherBoardsView.stream().filter(p -> p.getNickname().equals(nickname)).forEach(BoardView::setInkwell);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitInkwell(nickname));
    }
    
//--------------------GAME PHASE--------------------------------------------------------------------------

    /**
     * Method used to show in the view the contents of the GameStarted message.
     */
    @Override
    public void visitGameStarted(String str) {
        Platform.runLater(() -> {
            changeScene(scenesMap.get(MAIN_GUI));
            stg.setX(30);
            stg.setY(0);
            controllersMap.get(MAIN_GUI).visitGameStarted(str);
        });
    }

    /**
     * Method used to show in the view the contents of the InitialResource message.
     */
    @Override
    public void visitInitialResource(int quantity) {
        Platform.runLater(() -> {
            //changeScene(scenesMap.get(MAIN_GUI));
            controllersMap.get(SETUP_RESOURCE).visitInitialResource(quantity);
        });
    }

    /**
     * Method used to show in the view the contents of the LeaderProposal message.
     */
    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {
        Platform.runLater(() -> {
            changeScene(scenesMap.get(SETUP_LEADER));
            controllersMap.get(SETUP_LEADER).visitLeaderProposal(proposedLeaderCards);
        });
    }

    /**
     * Method used to show in the view the contents of the WhiteMarbleProposal message.
     */
    @Override
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitWhiteMarbleProposal(res1, res2));
    }

    /**
     * Method used to show in the view the contents of the StartTurn message.
     */
    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitStartTurn(currentPlayerNickname, errorMessage));
    }
    
    /**
     * Method used to show in the view the contents of the DevCardSale message.
     */
    @Override
    public void visitDevCardSale(String currentPlayerNickname) {
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitDevCardSale(currentPlayerNickname));
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitDevCardSale(currentPlayerNickname));
    }

    /**
     * Method used to show in the view the contents of the MiddleTurn message.
     */
    @Override
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMiddleTurn(currentPlayerNickname,errorMessage));
    }

    /**
     * Method used to show in the view the contents of the LeaderAction message.
     */
    @Override
    public void visitLeaderAction(String currentPlayerNickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitLeaderAction(currentPlayerNickname));
    }

    /**
     * Method used to show in the view the contents of the MainActionState message.
     */
    @Override
    public void visitMainActionState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMainActionState(currentPlayerNickname, errorMessage));
    }

    /**
     * Method used to show in the view the contents of the ProductionState message.
     */
    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitProductionState(currentPlayerNickname, errorMessage));
    }

    /**
     * Method used to show in the view the contents of the GameOverState message.
     */
    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitGameOverState(winner, gameResult));
    }

    /**
     * Method used to show in the view the contents of the MarketState message.
     */
    @Override
    public void visitMarketState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMarketState(currentPlayerNickname, errorMessage));
    }

    /**
     * Method used to show in the view the contents of the SwapState message.
     */
    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitSwapState(currentPlayerNickname, errorMessage));
    }

    /**
     * Method used to show in the view the contents of the ResourceManagementState message.
     */
    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitResourceManagementState(res,currentPlayerNickname, errorMessage));
    }
}
