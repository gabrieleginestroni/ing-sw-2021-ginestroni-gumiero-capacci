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

    /**
     * Method used to set the reference to a specific NetworkHandler in the GUI class and in every scene controller.
     * @param networkHandler The new NetworkHandler.
     */
    public void addNetworkHandler(NetworkHandler networkHandler) { this.networkHandler = networkHandler;}

    /**
     * Method used to show in the view the contents of the GameAbort message.
     */
    public abstract void visitGameAbort();

    /**
     * Method used to show the contents of a PlayerDisconnection message.
     * @param nickname The disconnected player's nickname.
     */
    public abstract void visitPlayerDisconnection(String nickname);

    /**
     * Method used to show the contents of a PlayerReconnection message.
     * @param nickname The reconnected player's nickname
     */
    public abstract void visitPlayerReconnection(String nickname);

    /**
     * Method used to show the contents of a BoardsUpdate message.
     * @param personalBoard The JSON file that represents the updated BoardView of a player's
     *                      PersonalBoard at the actual state of the game.
     * @param otherBoards The JSON file that represents the list of the updated HiddenHand-free BoardView of the PersonalBoards
     *                    of every other player at the actual state of the game.
     */
    public abstract void visitBoardsUpdate(String personalBoard, List<String> otherBoards);

    /**
     * Method used to show the contents of a DevGridUpdate message.
     * @param updatedGrid The JSON file that represents the updated GridView at the actual state of the game.
     */
    public abstract void visitDevGridUpdate(String updatedGrid);

    /**
     * Method used in a Solo Game to show the contents of a LorenzoUpdate message.
     * @param updatedLorenzo The JSON file that represents the updated LorenzoView.
     */
    public abstract void visitLorenzoUpdate(String updatedLorenzo);

    /**
     * Method used in a Solo Game to show the contents of a MarketUpdate message.
     * @param updatedMarket The JSON file that represents the updated MarketView at the actual state of the game.
     */
    public abstract void visitMarketUpdate(String updatedMarket);

    /**
     * Method used to show the contents of a ForcedReconnectionUpdate message.
     * @param personalBoard The JSON file that represents the updated BoardView of a player's
     *                      PersonalBoard at the actual state of the game.
     * @param otherBoards The JSON file that represents the list of the updated HiddenHand-free BoardView of the PersonalBoards
     *                    of every other player at the actual state of the game.
     * @param updatedGrid The JSON file that represents the updated GridView at the actual state of the game.
     * @param updatedMarket The JSON file that represents the updated MarketView at the actual state of the game.
     */
    public abstract void visitForcedReconnectionUpdate(String personalBoard, List<String> otherBoards,String updatedGrid,String updatedMarket);

    /**
     * Method used to show the contents of a GameStarted message.
     * @param str The string contained in every GameStarted message that simply
     *            says that the setup phase of the game ended and the turn of the first
     *            player in the round is beginning.
     */
    public abstract void visitGameStarted(String str);

    /**
     * Method used to show the contents of a InitialResource message.
     * @param quantity The number of resources to choose.
     */
    public abstract void visitInitialResource(int quantity);

    /**
     * Method used to show the contents of a Inkwell message.
     * @param nickname First player's nickname.
     */
    public abstract void visitInkwell(String nickname);

    /**
     * Method used to show the contents of a LeaderProposal message.
     * @param proposedLeaderCards The integer array that contains the 4 cardIDs of the proposed Leader Cards.
     */
    public abstract void visitLeaderProposal(int[] proposedLeaderCards);

    /**
     * Method used to show the contents of a LobbyFull message.
     * @param str The string contained in every LobbyFull message that simply
     *            says that the requested lobby is full.
     */
    public abstract void visitLobbyFull(String str);

    /**
     * Method used to show the contents of a LobbyNotReady message.
     * @param str The string contained in every LobbyNotReady message that simply
     *            says that the requested lobby is being created.
     */
    public abstract void visitLobbyNotReady(String str);

    /**
     * Method used to show the contents of a LoginSuccess message.
     * @param currentPlayers The string contained in every LoginSuccess message that simply
     *                       contains the nicknames of the players connected to the same lobby
     *                       at the moment the message is sent.
     */
    public abstract void visitLoginSuccess(String currentPlayers);

    /**
     * Method used to show the contents of a RequestLobbySize message.
     * @param str The string contained in every RequestLobbySize that contains the text message
     *            that has to be printed in the player's view.
     * @throws invalidClientInputException Thrown when the player inserts a badly formatted input.
     */
    public abstract void visitRequestLobbySize(String str) throws invalidClientInputException;

    /**
     * Method used to show the contents of a NicknameAlreadyUsed message.
     * @param str The string contained in every NicknameAlreadyUsed that says that
     *            the nickname chosen by the player is already used inside the requested
     *            lobby.
     * @param gameID The gameID of the game the client was previously trying to connect to.
     */
    public abstract void visitNicknameAlreadyUsed(String str,String gameID);

    /**
     * Method used to show the contents of a StartTurn message.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public abstract void visitStartTurn(String currentPlayerNickname, String errorMessage);

    /**
     * Method used to show the contents of a DevCardSale message.
     * @param currentPlayerNickname Current player's nickname.
     * @throws invalidClientInputException Thrown when the player inserts a badly formatted input.
     */
    public abstract void visitDevCardSale(String currentPlayerNickname) throws invalidClientInputException;

    /**
     * Method used to show the contents of a MiddleTurn message.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public abstract void visitMiddleTurn(String currentPlayerNickname,String errorMessage);

    /**
     * Method used to show the contents of a LeaderAction message.
     * @param currentPlayerNickname Current player's nickname.
     */
    public abstract void visitLeaderAction(String currentPlayerNickname);

    /**
     * Method used to show the contents of a MainActionState message.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public abstract void visitMainActionState(String currentPlayerNickname,String errorMessage);

    /**
     * Method used to show the contents of a ProductionState message.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     * @throws invalidClientInputException Thrown when the player inserts a badly formatted input.
     */
    public abstract void visitProductionState(String currentPlayerNickname,String errorMessage) throws invalidClientInputException;

    /**
     * Method used to show the contents of a GameOverState message.
     * @param winner Winner's nickname.
     * @param gameResult The map that contains the nicknames of every player
     *                   mapped to the number of Victory Points they obtained.
     */
    public abstract void visitGameOverState(String winner, Map<String, Integer> gameResult);

    /**
     * Method used to show the contents of a MarketState message.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public abstract void visitMarketState(String currentPlayerNickname,String errorMessage);

    /**
     * Method used to show the contents of a SwapState message.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public abstract void visitSwapState(String currentPlayerNickname,String errorMessage);

    /**
     * Method used to show the contents of a ProposeMarketResource message.
     * @param res The proposed resource.
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public abstract void visitResourceManagementState(Resource res,String currentPlayerNickname,String errorMessage);

    /**
     * Method used to show the contents of a ProposeWhiteMarble message.
     * @param res1 The resource that represents the first White Marble Effect.
     * @param res2 The resource that represents the second White Marble Effect.
     */
    public abstract void visitWhiteMarbleProposal(Resource res1,Resource res2);

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
