package it.polimi.ingsw.virtualview;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardObserver {
    private final List<Integer> hiddenHand;
    private final List<Integer> activeLeaders;
    private final Map<String,Integer> strongBox;
    private final List<Integer> cardSlot1;
    private final List<Integer> cardSlot2;
    private final List<Integer> cardSlot3;
    private int faithTrackMarker;
    private final boolean[] popeTiles;
    private final List<String> warehouseDepotResource;
    private final List<Integer> warehouseDepotQuantity;
    private final List<String> leaderDepotResource;
    private final List<Integer> leaderDepotQuantity;
    private boolean inkwell;
    private final String nickname;


    public BoardObserver(String nickname) {
        this.nickname = nickname;
        
        this.hiddenHand = new ArrayList<>();
        hiddenHand.add(0);
        hiddenHand.add(0);
        
        this.activeLeaders = new ArrayList<>();
        activeLeaders.add(0);
        activeLeaders.add(0);
        
        this.strongBox = new HashMap<>();
        strongBox.put(Resource.COIN.toString(),0);
        strongBox.put(Resource.SHIELD.toString(),0);
        strongBox.put(Resource.SERVANT.toString(),0);
        strongBox.put(Resource.STONE.toString(),0);
                
                
        this.cardSlot1 = new ArrayList<>();
        cardSlot1.add(0);
        cardSlot1.add(0);
        cardSlot1.add(0);
        
        this.cardSlot2 = new ArrayList<>();
        cardSlot2.add(0);
        cardSlot2.add(0);
        cardSlot2.add(0);
        
        this.cardSlot3 = new ArrayList<>();
        cardSlot3.add(0);
        cardSlot3.add(0);
        cardSlot3.add(0);
        
        this.faithTrackMarker = 0;
        
        this.popeTiles = new boolean[3];

        this.warehouseDepotResource = new ArrayList<>();
        warehouseDepotResource.add("NULL");
        warehouseDepotResource.add("NULL");
        warehouseDepotResource.add("NULL");

        this.warehouseDepotQuantity = new ArrayList<>();
        warehouseDepotQuantity.add(0);
        warehouseDepotQuantity.add(0);
        warehouseDepotQuantity.add(0);

        this.leaderDepotResource = new ArrayList<>();
        this.leaderDepotResource.add("NULL");
        this.leaderDepotResource.add("NULL");

        this.leaderDepotQuantity = new ArrayList<>();
        this.leaderDepotQuantity.add(0);
        this.leaderDepotQuantity.add(0);

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
