package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;

public class LorenzoObserver {
    private int blackCrossMarker;
    private String lastDrawnActionToken;

    public LorenzoObserver() {
        this.blackCrossMarker = 0 ;
        lastDrawnActionToken = null;
    }

    public void notifyLorenzoStatus(int newBlackCross){
        this.blackCrossMarker = newBlackCross;
    }

    public void notifyLastDrawActionToken(String lastDrawnActionToken){this.lastDrawnActionToken = lastDrawnActionToken;}

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
