package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.server.messages.client_server.ChosenLeaderMessage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that manages the setup leader scene
 */
public class SetupLeaderController extends GUIController {
    @FXML
    private StackPane leader0;
    @FXML
    private StackPane leader1;
    @FXML
    private StackPane leader2;
    @FXML
    private StackPane leader3;
    @FXML
    private Label message;
    @FXML
    private Button sendLeader;
    @FXML
    private Pane pane;

    private final List<Integer> chosenLeader = new ArrayList<>();
    private int[] proposedLeader;

    /**
     * Send message containing the two chosen leaders
     */
    public void sendLeaderMessage(){
        Platform.runLater(()-> {
            int[] arr = new int[2];
            int i = 0;
            for (Integer leader : chosenLeader) {
                arr[i] = leader;
                i++;
            }
            this.networkHandler.sendMessage(new ChosenLeaderMessage(arr));
            if(view.getOtherBoardsView() != null)
                view.changeScene(view.scenesMap.get(GUI.SETUP_RESOURCE));
            else
                view.changeScene(view.scenesMap.get(GUI.MAIN_GUI));
        });
    }

    /**
     * Add image to leader pane
     * @param cardId cardId of the card
     * @param pane pane where to add the card
     */
    private void setLeaderImage(int cardId, StackPane pane){
        ImageView img = new ImageView(new Image("/images/leaderCardsFront/leader" + cardId + ".png", 170.0, 260.0, false, true));
        pane.getChildren().add(img);
    }

    /**
     * Add specified leader to chosen leaders list
     * @param i index of chosen leader
     */
    private void setChosenLeader(int i){
        if(chosenLeader.size() == 0 || i != chosenLeader.get(chosenLeader.size()-1))
            chosenLeader.add(i);
        if(chosenLeader.size() >= 2) {
            sendLeader.setDisable(false);
            if(chosenLeader.size() > 2)
                chosenLeader.remove(0);
        }

        int j = 0;
        for(Integer ignored: chosenLeader){
            ImageView leaderImage = (ImageView) pane.lookup("#chosenLeader" + j);
            leaderImage.setImage(GUI.leaderCardImg[proposedLeader[chosenLeader.get(j)]]);
            leaderImage.setVisible(true);
            j++;
        }
    }

    /**
     * Set images and bind actions of the 4 leaders
     * @param proposedLeaderCards array of cardId leaders to choose from
     */
    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {
        proposedLeader = proposedLeaderCards;

        setLeaderImage(proposedLeaderCards[0], leader0);
        setLeaderImage(proposedLeaderCards[1], leader1);
        setLeaderImage(proposedLeaderCards[2], leader2);
        setLeaderImage(proposedLeaderCards[3], leader3);

        leader0.setOnMouseClicked(ActionEvent -> setChosenLeader(0));
        leader1.setOnMouseClicked(ActionEvent -> setChosenLeader(1));
        leader2.setOnMouseClicked(ActionEvent -> setChosenLeader(2));
        leader3.setOnMouseClicked(ActionEvent -> setChosenLeader(3));
    }

    /**
     * Show game abort message and display exit button
     */
    @Override
    public void visitGameAbort(){
        leader0.setDisable(true);
        leader1.setDisable(true);
        leader2.setDisable(true);
        leader3.setDisable(true);

        sendLeader.setOnAction(actionEvent -> Platform.runLater(()-> {
            System.exit(0);
        }));
        sendLeader.setDisable(false);
        sendLeader.getStyleClass().add("button_custom_1");
        sendLeader.setText("EXIT");

        message.setText("   Game Aborted");
    }
}
