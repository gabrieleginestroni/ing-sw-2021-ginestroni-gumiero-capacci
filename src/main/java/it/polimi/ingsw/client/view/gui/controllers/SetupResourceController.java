package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.server.messages.client_server.ChosenInitialResourcesMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that manages the setup resource scene
 */
public class SetupResourceController extends GUIController implements Initializable {
    @FXML
    private Rectangle rectangle;
    @FXML
    private Rectangle popUpEffect;
    @FXML
    private StackPane popUp;
    @FXML
    private Button abortBtn;
    @FXML
    private Button depot0;
    @FXML
    private Button depot1;
    @FXML
    private Button depot2;
    @FXML
    private Button coinButton;
    @FXML
    private Button servantButton;
    @FXML
    private Button stoneButton;
    @FXML
    private Button shieldButton;
    @FXML
    private Label message;
    @FXML
    private Label popUpMessage;
    @FXML
    private ImageView depotImg0;
    @FXML
    private ImageView depotImg1_0;
    @FXML
    private ImageView depotImg1_1;
    @FXML
    private ImageView depotImg2_0;
    @FXML
    private ImageView depotImg2_1;

    private int requestedQty;
    private int chosenQty;
    private int chosenDepot;
    private final Map<Integer, Integer> resMap = new HashMap<>();

    /**
     * @param ind Depot to place the resource
     */
    private void chooseDepot(int ind){
        chosenDepot = ind;

        popUp.setEffect(new DropShadow(20, Color.BLACK));
        popUpMessage.setText("Choose the resource you want to put in the depot number " + ind);
        popUpEffect.setVisible(true);
        popUp.setVisible(true);
    }

    /**
     * @param res Chosen resource
     */
    private void chooseResource(int res){
        if(chosenQty == 0) {
            resMap.put(res, chosenDepot);
            this.setChosenRes(res, chosenDepot, 0);
            chosenQty++;
        } else{
            for(Map.Entry<Integer,Integer> entry : resMap.entrySet()){
                if(entry.getKey() == res && entry.getValue() == chosenDepot && (chosenDepot == 1 || chosenDepot == 2)) {
                    this.setChosenRes(res, chosenDepot, 1);
                    chosenQty++;
                } else if(entry.getKey() != res && chosenDepot != entry.getValue()){
                    resMap.put(res,chosenDepot);
                    this.setChosenRes(res, chosenDepot, 0);
                    chosenQty++;
                }
            }
        }

        this.disablePopUp();

        if(chosenQty == requestedQty) {
            message.setTextFill(new Color(0, 0, 0, 1));
            message.setText("Please wait for other players");
            this.disableDepotButtons();
            networkHandler.sendMessage(new ChosenInitialResourcesMessage(resMap));
        }else{
            String s = requestedQty > 1 ? "s" : "";
            message.setTextFill(new Color(0, 0, 0, 1));
            message.setText("Choose "+ requestedQty +" resource" + s + " and the depot where to store it");
        }
    }

    /**
     *
     * @param res chosen resource
     * @param dep chosen depot
     * @param col chosen field
     */
    private void setChosenRes(int res, int dep, int col){
        String str = "";
        switch(res){
            case 0:
                str = "coin";
                break;
            case 1:
                str = "servant";
                break;
            case 2:
                str = "stone";
                break;
            case 3:
                str = "shield";
                break;
        }

        switch(dep){
            case 0:
                depotImg0.setImage(new Image("/images/resources/" + str + ".png"));
                depotImg0.setVisible(true);
                break;
            case 1:
                if(col == 0){
                    depotImg1_0.setImage(new Image("/images/resources/" + str + ".png"));
                    depotImg1_0.setVisible(true);
                }else{
                    depotImg1_1.setImage(new Image("/images/resources/" + str + ".png"));
                    depotImg1_1.setVisible(true);
                }
                break;
            case 2:
                if(col == 0){
                    depotImg2_0.setImage(new Image("/images/resources/" + str + ".png"));
                    depotImg2_0.setVisible(true);
                }else{
                    depotImg2_1.setImage(new Image("/images/resources/" + str + ".png"));
                    depotImg2_1.setVisible(true);
                }
                break;
        }
    }

    /**
     * Disable popup
     */
    private void disablePopUp(){
        popUpEffect.setVisible(false);
        popUp.setVisible(false);
    }

    /**
     * Disable depot buttons
     */
    private void disableDepotButtons(){
        depot0.setDisable(true);
        depot1.setDisable(true);
        depot2.setDisable(true);
    }

    /**
     * Initialize scene displaying warehouse image and resource to choose
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestedQty = 0;
        chosenQty = 0;
        chosenDepot = -1;

        rectangle.setArcWidth(30.0);
        rectangle.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(
                new Image("/images/warehouse.png", 350, 360, true, true)
        );

        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.BLACK));
        rectangle.setVisible(true);

        coinButton.setOnAction(ActionEvent -> chooseResource(0));
        servantButton.setOnAction(ActionEvent -> chooseResource(1));
        stoneButton.setOnAction(ActionEvent -> chooseResource(2));
        shieldButton.setOnAction(ActionEvent -> chooseResource(3));
    }

    /**
     * Enable depot buttons in wharehouse to choose where to place the resource
     * @param quantity number of resources to choose
     */
    @Override
    public void visitInitialResource(int quantity) {
        requestedQty = quantity;

        String s = requestedQty > 1 ? "s" : "";
        message.setText("Choose "+ requestedQty +" resource" + s + " and the depot where to store it");

        depot0.setOnAction(ActionEvent -> chooseDepot(0));
        depot1.setOnAction(ActionEvent -> chooseDepot(1));
        depot2.setOnAction(ActionEvent -> chooseDepot(2));

        depot0.setDisable(false);
        depot1.setDisable(false);
        depot2.setDisable(false);
    }

    /**
     * Show game abort message and display exit button
     */
    @Override
    public void visitGameAbort() {
        abortBtn.setOnAction(actionEvent -> Platform.runLater(() -> {
            System.exit(0);
        }));
        abortBtn.setDisable(false);
        abortBtn.setVisible(true);

        disableDepotButtons();
        disablePopUp();
        message.setText("    Game Aborted");
    }
}
