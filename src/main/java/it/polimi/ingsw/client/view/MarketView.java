package it.polimi.ingsw.client.view;

import java.util.Arrays;

public class MarketView {
    private String[][] market;
    private String freeMarble;

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

        return "MarketView{" +
                "market=" + str +
                "freeMarble='" + freeMarble + '\'' +
                '}';
    }
}
