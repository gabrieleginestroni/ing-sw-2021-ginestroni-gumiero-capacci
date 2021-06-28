package it.polimi.ingsw.client.view;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that contains development card grid
 */
public class GridView {
    private int[][] grid;

    /**
     *
     * @param row grid row
     * @param col grid column
     * @return element in position row, col
     */
    public int getGridId(int row, int col) {
        return grid[row][col];
    }

    /**
     * @return development card grid
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * @return string containing development card grid infos
     */
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
}
