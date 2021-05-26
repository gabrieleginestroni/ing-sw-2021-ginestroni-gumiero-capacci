package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class LoginController implements GUIController {
    private GUI view;
    @FXML
    private Button logInButton;
    @FXML
    private TextField bottomTextField;
    @FXML
    private TextField topTextField;
    @FXML
    private Label topLabel;
    @FXML
    private Label bottomLabel;
    @FXML
    private Label loginLog;





    public void userLogin(ActionEvent event) {
        sendLoginRequest();
    }

    private void sendLoginRequest() {
        try {
            Socket socket = new Socket(topTextField.getText(), Integer.parseInt(bottomTextField.getText()));
            loginLog.setTextFill(Color.color(0,0.8,0.6));
            loginLog.setText("Connection successful!");
            loginLog.setVisible(true);
            GUI view = new GUI();
            NetworkHandler networkHandler = new NetworkHandler(socket, view);
            view.addNetworkHandler(networkHandler);
            Thread networkThread = new Thread(networkHandler);
            networkThread.start();

            topLabel.setText("Game ID");
            bottomLabel.setText("Nickname");
            topTextField.setPromptText("Game ID");
            bottomTextField.setPromptText("Nickname");
            logInButton.setText("Join");

            logInButton.setOnAction( actionEvent -> {
                System.out.println("Test");
                String nickname = bottomTextField.getText();
                String gameId = topTextField.getText();
                view.setNickname(nickname);
                networkHandler.sendMessage(new LoginRequestMessage(gameId,nickname));
            });



        } catch (IOException e) {
            loginLog.setTextFill(Color.color(1,0,0));
            loginLog.setText("Connection error!");
            loginLog.setVisible(true);
        }

    }

    @Override
    public void setGUI(GUI view) {
        this.view = view;

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
    //--------------------------------------------------------------------------------------------------------------
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
