package it.polimi.ingsw.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that contains all board elements
 */
public class BoardView {
    private final String nickname;
    private final ArrayList<String> whiteMarbles;
    private final ArrayList<String> discounts;
    private final List<Integer> hiddenHand;
    private final List<Integer> activeLeaders;
    private final Map<String,Integer> strongBox;
    private final ArrayList<Integer>[] cardSlot;
    private final int faithTrackMarker;
    private final boolean[] popeTiles;
    private final List<String> warehouseDepotResource;
    private final List<Integer> warehouseDepotQuantity;
    private final List<String> leaderDepotResource;
    private final List<Integer> leaderDepotQuantity;
    private boolean inkwell;

    /**
     * Initialize all elements empty except username
     * @param nickname player's username
     */
    public BoardView(String nickname) {
        this.nickname = nickname;
        whiteMarbles = null;
        discounts = null;
        hiddenHand = null;
        activeLeaders = null;
        strongBox = null;
        cardSlot = null;
        faithTrackMarker = 0;
        popeTiles = null;
        warehouseDepotResource = null;
        warehouseDepotQuantity = null;
        leaderDepotResource = null;
        leaderDepotQuantity = null;
        inkwell = false;
    }

    /**
     * @return personal pope tiles status
     */
    public boolean[] getPopeTiles() { return popeTiles; }

    /**
     * @return personal strongbox
     */
    public Map<String, Integer> getStrongBox() {
        return strongBox;
    }

    /**
     * @return list of resources in warehouse depots
     */
    public List<String> getWarehouseDepotResource() {
        return warehouseDepotResource;
    }

    /**
     * @return list of resources quantity in warehouse depots
     */
    public List<Integer> getWarehouseDepotQuantity() {
        return warehouseDepotQuantity;
    }

    /**
     * @return list of active leader cards
     */
    public List<Integer> getActiveLeaders() {
        return activeLeaders;
    }

    /**
     * @return list of development cards in card slots
     */
    public ArrayList<Integer>[] getCardSlot() {
        return cardSlot;
    }

    /**
     * @return if player has inkwell
     */
    public boolean hasInkwell() {
        return inkwell;
    }

    /**
     * @param index cardslot number
     * @return top card in required slot
     */
    public int getTopCardSlot(int index) {
        int card;
        try {
            card = cardSlot[index].get(cardSlot[index].size() - 1);
        }catch (Exception e){
            return 0;
        }
        return card;
    }

    /**
     * @return personal faith points
     */
    public int getFaithTrackMarker() {
        return faithTrackMarker;
    }

    /**
     * @return list of resources in leader depots
     */
    public List<String> getLeaderDepotResource() {
        return leaderDepotResource;
    }

    /**
     * @return list of resources quantity in leader depots
     */
    public List<Integer> getLeaderDepotQuantity() {
        return leaderDepotQuantity;
    }

    /**
     * @return player nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return list of personal active discounts
     */
    public ArrayList<String> getDiscounts() {
        return discounts;
    }

    /**
     * set inkwell to player
     */
    public void setInkwell() {
        this.inkwell = true;
    }

    /**
     * @return player list of hidden leader cards
     */
    public List<Integer> getHiddenHand() {
        return hiddenHand;
    }

    /**
     * @return string of all board's element
     */
    @Override
    public String toString() {
        return "BoardView{" +
                "nickname='" + nickname + '\'' +
                ", faithTrackMarker=" + faithTrackMarker +
                ", whiteMarbles=" + whiteMarbles  +
                ", discounts=" + discounts  +
                ", hiddenHand=" + hiddenHand  +
                ", activeLeaders=" + activeLeaders +
                ", strongBox=" + strongBox +
                ", cardSlot=" + Arrays.toString(cardSlot) +
                ", popeTiles=" + Arrays.toString(popeTiles) +
                ", warehouseDepotResource=" + warehouseDepotResource +
                ", warehouseDepotQuantity=" + warehouseDepotQuantity +
                ", leaderDepotResource=" + leaderDepotResource +
                ", leaderDepotQuantity=" + leaderDepotQuantity +
                ", inkwell=" + inkwell +
                '}';
    }
}
