package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

/**
 * @author Gabriele Ginestroni
 * Class that represents an observer of the market
 */
public class MarketObserver {
    private String[][] market;
    private String freeMarble;
    private transient final VirtualView virtualView;

    /**
     * Creates an empty market observer
     * @param virtualView Virtual view of the game which the market is part of
     */
    public MarketObserver(VirtualView virtualView) {
        this.market = new String[3][4];
        this.freeMarble = null;
        this.virtualView = virtualView;
    }

    /**
     * Changes the market status and notifies to the virtual view a change of the market status
     * @param newMarketGrid New market grid
     * @param newFreeMarble New free marble
     */
    public void notifyMarketChange(String[][] newMarketGrid, String newFreeMarble){
        this.market = newMarketGrid;
        this.freeMarble = newFreeMarble;
        //virtual view could be null in some low level observer tests that don't
        //instantiate a game
        if(virtualView != null) virtualView.updateMarketVirtualView();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for(int i = 0; i < 3; i++) {
            str.append("[  ");
            for (int j = 0; j < 4; j++) {
                StringBuilder s = new StringBuilder("");
                for(int h = market[i][j].length(); h < 10; h++)
                    s.append(" ");
                str = str.append(market[i][j]).append(s);
            }
            str.append("]\n");
        }

        return "MarketObserver{" +
                "market=" + str +
                "freeMarble='" + freeMarble + '\'' +
                '}';
    }

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
