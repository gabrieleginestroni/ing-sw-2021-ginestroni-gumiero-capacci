package it.polimi.ingsw.server.virtual_view;

import com.google.gson.Gson;
import it.polimi.ingsw.server.ClientHandler;

import it.polimi.ingsw.server.controller.Player;

import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.messages.server_client.*;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import java.io.IOException;
import java.util.*;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that contains the observers of all the game structures of a certain game and handles the communication with clients.
 */

public class VirtualView {
    private  MarketObserver marketObserver;
    private  LorenzoObserver lorenzoObserver;
    private  GridObserver gridObserver;
    private transient List<Player> players;

    /**
     * All private attributes of this class are set to null;
     */
    public VirtualView() {
        this.marketObserver = null;
        this.lorenzoObserver = null;
        this.gridObserver = null;
        this.players = null;
    }

    /**
     * Sets the reference to a specified MarketObserver.
     * @param marketObserver The MarketObserver valid for the game this VirtualView refers to.
     */
    public void setMarketObserver(MarketObserver marketObserver){
        this.marketObserver = marketObserver;
    }

    /**
     * Sets the reference to a specified LorenzoObserver.
     * @param lorenzoObserver The LorenzoObserver valid for the game this VirtualView refers to.
     */
    public void setLorenzoObserver(LorenzoObserver lorenzoObserver){ this.lorenzoObserver = lorenzoObserver; }

    /**
     * Sets the reference to a specified GridObserver.
     * @param gridObserver The GridObserver valid for the game this VirtualView refers to.
     */
    public void setGridObserver(GridObserver gridObserver){ this.gridObserver = gridObserver;}

    /**
     * Sets the reference to a specified list of Players.
     * @param players The list of Players that take part in the game this
     *                VirtualView refers to: if the specific game is a SoloGame this list must contain only 1 player.
     */
    public void setPlayers(List<Player> players){ this.players = players;}

    /**
     * This method is used to notify all the remaining players the disconnection of a player.
     * @param nickname The nickname of the disconnected player.
     */
    public void notifyPlayerDisconnection(String nickname){
        players.stream().forEach(p -> p.getClientHandler().sendAnswerMessage(new PlayerDisconnectionMessage(nickname)));
        updateBoardVirtualView();
    }

    /**
     * This method is used to force the update of the view of a reconnected player (during the time he was disconnected the game
     * could have continued).
     * @param player The reconnected player.
     */
    public void forcedReconnectionUpdate(Player player){
        ClientHandler handler = player.getClientHandler();

        ForcedReconnectionUpdateMessage message = new ForcedReconnectionUpdateMessage(this.gridObserver.toJSONString(),this.marketObserver.toJSONString());
        message.addPersonalBoard(player.getBoardObserver().toJSONString());
        players.stream().filter(q -> player != q).forEach(q -> message.addOtherBoard(q.getBoardObserver().toJSONHandFreeString()));
        try {
            handler.sendAnswerMessage(message);
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }

        players.stream().filter(q -> player != q).forEach(q -> q.getClientHandler().sendAnswerMessage(new PlayerReconnectionMessage(player.getNickname())));
        updateBoardVirtualView();
    }

    /**
     * This method sends to all the players the updated version of the boards of all the players in the game: every player
     * receives a complete copy of his board and a HiddenHand-free copy of the boards of other players.
     */
    public void updateBoardVirtualView() {
        players.stream().forEach(p -> {
            BoardsUpdateMessage message = new BoardsUpdateMessage();
            message.addPersonalBoard(p.getBoardObserver().toJSONString());
            players.stream().filter(q -> p != q).forEach(q -> {
                message.addOtherBoard(q.getBoardObserver().toJSONHandFreeString());
            });
            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }

    /**
     * This method sends to all the players the updated version of the game Market.
     */
    public void updateMarketVirtualView(){
        String marketJSON = this.marketObserver.toJSONString();
        MarketUpdateMessage message = new MarketUpdateMessage(marketJSON);
        players.stream().forEach(p -> {
            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();

            }
        });
    }

    /**
     * This method sends to all the players the updated version of the Lorenzo class of the game.
     */
    public void updateLorenzoVirtualView() {
        String lorenzoJSON = this.lorenzoObserver.toJSONString();
        LorenzoUpdateMessage message = new LorenzoUpdateMessage(lorenzoJSON);
        try {
            players.get(0).getClientHandler().sendAnswerMessage(message);
        } catch (NullPointerException e) {
            //e.printStackTrace();

        }
    }

    /**
     * This method sends to all the players the updated version of the game DevelopmentCardGrid.
     */
    public void updateGridVirtualView(){
        String gridJSON = this.gridObserver.toJSONString();
        DevGridUpdateMessage message = new DevGridUpdateMessage(gridJSON);

        players.stream().forEach(p -> {
            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }

    /**
     * This method is used in the Leader Card setup phase of the game to propose 4 Leader Card
     * to a specific player and wait his answer.
     * @param leaderCards The list of 4 Leader Cards obtained by the Game that will be proposed to the specified player.
     * @param player The Player to which the Leader Card will be proposed.
     * @return The list that contains the Leader Cards (2) chosen by the player.
     */
    public List<LeaderCard> propose4Leader(List<LeaderCard> leaderCards, Player player) {
        int[] proposedLeaderCards = new int[4];
        ClientHandler playerHandler = player.getClientHandler();

        int[] chosenLeadersArray = new int[2];
        for (int i = 0; i < leaderCards.size(); i++)
            proposedLeaderCards[i] = leaderCards.get(i).getId();
        try {
            playerHandler.sendAnswerMessage(new LeaderProposalMessage(proposedLeaderCards));

            Message msg = playerHandler.waitMessage();

            if (msg instanceof ChosenLeaderMessage)
                chosenLeadersArray = ((ChosenLeaderMessage) msg).getChosenLeaderIndex();

            List<LeaderCard> chosenLeaderList = new ArrayList<>();
            for (int i : chosenLeadersArray)
                chosenLeaderList.add(leaderCards.get(i));

            return chosenLeaderList;
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * This method sends to the specified player the complete and updated version of his board.
     * @param nickname The nickname of the player that is going to get his view updated.
     */
    public void updatePersonalBoardVirtualView(String nickname) {
        players.stream().filter(p -> p.getNickname().equals(nickname)).forEach(p -> {

            BoardsUpdateMessage message = new BoardsUpdateMessage();
            message.addPersonalBoard(p.getBoardObserver().toJSONString());

            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }

    /**
     * This method sends to all the players information about who is the player that received the Inkwell.
     * @param nickname The nickname of the player that received the Inkwell (that means he will be the first player of the round).
     */
    public void updateInkwellView(String nickname){
        InkwellMessage message = new InkwellMessage(nickname);
        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }

    /**
     * This method is used in the Resources setup phase of the game to propose every player the right
     * amount of resources to choose and the depot where to store them. This method also waits for the specified player answer.
     * @param quantity The quantity of Resources to propose.
     * @param player The Player the resources will be proposed to.
     * @return The map that contains the Resources chosen by the player mapped with the index of the Warehouse Depot
     *         where to store them: if the specified player can choose 2 resources but wants to put them in the same index
     *         there will be only 1 entry in this map. In every other case this map will contain 1 entry for every chosen Resource.
     */
    public Map<Resource,Integer> proposeInitialResources(int quantity, Player player) {

        ClientHandler playerHandler = player.getClientHandler();
        Map<Resource,Integer> resMap = new HashMap<>();

        try {

            playerHandler.sendAnswerMessage(new InitialResourceChooseMessage(quantity));

            Message msg = playerHandler.waitMessage();

            if(msg instanceof ChosenInitialResourcesMessage) {
                Map<Integer,Integer> intMap = ((ChosenInitialResourcesMessage)msg).getChosenResources();
                for(Map.Entry<Integer,Integer> res: intMap.entrySet()){
                    resMap.put(Resource.values()[res.getKey()],res.getValue());
                }
            }

        } catch (IOException | ClassNotFoundException e){
            //e.printStackTrace();
            return null;
        }
        return resMap;
    }

    /**
     * This method is used to notify all the players the start of the game.
     */
    public void gameStarted(){
        GameStartedMessage message = new GameStartedMessage();

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players the start of the turn of a specific player.
     * @param currentPlayerNickname The nickname of the current player, the one that plays in the starting turn.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public void startTurn(String currentPlayerNickname,String errorMessage){
        StartTurnStateMessage message = new StartTurnStateMessage(currentPlayerNickname, errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players the start of a Market Action.
     * @param currentPlayerNickname The nickname of the current player, the one that requested to perform a Market Action.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public void marketAction(String currentPlayerNickname,String errorMessage){
        MarketStateMessage message = new MarketStateMessage(currentPlayerNickname,errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players the start of a Development Card Purchase Action.
     * @param currentPlayerNickname The nickname of the current player, the one that requested to perform
     *                              a Development Card Purchase Action.
     */
    public void devCardSaleAction(String currentPlayerNickname){
        DevCardSaleStateMessage message = new DevCardSaleStateMessage(currentPlayerNickname);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players the start of a Production Action.
     * @param currentPlayerNickname The nickname of the current player, the one that requested to perform
     *                              a Production Action.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public void productionAction(String currentPlayerNickname, String errorMessage){
        ActivateProductionStateMessage message = new ActivateProductionStateMessage(currentPlayerNickname, errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players the start of a Leader Action.
     * @param currentPlayerNickname The nickname of the current player, the one that requested to perform
     *                              a Leader Action.
     */
    public void leaderAction(String currentPlayerNickname){
        LeaderActionStateMessage message = new LeaderActionStateMessage(currentPlayerNickname);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players that the current player entered the Middle Turn, which is the
     * phase of the turn when the current player is choosing to perform a Leader Action or not, right before the end of his turn.
     * @param currentPlayerNickname The nickname of the current player.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public void middleTurn(String currentPlayerNickname,String errorMessage){
        MiddleTurnStateMessage message = new MiddleTurnStateMessage(currentPlayerNickname,errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to notify all the players that the current player is choosing the main action to perform.
     * @param currentPlayerNickname The nickname of the current player.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public void mainAction(String currentPlayerNickname,String errorMessage){
        MainActionStateMessage message = new MainActionStateMessage(currentPlayerNickname,errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });

    }

    /**
     * This method is used to send to all the players the final results of the game.
     * @param winner The nickname of the winner: in the case of a Solo Game where Lorenzo wins this string is set to null.
     * @param gameResult The map containing all players nickname mapped to the number of Victory Points they obtained in the game.
     */
    public void showResult(String winner, Map<String, Integer> gameResult){
        GameOverMessage message = new GameOverMessage(winner,gameResult);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }

    /**
     * This method is used to notify the current player that he has 2 White Marble Effects activated and
     * needs to choose 1 of these to apply to 1 of the White Marbles obtained with a move in the Market.
     * This method also waits for the player's answer.
     * @param res1 The Resource that represents the first active White Marble Effect of the current player.
     * @param res2 The Resource that represents the second active White Marble Effect of the current player.
     * @param currentPlayer The current player.
     * @return The Resource that the current player chose between res1 and res2.
     */
    public Resource proposeWhiteMarble(Resource res1, Resource res2, Player currentPlayer){
        ClientHandler playerHandler = currentPlayer.getClientHandler();
        try {

            playerHandler.sendAnswerMessage(new ProposeWhiteMarbleMessage(res1,res2));

            Message msg = playerHandler.waitMessage();
            boolean success = false;
            while(!success) {
                if(msg instanceof ChosenWhiteMarbleMessage)
                    success = true;
                else
                    msg = playerHandler.waitMessage();
            }

            return ((ChosenWhiteMarbleMessage) msg).getRes();

        } catch (IOException | ClassNotFoundException e){
            //e.printStackTrace();
        }
        return null;

    }

    /**
     * This method is used to notify all the players that the current player chose to perform a Swap with the Resources that
     * he has placed in his Warehouse Depots.
     * @param currentPlayerNickname The nickname of the current player.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public void proposeSwap(String currentPlayerNickname,String errorMessage){
        SwapMessage message = new SwapMessage(currentPlayerNickname,errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }

    /**
     * This method is used to notify all the players that the current player is choosing the action to perform
     * (discard, place, perform a swap) on 1 of the Resources obtained with a move in the Market. This method also
     * waits for the player's answer.
     * @param res The proposed Resource.
     * @param currentPlayer The current player.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     * @return The Integer that represents the action chose by the current player.
     */
    public int proposeMarketResource(Resource res,Player currentPlayer,String errorMessage){
        ProposeMarketResourceMessage message = new ProposeMarketResourceMessage(res,currentPlayer.getNickname(),errorMessage);
        ClientHandler playerHandler = currentPlayer.getClientHandler();
        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
       try {
           Message msg = playerHandler.waitMessage();
           boolean success = false;
           while(!success) {
               if (msg instanceof ChosenMarketDepotMessage)
                   success = true;
               else
                   msg = playerHandler.waitMessage();
           }

           return ((ChosenMarketDepotMessage) msg).getChosenDepot();

       } catch (IOException | ClassNotFoundException e){
           //e.printStackTrace();
       }
       return -1;
    }

    /**
     * This method is used to notify all the players that the game has terminate because too many players disconnected
     * or because at least 1 player disconnected during the setup phase of the game.
     */
    public void gameAbort(){
        GameAbortedMessage message = new GameAbortedMessage();
        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();
            }
        });
    }
}