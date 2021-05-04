package it.polimi.ingsw.server.virtual_view;

import com.google.gson.Gson;

/**
 * @author Gabriele Ginestroni
 * Class that represents an observer of Lorenzo
 */
public class LorenzoObserver {
    private int blackCrossMarker;
    private String lastDrawnActionToken;
    private transient final VirtualView virtualView;

    /**
     * Creates an empty Lorenzo observer
     * @param virtualView Virtual view of the game which Lorenzo is part of
     */
    public LorenzoObserver(VirtualView virtualView) {
        this.blackCrossMarker = 0 ;
        lastDrawnActionToken = null;
        this.virtualView = virtualView;
    }

    /**
     * Changes Lorenzo's faith status and notifies to the virtual view a change of the Lorenzo status
     * @param newBlackCross New faith points value
     */
    public void notifyLorenzoStatus(int newBlackCross){
        this.blackCrossMarker = newBlackCross;

         virtualView.updateLorenzoVirtualView();
    }

    /**
     * Changes Lorenzo's last drawn token action notifies to the virtual view a change of the Lorenzo status
     * @param lastDrawnActionToken New last drawn token action
     */
    public void notifyLastDrawnActionToken(String lastDrawnActionToken){this.lastDrawnActionToken = lastDrawnActionToken;}

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
