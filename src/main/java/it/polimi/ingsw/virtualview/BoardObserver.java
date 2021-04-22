package it.polimi.ingsw.virtualview;
import com.google.gson.Gson;
import it.polimi.ingsw.model.Resource;

import java.util.*;



public class BoardObserver {
    private final List<Integer> hiddenHand;
    private final List<Integer> activeLeaders;
    private final Map<String,Integer> strongBox;
    private final ArrayList<Integer>[] cardSlot;
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
        
        this.activeLeaders = new ArrayList<>();

        this.strongBox = new HashMap<>();
        strongBox.put(Resource.COIN.toString(),0);
        strongBox.put(Resource.SHIELD.toString(),0);
        strongBox.put(Resource.SERVANT.toString(),0);
        strongBox.put(Resource.STONE.toString(),0);
                
                
        this.cardSlot = new ArrayList[3];
        cardSlot[0] = new ArrayList<>();
        cardSlot[1] = new ArrayList<>();
        cardSlot[2] = new ArrayList<>();
        

        
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


    public void notifyLeaderDiscard(int leaderCardHandIndex){
        int leaderToDiscard = hiddenHand.get(leaderCardHandIndex);
        hiddenHand.remove((Integer)leaderToDiscard);

    }

    public void notifyLeaderActivation(int leaderCardHandIndex){
        int leaderToActivate = hiddenHand.get(leaderCardHandIndex);
        hiddenHand.remove((Integer)leaderToActivate);
        activeLeaders.add(leaderToActivate);

    }

    public void notifyAddLeader(int cardId){
        hiddenHand.add(cardId);
    }

    public void notifyDevelopmentCardPlacement(int cardId, int cardSlotIndex){
        cardSlot[cardSlotIndex].add(cardId);
    }

    public void notifyFaithMarkerUpdate(int newMarker){
        faithTrackMarker = newMarker;
    }

    public void notifyPopeTileActivation(int popeTileIndex){
        popeTiles[popeTileIndex] = true;
    }

    public void notifyWarehouseDepotUpdate(String res,int newQuantity, int index){
        warehouseDepotQuantity.set(index,newQuantity);
        warehouseDepotResource.set(index,res);

    }

    public void notifyLeaderDepotUpdate(int newQuantity, int index){
        leaderDepotQuantity.set(index,newQuantity);
    }

    public void notifyLeaderDepotCreation(String res){
        int leaderDepotToCreateIndex = leaderDepotResource.indexOf("NULL");
        leaderDepotResource.set(leaderDepotToCreateIndex,res);
        leaderDepotQuantity.set(leaderDepotToCreateIndex,0);

    }
    public void notifyStrongboxUpdate(String res,int newQuantity){
        strongBox.put(res,newQuantity);

    }

    public void notifyInkwellSet(){
        inkwell = true;
    }

    @Override
    public String toString() {
        return "BoardObserver{" +
                "hiddenHand=" + hiddenHand  +
                ", activeLeaders=" + activeLeaders +
                ", strongBox=" + strongBox +
                ", cardSlot=" + Arrays.toString(cardSlot) +
                ", faithTrackMarker=" + faithTrackMarker +
                ", popeTiles=" + Arrays.toString(popeTiles) +
                ", warehouseDepotResource=" + warehouseDepotResource +
                ", warehouseDepotQuantity=" + warehouseDepotQuantity +
                ", leaderDepotResource=" + leaderDepotResource +
                ", leaderDepotQuantity=" + leaderDepotQuantity +
                ", inkwell=" + inkwell +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
