package it.polimi.ingsw.virtualview;
import com.google.gson.Gson;
import it.polimi.ingsw.model.Resource;

import java.util.*;


/**
 * @author Gabriele Ginestroni
 * Class that represents an observer of the player's board. It always cointains a coincise snapshot of the board status
 */
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
    private final transient VirtualView virtualView;


    /**
     * Creates an empty board observer
     * @param nickname Nickname of the player which the board observer belongs to
     * @param virtualView Virtual view of the game which this observer's board is part of
     *
     */
    public BoardObserver(String nickname,VirtualView virtualView) {
        this.nickname = nickname;

        this.hiddenHand = new ArrayList<>();
        this.virtualView = virtualView;
        
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

    /**
     * Removes an inactive leader card from the observer hand
     * and notifies to the virtual view a change of the board status
     * @param leaderCardHandIndex Index of the leader card discarded (referred to the hand)
     */
    public void notifyLeaderDiscard(int leaderCardHandIndex){
        int leaderToDiscard = hiddenHand.get(leaderCardHandIndex);
        hiddenHand.remove((Integer)leaderToDiscard);
        virtualView.updateBoardVirtualView();

    }

    /**
     * Moves a leader card from the hand to the list of active leader card
     * and notifies to the virtual view a change of the board status
     * @param leaderCardHandIndex Index of the leader card activated
     */
    public void notifyLeaderActivation(int leaderCardHandIndex){
        int leaderToActivate = hiddenHand.get(leaderCardHandIndex);
        hiddenHand.remove((Integer)leaderToActivate);
        activeLeaders.add(leaderToActivate);
        virtualView.updateBoardVirtualView();

    }

    /**
     * Adds an inactive leader card to the hand
     * and notifies to the virtual view a change of the board status
     * @param cardId Index of the leader card added
     */
    public void notifyAddLeader(int cardId){
        hiddenHand.add(cardId);
        virtualView.updateBoardVirtualView();
    }

    /**
     * Adds a development card to a card slot
     * and notifies to the virtual view a change of the board status
     * @param cardId Id of the development card added
     * @param cardSlotIndex Index of the card slot which the card has been placed in
     */
    public void notifyDevelopmentCardPlacement(int cardId, int cardSlotIndex){
        cardSlot[cardSlotIndex].add(cardId);
        virtualView.updateBoardVirtualView();
    }

    /**
     * Changes the faith marker
     * and notifies to the virtual view a change of the board status
     * @param newMarker New faith track marker
     */
    public void notifyFaithMarkerUpdate(int newMarker){
        faithTrackMarker = newMarker;
        virtualView.updateBoardVirtualView();
    }

    /**
     * Sets 'TRUE' to a pope tile and notifies to the virtual view a change of the board status
     * @param popeTileIndex Index of the pope tile activated
     */
    public void notifyPopeTileActivation(int popeTileIndex){
        popeTiles[popeTileIndex] = true;
        virtualView.updateBoardVirtualView();
    }

    /**
     * Changes a warehouse depot status and notifies to the virtual view a change of the board status
     * @param res New resource of the warehouse depot
     * @param newQuantity New quantity of the warehouse depot
     * @param index Index of the warehouse depot
     */
    public void notifyWarehouseDepotUpdate(String res,int newQuantity, int index){
        warehouseDepotQuantity.set(index,newQuantity);
        warehouseDepotResource.set(index,res);
        virtualView.updateBoardVirtualView();

    }

    /**
     * Changes a leader depot status and notifies to the virtual view a change of the board status
     * @param newQuantity New quantity of the leader depot
     * @param index Index of the leader depot
     */
    public void notifyLeaderDepotUpdate(int newQuantity, int index){
        leaderDepotQuantity.set(index,newQuantity);
        virtualView.updateBoardVirtualView();
    }

    /**
     * Adds an empty leader depot and notifies to the virtual view a change of the board status
     * @param res Resource of the leader depot created
     */
    public void notifyLeaderDepotCreation(String res){
        int leaderDepotToCreateIndex = leaderDepotResource.indexOf("NULL");
        leaderDepotResource.set(leaderDepotToCreateIndex,res);
        leaderDepotQuantity.set(leaderDepotToCreateIndex,0);
        virtualView.updateBoardVirtualView();

    }

    /**
     * Changes strongbox status and notifies to the virtual view a change of the board status
     * @param res Resource whose quantity has been changed
     * @param newQuantity New quantity of resource
     */
    public void notifyStrongboxUpdate(String res,int newQuantity){
        strongBox.put(res,newQuantity);
        virtualView.updateBoardVirtualView();

    }

    /**
     * Sets 'TRUE' to the inkwell and notifies to the virtual view a change of the board status
     */
    public void notifyInkwellSet(){
        inkwell = true;
        virtualView.updateBoardVirtualView();
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
