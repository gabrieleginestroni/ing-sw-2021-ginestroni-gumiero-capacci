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

public class VirtualView {

    private  MarketObserver marketObserver;
    private  LorenzoObserver lorenzoObserver;
    private  GridObserver gridObserver;
    private transient List<Player> players;


    public VirtualView() {

        this.marketObserver = null;
        this.lorenzoObserver = null;
        this.gridObserver = null;
        this.players = null;
    }

    public void setMarketObserver(MarketObserver marketObserver){
        this.marketObserver = marketObserver;
    }
    public void setLorenzoObserver(LorenzoObserver lorenzoObserver){ this.lorenzoObserver = lorenzoObserver; }
    public void setGridObserver(GridObserver gridObserver){ this.gridObserver = gridObserver;}
    public void setPlayers(List<Player> players){ this.players = players;}

    public void notifyPlayerDisconnection(String nickname){
        players.stream().forEach(p -> p.getClientHandler().sendAnswerMessage(new PlayerDisconnectionMessage(nickname)));
    }

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
    }

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

    public void updateLorenzoVirtualView() {
        String lorenzoJSON = this.lorenzoObserver.toJSONString();
        LorenzoUpdateMessage message = new LorenzoUpdateMessage(lorenzoJSON);
        try {
            players.get(0).getClientHandler().sendAnswerMessage(message);
        } catch (NullPointerException e) {
            //e.printStackTrace();

        }
    }

    public void updateGridVirtualView(){
        String gridJSON = this.gridObserver.toJSONString();
        DevGridUpdateMessage message = new DevGridUpdateMessage(gridJSON);
        //TODO
        //TESTING
        players.stream().forEach(p -> {
            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (NullPointerException e) {
                //e.printStackTrace();

            }
        });
    }

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
        }
        return resMap;
    }

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

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}