package it.polimi.ingsw.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BoardView {
    private final String nickname;
    private List<Integer> hiddenHand;
    private List<Integer> activeLeaders;
    private Map<String,Integer> strongBox;
    private ArrayList<Integer>[] cardSlot;
    private int faithTrackMarker;
    private boolean[] popeTiles;
    private List<String> warehouseDepotResource;
    private List<Integer> warehouseDepotQuantity;
    private List<String> leaderDepotResource;
    private List<Integer> leaderDepotQuantity;
    private boolean inkwell;

    public BoardView(String nickname) {
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setInkwell() {
        this.inkwell = true;
    }

    @Override
    public String toString() {
        return "BoardView{" +
                "nickname='" + nickname + '\'' +
                ", hiddenHand=" + hiddenHand  +
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
                '}';
    }
}
