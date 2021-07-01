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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void visitGameAbort() {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitGameAbort());
        Platform.runLater(() -> controllersMap.get(SETUP_LEADER).visitGameAbort());
        Platform.runLater(() -> controllersMap.get(SETUP_RESOURCE).visitGameAbort());
    }

    /**
     * {@inheritDoc}
     * @param nickname Disconnected player.
     */
    @Override
    public void visitPlayerDisconnection(String nickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitPlayerDisconnection(nickname));
    }

    /**
     * {@inheritDoc}
     * @param nickname The reconnected player's nickname
     */
    @Override
    public void visitPlayerReconnection(String nickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitPlayerReconnection(nickname));
    }

    /**
     * {@inheritDoc}
     * @param personalBoard The JSON file that represents the updated BoardView of a player's
     *                      PersonalBoard at the actual state of the game.
     * @param otherBoards The JSON file that represents the list of the updated HiddenHand-free BoardView of the PersonalBoards
     *                    of every other player at the actual state of the game.
     * @param updatedGrid The JSON file that represents the updated GridView at the actual state of the game.
     * @param updatedMarket The JSON file that represents the updated MarketView at the actual state of the game.
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
     * {@inheritDoc}
     * @param str The string contained in every NicknameAlreadyUsed that says that
     *            the nickname chosen by the player is already used inside the requested
     *            lobby.
     * @param gameID The gameID of the game the client was previously trying to connect to.
     */
    @Override
    public void visitNicknameAlreadyUsed(String str,String gameID) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitNicknameAlreadyUsed(str, gameID));
    }

    /**
     * {@inheritDoc}
     * @param str The string contained in every LobbyFull message that simply
     */
    @Override
    public void visitLobbyFull(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLobbyFull(str));
    }

    /**
     * {@inheritDoc}
     * @param str The string contained in every LobbyNotReady message that simply
     */
    @Override
    public void visitLobbyNotReady(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLobbyNotReady(str));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayers The string contained in every LoginSuccess message that simply
     *                       contains the nicknames of the players connected to the same lobby
     */
    @Override
    public void visitLoginSuccess(String currentPlayers) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitLoginSuccess(currentPlayers));
    }

    /**
     * {@inheritDoc}
     * @param str The string contained in every RequestLobbySize that contains the text message
     *            that has to be printed in the player's view.
     */
    @Override
    public void visitRequestLobbySize(String str) {
        Platform.runLater(() -> controllersMap.get(LOGIN).visitRequestLobbySize(str));
    }
    
    //---------------------UPDATES-------------------------------------------------------------

    /**
     * {@inheritDoc}
     * @param personalBoard The JSON file that represents the updated BoardView of a player's
     *                      PersonalBoard at the actual state of the game.
     * @param otherBoards The JSON file that represents the list of the updated HiddenHand-free BoardView of the PersonalBoards
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
     * {@inheritDoc}
     * @param updatedGrid The JSON file that represents the updated GridView at the actual state of the game.
     */
    @Override
    public void visitDevGridUpdate(String updatedGrid) {
        this.devGrid = new Gson().fromJson(updatedGrid, GridView.class);
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitDevGridUpdate());
    }

    /**
     * {@inheritDoc}
     * @param updatedLorenzo The JSON file that represents the updated LorenzoView.
     */
    @Override
    public void visitLorenzoUpdate(String updatedLorenzo) {
        this.lorenzoView = new Gson().fromJson(updatedLorenzo, LorenzoView.class);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitLorenzoUpdate());
    }

    /**
     * {@inheritDoc}
     * @param updatedMarket The JSON file that represents the updated MarketView at the actual state of the game.
     */
    @Override
    public void visitMarketUpdate(String updatedMarket) {
        this.marketView = new Gson().fromJson(updatedMarket, MarketView.class);
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMarketUpdate());
    }

    /**
     * {@inheritDoc}
     * @param nickname First player's nickname.
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
     * {@inheritDoc}
     * @param str The string contained in every GameStarted message that simply
     *            says that the setup phase of the game ended and the turn of the first
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
     * {@inheritDoc}
     * @param quantity The number of resources to choose.
     */
    @Override
    public void visitInitialResource(int quantity) {
        Platform.runLater(() -> {
            //changeScene(scenesMap.get(MAIN_GUI));
            controllersMap.get(SETUP_RESOURCE).visitInitialResource(quantity);
        });
    }

    /**
     * {@inheritDoc}
     * @param proposedLeaderCards The integer array that contains the 4 cardIDs of the proposed Leader Cards.
     */
    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {
        Platform.runLater(() -> {
            changeScene(scenesMap.get(SETUP_LEADER));
            controllersMap.get(SETUP_LEADER).visitLeaderProposal(proposedLeaderCards);
        });
    }

    /**
     * {@inheritDoc}
     * @param res1 The resource that represents the first White Marble Effect.
     * @param res2 The resource that represents the second White Marble Effect.
     */
    @Override
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitWhiteMarbleProposal(res1, res2));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitStartTurn(currentPlayerNickname, errorMessage));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     */
    @Override
    public void visitDevCardSale(String currentPlayerNickname) {
        Platform.runLater(() -> controllersMap.get(DEVELOPMENT).visitDevCardSale(currentPlayerNickname));
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitDevCardSale(currentPlayerNickname));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitMiddleTurn(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMiddleTurn(currentPlayerNickname,errorMessage));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     */
    @Override
    public void visitLeaderAction(String currentPlayerNickname) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitLeaderAction(currentPlayerNickname));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitMainActionState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMainActionState(currentPlayerNickname, errorMessage));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitProductionState(currentPlayerNickname, errorMessage));
    }

    /**
     * {@inheritDoc}
     * @param winner Winner's nickname.
     * @param gameResult The map that contains the nicknames of every player
     */
    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitGameOverState(winner, gameResult));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitMarketState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitMarketState(currentPlayerNickname, errorMessage));
    }

    /**
     * {@inheritDoc}
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitSwapState(currentPlayerNickname, errorMessage));
    }

    /**
     * {@inheritDoc}
     * @param res The proposed resource.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {
        Platform.runLater(() -> controllersMap.get(MAIN_GUI).visitResourceManagementState(res,currentPlayerNickname, errorMessage));
    }
}
