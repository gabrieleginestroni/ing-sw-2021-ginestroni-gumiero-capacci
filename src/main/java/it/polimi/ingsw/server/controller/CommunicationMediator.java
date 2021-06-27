package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class used to enable communication between different states during a player's turn
 */
public class CommunicationMediator {
    private boolean mainActionDone;
    private boolean leaderActionDone;
    private Map<Resource, Integer> productionOutputs;
    private Map<Resource, Integer> marketResources;
    private boolean playerWon;
    private int chosenDepot;
    private boolean marketStateEnded;
    private List<Integer> productionHistory;

    /**
     *
     * @return True if the player (in a solo game) has won during the current turn
     */
    public boolean hasPlayerWon() {
        return playerWon;
    }

    /**
     * Sets (in a solo game) the player as winner
     */
    public void setPlayerWon() {
        this.playerWon = true;
    }

    /**
     * Creates an empty mediator
     */
    public CommunicationMediator() {
        this.mainActionDone = false;
        this.leaderActionDone = false;
        productionOutputs = new HashMap<>();
        marketResources = new HashMap<>();
        productionHistory = new ArrayList<>();
        this.playerWon = false;
        this.chosenDepot = -3;
        this.marketStateEnded = false;
    }

    /**
     *
     * @return List of productions already activated in the current turn
     */
    public List<Integer> getProductionHistory() {
        return productionHistory;
    }

    /**
     *
     * @return Map of production outputs. Key is a resource, value a quantity
     */
    public Map<Resource, Integer> getProductionOutputs() {
        return productionOutputs;
    }

    /**
     *
     * @return True if the player has already done a main action
     */
    public boolean isMainActionDone() {
        return mainActionDone;
    }

    /**
     *
     * @return True if the player has already done a leader action
     */

    public boolean isLeaderActionDone() {
        return leaderActionDone;
    }

    /**
     * Sets the main action as done
     */
    public void setMainActionDone() {
        this.mainActionDone = true;
    }

    /**
     * Sets the leader action as done
     */
    public void setLeaderActionDone() {
        this.leaderActionDone = true;
    }

    /**
     * Refreshes the mediator for a new turn
     */
    public void refresh(){
        this.mainActionDone = false;
        this.leaderActionDone = false;
        productionOutputs = new HashMap<>();
        marketResources = new HashMap<>();
        productionHistory = new ArrayList<>();
        this.playerWon = false;
        this.chosenDepot = -3;
        this.marketStateEnded = false;
    }

    /**
     *
     * @param res Map of resources to add to the outputs of the production state. Key is a resource, value a quantity
     */
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

    /**
     *
     * @return Map of resource the player got from the market
     */
    public Map<Resource, Integer> getMarketResources() {
        return marketResources;
    }

    /**
     *
     * @param marketResources Map of resource the player got from the market
     */
    public void setMarketResources(Map<Resource, Integer> marketResources) {
        this.marketResources = marketResources;
    }

    /**
     * Discards all white marbles the player got from the market
     */
    public void discardWhiteMarbles(){
        marketResources.remove(Resource.WHITE);
    }

    /**
     * Substitutes one white marble with the specified resource in the market resources map
     * @param res Resource to substitute to a white marble
     */
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

    /**
     * Substitutes all white marbles with the specified resource in the market resources map
     * @param res Resource to substitute to all white marbles
     */
    public void substituteAllWhiteMarble(Resource res){
        if(marketResources.containsKey(Resource.WHITE)){
            int oldWhiteMarble = marketResources.get(Resource.WHITE);
            this.discardWhiteMarbles();
            marketResources.put(res,marketResources.getOrDefault(res,0) + oldWhiteMarble);
        }
    }

    /**
     * Removes faith from the market resource map
     * @return Faith removed
     */
    public int removeFaith(){
        int oldFaith = marketResources.getOrDefault(Resource.FAITH,0);
        if(oldFaith != 0) {
            marketResources.remove(Resource.FAITH);
            return oldFaith;
        } else
            return 0;
    }

    /**
     * Removes one resource from the market map
     * @param res Resource to remove
     */
    public void remove1Resource(Resource res){
        int oldQty = marketResources.getOrDefault(res,0);
        if(oldQty != 0) {
            marketResources.put(res,oldQty - 1);
            if(marketResources.get(res) == 0)
                marketResources.remove(res);
        }
    }

    /**
     *
     * @return Chosen resource management action
     */
    public int getChosenDepot() {
        return chosenDepot;
    }

    /**
     * Sets chosen resource management action
     * @param chosenDepot
     */
    public void setChosenDepot(int chosenDepot) {
        this.chosenDepot = chosenDepot;
    }

    /**
     *
     * @return True if market state is ended, false otherwise
     */
    public boolean isMarketStateEnded() {
        return marketStateEnded;
    }

    /**
     * Sets market state as ended
     */
    public void setMarketStateEnded() {
        this.marketStateEnded = true;
    }
}
