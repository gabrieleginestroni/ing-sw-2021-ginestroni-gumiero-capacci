package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicationMediator {
    private boolean mainActionDone;
    private boolean leaderActionDone;
    private Map<Resource, Integer> productionOutputs;
    private Map<Resource, Integer> marketResources;
    private boolean playerWon;
    private int chosenDepot;
    private boolean marketStateEnded;
    private List<Integer> productionHistory;

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon() {
        this.playerWon = true;
    }

    public CommunicationMediator() {
        this.mainActionDone = false;
        this.leaderActionDone = false;
        productionOutputs = new HashMap<>();
        marketResources = new HashMap<>();
        productionHistory = new ArrayList<>();
        this.playerWon = false;
        this.chosenDepot = -2;
        this.marketStateEnded = false;
    }

    public List<Integer> getProductionHistory() {
        return productionHistory;
    }

    public Map<Resource, Integer> getProductionOutputs() {
        return productionOutputs;
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public boolean isLeaderActionDone() {
        return leaderActionDone;
    }

    public void setMainActionDone() {
        this.mainActionDone = true;
    }

    public void setLeaderActionDone() {
        this.leaderActionDone = true;
    }

    public void refresh(){
        this.mainActionDone = false;
        this.leaderActionDone = false;
        productionOutputs = new HashMap<>();
        marketResources = new HashMap<>();
        productionHistory = new ArrayList<>();
        this.playerWon = false;
        this.chosenDepot = -2;
        this.marketStateEnded = false;
    }

    public void addProductionOutputs(Map<Resource, Integer> res){
        for(Map.Entry<Resource, Integer> entry: res.entrySet()){
            int prevQty = 0;
            if(productionOutputs.get(entry.getKey()) != null)
                prevQty = productionOutputs.get(entry.getKey());
            productionOutputs.put(entry.getKey(), prevQty + entry.getValue());
        }
        System.out.println(new Gson().toJson(res));
        System.out.println(productionOutputs);
    }

    public Map<Resource, Integer> getMarketResources() {
        return marketResources;
    }

    public void setMarketResources(Map<Resource, Integer> marketResources) {
        this.marketResources = marketResources;
    }

    public void discardWhiteMarbles(){
        marketResources.remove(Resource.WHITE);
    }
    public void substitute1WhiteMarble(Resource res){
        if(marketResources.containsKey(Resource.WHITE)){
            int oldWhiteMarble = marketResources.get(Resource.WHITE);
            if(oldWhiteMarble == 1)
                this.discardWhiteMarbles();
            else
                marketResources.put(Resource.WHITE, oldWhiteMarble - 1);
            marketResources.put(res,marketResources.getOrDefault(res,0) + 1);
        }
    }

    public void substituteAllWhiteMarble(Resource res){
        if(marketResources.containsKey(Resource.WHITE)){
            int oldWhiteMarble = marketResources.get(Resource.WHITE);
            this.discardWhiteMarbles();
            marketResources.put(res,marketResources.getOrDefault(res,0) + oldWhiteMarble);
        }
    }

    public int removeFaith(){
        int oldFaith = marketResources.getOrDefault(Resource.FAITH,0);
        if(oldFaith != 0) {
            marketResources.remove(Resource.FAITH);
            return oldFaith;
        } else
            return 0;
    }

    public void remove1Resource(Resource res){
        int oldQty = marketResources.getOrDefault(res,0);
        if(oldQty != 0) {
            marketResources.put(res,oldQty - 1);
            if(marketResources.get(res) == 0)
                marketResources.remove(res);
        }
    }


    public int getChosenDepot() {
        return chosenDepot;
    }

    public void setChosenDepot(int chosenDepot) {
        this.chosenDepot = chosenDepot;
    }

    public boolean isMarketStateEnded() {
        return marketStateEnded;
    }

    public void setMarketStateEnded() {
        this.marketStateEnded = true;
    }
}
