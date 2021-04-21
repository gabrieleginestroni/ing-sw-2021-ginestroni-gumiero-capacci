package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

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
