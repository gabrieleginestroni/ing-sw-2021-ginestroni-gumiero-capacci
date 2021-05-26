package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.view.GUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiLauncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GUI.setStg(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        GUI.stg.setTitle("Masters of Renaissance");
        GUI.stg.setResizable(false);
        GUI.stg.setScene(new Scene(root,836,588));
        GUI.stg.show();


    }
}
