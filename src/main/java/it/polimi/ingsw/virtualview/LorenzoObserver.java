package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;

public class LorenzoObserver {

    private int blackCrossMarker;

    public LorenzoObserver() {
        this.blackCrossMarker = 0 ;
    }

    public void notifyLorenzoStatus(int newBlackCross){
        this.blackCrossMarker = newBlackCross;
    }

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
