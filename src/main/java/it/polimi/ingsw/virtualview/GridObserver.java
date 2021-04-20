package it.polimi.ingsw.virtualview;

public class GridObserver {
    private int[][] grid;

    public GridObserver() {
        this.grid = new int[3][4];
    }

    public void notifyDevelopmentGridChange(int[][] newDevGrid){
        this.grid = newDevGrid;
    }
}
