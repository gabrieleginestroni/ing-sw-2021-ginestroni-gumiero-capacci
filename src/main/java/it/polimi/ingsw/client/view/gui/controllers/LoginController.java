package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.BoardView;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.GridView;
import it.polimi.ingsw.client.view.MarketView;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.messages.client_server.LoginSizeMessage;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController extends GUIController implements Initializable {
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
    @FXML
    private Label message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInButton.setDefaultButton(true);
    }

    public void userLogin(ActionEvent event) {
        Platform.runLater(()-> sendLoginRequest());
    }

    private void sendLoginRequest() {
        try {
            Socket socket = new Socket(topTextField.getText(), Integer.parseInt(bottomTextField.getText()));
            message.setLineSpacing(-10.0);
            loginLog.setTextFill(Color.color(0,0.6,0));
            loginLog.setText("Connection successful!");
            loginLog.setVisible(true);
            NetworkHandler networkHandler = new NetworkHandler(socket, view);
            view.addNetworkHandler(networkHandler);
            Thread networkThread = new Thread(networkHandler);
            networkThread.start();

            topLabel.setText("Game ID");
            bottomLabel.setText("Nickname");
            topTextField.setPromptText("Game ID");
            bottomTextField.setPromptText("Nickname");
            logInButton.setText("Join");
            topTextField.clear();
            bottomTextField.clear();

            logInButton.setOnAction( actionEvent -> Platform.runLater(()-> {
                String nickname = bottomTextField.getText();
                String gameId = topTextField.getText();

                if(nickname.replaceAll(" ", "").length() > 0 && nickname.length() < 20 && gameId.replaceAll(" ", "").length() > 0) {
                    view.setNickname(nickname);
                    super.networkHandler.sendMessage(new LoginRequestMessage(gameId, nickname));
                    loginLog.setVisible(false);
                } else {
                    loginLog.setVisible(false);
                    loginLog.setTextFill(Color.color(1,0,0));
                    loginLog.setText("Invalid gameId or nickname");
                    loginLog.setVisible(true);
                }
            }));

        } catch (IOException | NumberFormatException e) {
            loginLog.setTextFill(Color.color(1,0,0));
            loginLog.setText("Connection error!");
            loginLog.setVisible(true);
        }

    }

    private void setAllNotVisible(){

        logInButton.setVisible(false);
        logInButton.setDisable(true);
        bottomTextField.setVisible(false);
        topTextField.setVisible(false);
        topLabel.setVisible(false);
        bottomLabel.setVisible(false);
        loginLog.setVisible(false);
        message.setVisible(false);

    }

    @Override
    public void visitNicknameAlreadyUsed(String str, String gameID) {
        message.setText(str);
        message.setVisible(true);
        topTextField.setVisible(false);
        topLabel.setVisible(false);
    }

    @Override
    public void visitLobbyFull(String str) {
        message.setText(str);
        message.setVisible(true);
        bottomTextField.setVisible(false);
        bottomLabel.setVisible(false);
    }

    @Override
    public void visitLobbyNotReady(String str) {
        message.setText(str);
        message.setVisible(true);
        bottomTextField.setVisible(false);
        bottomLabel.setVisible(false);
    }

    @Override
    public void visitLoginSuccess(String currentPlayers) {
        setAllNotVisible();
        message.setText("Login success!\n" +
                "Current players:\n" + currentPlayers);
        message.setPrefHeight(300);
        message.setVisible(true);
    }

    @Override
    public void visitForcedReconnectionUpdate() {
        view.changeScene(view.scenesMap.get(GUI.MAIN_GUI));
    }

    @Override
    public void visitRequestLobbySize(String str) {
        message.setText(str);
        message.setVisible(true);
        topLabel.setText("Lobby size");
        bottomLabel.setVisible(false);
        topTextField.setPromptText("Size");
        bottomTextField.setVisible(false);
        logInButton.setText("Create lobby");
        topTextField.clear();
        bottomTextField.clear();
        logInButton.setOnAction( actionEvent -> Platform.runLater(()-> {
            try {
                int dim = Integer.parseInt(topTextField.getText().trim());
                if (dim > 0 && dim < 5)
                    this.networkHandler.sendMessage(new LoginSizeMessage(dim));
                else
                    throw new Exception();
            }catch(Exception e){
                loginLog.setText("Invalid lobby size, please retry");
                loginLog.setTextFill(Color.color(1, 0, 0));
                loginLog.setVisible(true);

            }
        }));
    }
}
