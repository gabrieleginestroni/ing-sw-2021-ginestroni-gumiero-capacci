package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

public class LoginController {
    @FXML
    private Button logInButton;
    @FXML
    private TextField gameId;
    @FXML
    private TextField serverPort;
    @FXML
    private TextField serverIP;
    @FXML
    private TextField nickName;
    @FXML
    private Label loginError;





    //public void userLogin(ActionEvent event){
    //    try {
            //sendLoginRequest();
    //    } catch (IOException e) {
    //        loginError.setText("Connection error!");
    //    }
    //}

    /*private void sendLoginRequest() throws IOException {
         try {
            networkHandler.sendMessage(new LoginRequestMessage(gameId,nickName));

            Thread networkThread = new Thread(networkHandler);
            networkThread.start();

    } */

    private void userJoin(ActionEvent event){

    }
}
