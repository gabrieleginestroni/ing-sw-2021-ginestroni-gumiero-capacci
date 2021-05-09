package it.polimi.ingsw.client.view;

import java.util.Arrays;

public class GridView {
    private int[][] grid;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for(int i = 0; i < 3; i++) {
            str.append("[  ");
            for (int j = 0; j < 4; j++) {
                if(grid[i][j] < 10)
                    str.append(" ");
                str = str.append(grid[i][j]).append("  ");
            }
            str.append("]\n");
        }

        return "GridView{" +
                "grid=" + str +
                '}';
    }
}
