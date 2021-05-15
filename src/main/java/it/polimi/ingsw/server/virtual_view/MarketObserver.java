package it.polimi.ingsw.server.virtual_view;

import com.google.gson.Gson;

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

        virtualView.updateMarketVirtualView();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for(int i = 0; i < 3; i++) {
            str.append("[  ");
            for (int j = 0; j < 4; j++)
                str.append(market[i][j]).append(" ".repeat(Math.max(0, 10 - market[i][j].length())));
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