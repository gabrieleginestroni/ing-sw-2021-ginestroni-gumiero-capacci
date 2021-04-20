package it.polimi.ingsw.virtualview;

import java.util.HashMap;
import java.util.Map;

public class BoardObserver {
    private int[] hiddenHand;
    private int[] activeLeaders;
    private Map<String,Integer> strongBox;
    private int[] cardSlot1;
    private int[] cardSlot2;
    private int[] cardSlot3;
    private int faithTrackMarker;
    private boolean[] popeTiles;
    private String[] warehouseDepotResource;
    private int[] warehouseDepotQuantity;
    private String[] leaderDepotResource;
    private int[] leaderDepotQuantity;
    private boolean inkwell;


    public BoardObserver() {
        this.hiddenHand = new int[2];
        this.activeLeaders = new int[2];
        this.strongBox = new HashMap<>();
        this.cardSlot1 = new int[3];
        this.cardSlot2 = new int[3];
        this.cardSlot3 = new int[3];
        this.faithTrackMarker = 0;
        this.popeTiles = new boolean[3];
        this.warehouseDepotResource = new String[3];
        this.warehouseDepotQuantity = new int[3];
        this.leaderDepotResource = new String[2];
        this.leaderDepotQuantity = new int[2];
        this.inkwell = false;
    }

    //TODO
    public void notifyLeaderDiscard(int leaderCardHandIndex){}
    public void notifyLeaderActivation(int leaderCardHandIndex){}
    public void notifyDevelopmentCardPlacement(int cardId, int cardSlotIndex){}
    public void notifyFaithMarkerUpdate(int newMarker){}
    public void notifyPopeTileActivation(int popeTileIndex){}
    public void notifyWarehouseDepotUpdate(String res,int quantity, int index){}
    public void notifyLeaderDepotUpdate(String res,int quantity, int index){}
    public void notifyStrongboxUpdate(String res,int quantity){}
    public void notifyInkwellSet(){}

}
