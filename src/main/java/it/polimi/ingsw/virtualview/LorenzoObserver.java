package it.polimi.ingsw.virtualview;

public class LorenzoObserver {
    private int blackCrossMarker;


    public LorenzoObserver() {
        this.blackCrossMarker = 0 ;
    }

    public void notifyLorenzoStatus(int newBlackCross){
        this.blackCrossMarker=newBlackCross;
    }
}
