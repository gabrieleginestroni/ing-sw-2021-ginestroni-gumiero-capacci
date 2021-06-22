package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.messages.client_server.ChosenDevCardToPurchaseMessage;
import it.polimi.ingsw.server.model.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;



public class DevelopmentController extends GUIController implements Initializable {

    @FXML
    private Button changeSceneButton;
    @FXML
    private BorderPane pane;
    @FXML
    private Label textMessage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/images/table_background.jpg",1490.0,810.0,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

        changeSceneButton.setOnAction( actionEvent -> Platform.runLater(()-> view.changeScene(view.scenesMap.get(GUI.MAIN_GUI))));

        StackPane devPane;
        for(int i = 0; i < 3 ; i++) {
            for(int j = 0; j < 4; j++) {
                devPane = (StackPane) pane.lookup("#dev_" + i + "_" + j);
                int finalI = i;
                int finalJ = j;
                devPane.setOnMouseClicked(mouseEvent -> {
                    chosenRow = finalI;
                    chosenCol = finalJ;
                    textMessage.setText("Selected ROW: "+chosenRow+" COL: "+chosenCol+"\n Please choose a card slot and resources ");
                    view.controllersMap.get(GUI.MAIN_GUI).
                    view.changeScene(view.scenesMap.get(GUI.MAIN_GUI));
                    textMessage.setVisible(true);
                });

            }
        }
    }

    @Override
    public void visitDevGridUpdate() {
        int[][] devGrid = view.getDevGridView().getGrid();
        StackPane devPane;
        ImageView devImg;
        for(int i = 0; i < 3 ; i++) {
            for(int j = 0; j < 4; j++) {
                 devPane = (StackPane) pane.lookup("#dev_"+i+"_"+j);
                 devImg = (ImageView) devPane.getChildren().get(0);
                 if(devGrid[i][j] != 0){  //0 means that no cards a remaining in that slot
                     devImg.setImage(GUI.developmentCardImg[devGrid[i][j]]);
                 } else
                     devImg.setImage(GUI.developmentCardImg[0]);
            }
        }
    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) {
        if(currentPlayerNickname.equals(view.getNickname())) {
            textMessage.setText("Choose a card to buy");
            chosenCol = -1;
            chosenRow = -1;
        }else{
            textMessage.setText(currentPlayerNickname + " is purchasing a development card");
        }
    }

}
