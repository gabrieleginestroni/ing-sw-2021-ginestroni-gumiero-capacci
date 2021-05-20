package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public Map<String, Integer> getStrongBox() {
        return strongBox;
    }

    public List<String> getWarehouseDepotResource() {
        return warehouseDepotResource;
    }

    public List<Integer> getWarehouseDepotQuantity() {
        return warehouseDepotQuantity;
    }

    public List<Integer> getActiveLeaders() {
        return activeLeaders;
    }

    public int getTopCardSlot(int index) {
        int card;
        try {
            card = cardSlot[index].get(cardSlot[index].size() - 1);
        }catch (Exception e){
            return 0;
        }
        return card;
    }

    public List<String> getLeaderDepotResource() {
        return leaderDepotResource;
    }

    public List<Integer> getLeaderDepotQuantity() {
        return leaderDepotQuantity;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<String> getDiscounts() {
        return discounts;
    }

    public void setInkwell() {
        this.inkwell = true;
    }

    public List<Integer> getHiddenHand() {
        return hiddenHand;
    }

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
