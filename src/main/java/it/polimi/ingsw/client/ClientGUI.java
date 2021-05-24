package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.gui.Login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientGUI {

    private static Stage stg;

    public static void main(String[] args) {
        Login.main(args);
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("/fxml/" + fxml));
        stg.getScene().setRoot(pane);
    }

    public static void setStg(Stage stg) {
        ClientGUI.stg = stg;
    }
}
