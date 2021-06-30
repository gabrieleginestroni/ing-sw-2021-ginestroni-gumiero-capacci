package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Abstract class that represents the concept of the view used by clients.
 */
public abstract class View {
    GridView devGrid;
    BoardView personalBoardView;
    List<BoardView> otherBoardsView;
    LorenzoView lorenzoView;
    MarketView marketView;
    NetworkHandler networkHandler;
    boolean gameOver;

    DevelopmentCard[] developmentCards;
    LeaderCard[] leaderCards;

    String nickname;

    View(){
        Gson gson = new Gson();

        try{

            //Reading LeaderCards
            Reader reader = new InputStreamReader(LeaderCard.class.getResourceAsStream("/LeaderCards.json"));
            leaderCards = gson.fromJson(reader, LeaderCard[].class);

            //Reading DevelopmentCards
            reader = new InputStreamReader(DevelopmentCard.class.getResourceAsStream("/DevelopmentCards.json"));
            developmentCards = gson.fromJson(reader, DevelopmentCard[].class);

            reader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }
        this.gameOver = false;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Scanner getScanner(){ return null;}
    public boolean isGameOver(){return this.gameOver;}
    public String getNickname(){return this.nickname;}
    public List<BoardView> getOtherBoardsView(){ return this.otherBoardsView;}
    public BoardView getPersonalBoardView(){ return this.personalBoardView;}
    public LorenzoView getLorenzoView(){ return this.lorenzoView;}
    public GridView getDevGridView(){return this.devGrid;}

    public MarketView getMarketView() {
        return marketView;
    }

    public void showMessage(String str) {
        System.out.println(str);
    }
    public void addNetworkHandler(NetworkHandler networkHandler) { this.networkHandler = networkHandler;}

    public abstract void visitGameAbort();
    public abstract void visitPlayerDisconnection(String nickname);
    public abstract void visitPlayerReconnection(String nickname);

    public abstract void visitBoardsUpdate(String personalBoard, List<String> otherBoards);
    public abstract void visitDevGridUpdate(String updatedGrid);
    public abstract void visitLorenzoUpdate(String updatedLorenzo);
    public abstract void visitMarketUpdate(String updatedMarket);
    public abstract void visitForcedReconnectionUpdate(String personalBoard, List<String> otherBoards,String updatedGrid,String updatedMarket);
    public abstract void visitGameStarted(String str);
    public abstract void visitInitialResource(int quantity);
    public abstract void visitInkwell(String nickname);

    public abstract void visitLeaderProposal(int[] proposedLeaderCards);
    public abstract void visitLobbyFull(String str);
    public abstract void visitLobbyNotReady(String str);
    public abstract void visitLoginSuccess(String currentPlayers);
    public abstract void visitRequestLobbySize(String str) throws invalidClientInputException;
    public abstract void visitNicknameAlreadyUsed(String str,String gameID);
    public abstract void visitWhiteMarbleProposal(Resource res1,Resource res2);


    public abstract void visitStartTurn(String currentPlayerNickname, String errorMessage);
    public abstract void visitDevCardSale(String currentPlayerNickname) throws invalidClientInputException;
    public abstract void visitMiddleTurn(String currentPlayerNickname,String errorMessage);
    public abstract void visitLeaderAction(String currentPlayerNickname);
    public abstract void visitMainActionState(String currentPlayerNickname,String errorMessage);
    public abstract void visitProductionState(String currentPlayerNickname,String errorMessage) throws invalidClientInputException;
    public abstract void visitGameOverState(String winner, Map<String, Integer> gameResult);
    public abstract void visitMarketState(String currentPlayerNickname,String errorMessage);
    public abstract void visitSwapState(String currentPlayerNickname,String errorMessage);
    public abstract void visitResourceManagementState(Resource res,String currentPlayerNickname,String errorMessage);

    /**
     * Method used to get the reference to a specified Development Card from only its ID.
     * @param cardID The Development Card ID.
     * @return The reference to the right Development Card.
     */
    public DevelopmentCard getDevelopmentCardByID(int cardID){
        if(cardID > 0 && cardID <= developmentCards.length) return this.developmentCards[cardID-1];
        return null;
    }

    /**
     * Method used to get the reference to a specified Leader Card from only its ID.
     * @param cardID The Leader Card ID.
     * @return The reference to the right Leader Card.
     */
    public LeaderCard getLeaderCardByID(int cardID){
        if(cardID > 0 && cardID <= leaderCards.length) return this.leaderCards[cardID-1];
        return null;
    }

}
