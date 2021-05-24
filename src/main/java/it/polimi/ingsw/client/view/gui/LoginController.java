package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.awt.event.ActionEvent;
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





    public void userLogin(ActionEvent event){
       // sendLoginRequest();
    }

    private void sendLoginRequest() throws IOException {
       /*  try {
            Socket socket = new Socket(serverIP.getText(),Integer.parseInt(serverPort.getText()));
            System.out.println(socket);
           NetworkHandler networkHandler = new NetworkHandler(socket, view);
            view.addNetworkHandler(networkHandler);

            view.showMessage("Type game ID:");
            String gameID = scanner.nextLine();
            String nickname;
            view.showMessage("Type nickname:");
            nickname = scanner.nextLine();
            while(nickname.length() > 20) {
                view.showMessage("Nickname must be < 20 chars");
                view.showMessage("Type nickname:");
                nickname = scanner.nextLine();
            }
            view.setNickname(nickname);
            networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));

            Thread networkThread = new Thread(networkHandler);
            networkThread.start();
        */
    }
}
