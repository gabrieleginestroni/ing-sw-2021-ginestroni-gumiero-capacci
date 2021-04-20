package it.polimi.ingsw.virtualview;

public class MarketObserver {
    private String[][] market;
    private String freeMarble;

    public MarketObserver() {
        this.market = new String[3][4];
        this.freeMarble = null;
    }

    public void notifyMarketChange(String[][] newMarketGrid, String newFreeMarble){
        this.market = newMarketGrid;
        this.freeMarble = newFreeMarble;
    }
}
