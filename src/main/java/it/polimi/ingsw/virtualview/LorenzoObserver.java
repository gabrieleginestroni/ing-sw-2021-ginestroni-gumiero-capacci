package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;

public class LorenzoObserver {
    private int blackCrossMarker;
    private String lastDrawnActionToken;
    private transient final VirtualView virtualView;

    public LorenzoObserver(VirtualView virtualView) {
        this.blackCrossMarker = 0 ;
        lastDrawnActionToken = null;
        this.virtualView = virtualView;

    }

    public void notifyLorenzoStatus(int newBlackCross){
        this.blackCrossMarker = newBlackCross;
        //virtual view is null in some low level observer tests that don't
        //instantiate a game
        if(virtualView != null) virtualView.updateLorenzoVirtualView();
    }

    public void notifyLastDrawActionToken(String lastDrawnActionToken){this.lastDrawnActionToken = lastDrawnActionToken;}

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
