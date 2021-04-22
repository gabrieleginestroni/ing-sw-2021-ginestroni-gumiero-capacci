package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;

public class GridObserver {
    private int[][] grid;

    public GridObserver() {
        this.grid = new int[3][4];
    }

    public void notifyDevelopmentGridChange(int[][] newDevGrid){
        this.grid = newDevGrid;
    }

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
