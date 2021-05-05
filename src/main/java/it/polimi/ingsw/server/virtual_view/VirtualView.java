package it.polimi.ingsw.server.virtual_view;


import com.google.gson.Gson;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.messages.client_server.ChosenLeaderMessage;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.messages.server_client.*;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}