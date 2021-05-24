package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientGUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientGUI.setStg(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("boh");
        primaryStage.setScene(new Scene(root,800,500));
        primaryStage.show();

    }
}
