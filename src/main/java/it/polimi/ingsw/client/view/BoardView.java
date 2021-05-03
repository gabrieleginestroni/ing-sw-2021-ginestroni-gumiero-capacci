package it.polimi.ingsw.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardView {
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
    private final String nickname;

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
}
