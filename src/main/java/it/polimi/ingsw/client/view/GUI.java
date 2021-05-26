package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.gui.controllers.GUIController;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class GUI extends View{
    public static final String LOGIN = "login.fxml";
    private static final String SETUP_LEADER = "setupLeader.fxml";
    private static final String SETUP_RESOURCE = "setupResource.fxml";
    private static final String MAIN_GUI = "game.fxml";
    private static final String END_GAME = "endGame.fxml";

    public static Stage stg;
    private final Map<String, GUIController> controllersMap = new HashMap<>();
    private final HashMap<String, Scene> scenesMap = new HashMap<>();


    public GUI() {
        List<String> fxml = new ArrayList<>(Arrays.asList(SETUP_LEADER, SETUP_RESOURCE, MAIN_GUI, END_GAME));
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

    public static void setStg(Stage stg) {
        GUI.stg = stg;
    }

    public void changeScene(Scene scene) {
        stg.setScene(scene);
    }

    @Override
    public void showMessage(String str) {

    }

    @Override
    public void visitNicknameAlreadyUsed(String str,String gameID) {


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
        Platform.runLater(() -> controllersMap.get(LOGIN).visitRequestLobbySize(str));
    }

    //-----------------------------------------------------------------------------------------
    @Override
    public void visitBoardsUpdate(String personalBoard, List<String> otherBoards) {

    }

    @Override
    public void visitDevGridUpdate(String updatedGrid) {

    }

    @Override
    public void visitGameStarted(String str) {

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
    public void visitLorenzoUpdate(String updatedLorenzo) {

    }

    @Override
    public void visitMarketUpdate(String updatedMarket) {

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
