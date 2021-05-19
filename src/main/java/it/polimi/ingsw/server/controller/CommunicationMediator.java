package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Resource;

import java.util.HashMap;
import java.util.Map;

public class CommunicationMediator {
    private boolean mainActionDone;
    private boolean leaderActionDone;
    private Map<Resource, Integer> productionOutputs;

    public CommunicationMediator() {
        this.mainActionDone = false;
        this.leaderActionDone = false;
        productionOutputs = new HashMap<>();
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
    }

    public void addProductionOutputs(Map<Resource, Integer> res){
        for(Map.Entry<Resource, Integer> entry: res.entrySet()){
            int prevQty = 0;
            if(productionOutputs.get(entry.getKey()) != null)
                prevQty = productionOutputs.get(entry.getKey());
            productionOutputs.put(entry.getKey(), prevQty + entry.getValue());
        }
    }
}
