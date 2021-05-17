package it.polimi.ingsw.server.virtual_view;

import com.google.gson.Gson;
import it.polimi.ingsw.server.ClientHandler;

import it.polimi.ingsw.server.controller.Player;

import it.polimi.ingsw.server.messages.client_server.ChosenInitialResourcesMessage;
import it.polimi.ingsw.server.messages.client_server.ChosenLeaderMessage;
import it.polimi.ingsw.server.messages.client_server.Message;
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

    public void updateBoardVirtualView() {
        players.stream().forEach(p -> {
            BoardsUpdateMessage message = new BoardsUpdateMessage();
            message.addPersonalBoard(p.getBoardObserver().toJSONString());
            players.stream().filter(q -> p != q).forEach(q -> {
                message.addOtherBoard(q.getBoardObserver().toJSONHandFreeString());
            });
            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });
    }

    public void updateMarketVirtualView(){
        String marketJSON = this.marketObserver.toJSONString();
        MarketUpdateMessage message = new MarketUpdateMessage(marketJSON);
        //TODO
        //TESTING
        players.stream().forEach(p -> {
            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();

            }
        });
    }

    public void updateLorenzoVirtualView() {
        String lorenzoJSON = this.lorenzoObserver.toJSONString();
        LorenzoUpdateMessage message = new LorenzoUpdateMessage(lorenzoJSON);
        try {
            players.get(0).getClientHandler().sendAnswerMessage(message);
        } catch (IOException | NullPointerException e) {
            //TODO
            //p.getClientHandler().sendErrorMessage();

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
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();

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
            //TODO
            //p.getClientHandler().sendErrorMessage();
            return null;
        }
    }

    public void updatePersonalBoardVirtualView(String nickname) {
        players.stream().filter(p -> p.getNickname().equals(nickname)).forEach(p -> {

            BoardsUpdateMessage message = new BoardsUpdateMessage();
            message.addPersonalBoard(p.getBoardObserver().toJSONString());

            try {
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });
    }

    public void updateInkwellView(String nickname){
        InkwellMessage message = new InkwellMessage(nickname);
        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
            //TODO
            //p.getClientHandler().sendErrorMessage();
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
            //TODO
            //p.getClientHandler().sendErrorMessage();
        }
        return resMap;
    }

    public void gameStarted(){
        GameStartedMessage message = new GameStartedMessage();

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }

    public void startTurn(String currentPlayerNickname,String errorMessage){
        StartTurnStateMessage message = new StartTurnStateMessage(currentPlayerNickname,errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }

    public void marketAction(String currentPlayerNickname){
        MarketStateMessage message = new MarketStateMessage(currentPlayerNickname);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }

    public void devCardSaleAction(String currentPlayerNickname){
        DevCardSaleStateMessage message = new DevCardSaleStateMessage(currentPlayerNickname);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }



    public void productionAction(String currentPlayerNickname){
        ActivateProductionStateMessage message = new ActivateProductionStateMessage(currentPlayerNickname);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }

    public void leaderAction(String currentPlayerNickname){
        LeaderActionStateMessage message = new LeaderActionStateMessage(currentPlayerNickname);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }
    public void middleTurn(String currentPlayerNickname,String errorMessage){
        MiddleTurnStateMessage message = new MiddleTurnStateMessage(currentPlayerNickname,errorMessage);

        players.stream().forEach(p -> {
            try{
                p.getClientHandler().sendAnswerMessage(message);
            } catch (IOException | NullPointerException e) {
                //TODO
                //p.getClientHandler().sendErrorMessage();
            }
        });

    }







    public String toJSONString(){
        return new Gson().toJson(this);
    }
}