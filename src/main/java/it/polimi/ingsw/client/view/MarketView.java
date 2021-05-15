package it.polimi.ingsw.client.view;

public class MarketView {
    private String[][] market;
    private String freeMarble;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for(int i = 0; i < 3; i++) {
            str.append("[  ");
            for (int j = 0; j < 4; j++)
                str.append(market[i][j]).append(" ".repeat(Math.max(0, 10 - market[i][j].length())));
            str.append("]\n");
        }

        return "MarketView{" +
                "market=" + str +
                "freeMarble='" + freeMarble + '\'' +
                '}';
    }
}
