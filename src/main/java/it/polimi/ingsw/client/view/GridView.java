package it.polimi.ingsw.client.view;

public class GridView {
    private int[][] grid;

    public int getGridId(int row, int col) {
        return grid[row][col];
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for(int i = 0; i < 3; i++) {
            str.append("[  ");
            for (int j = 0; j < 4; j++) {
                if(grid[i][j] < 10)
                    str.append(" ");
                str.append(grid[i][j]).append("  ");
            }
            str.append("]\n");
        }

        return "GridView{" +
                "grid=" + str +
                '}';
    }

    public int[][] getGrid() {
        return grid;
    }
}
