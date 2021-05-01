package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;

public class GridObserver {
    private int[][] grid;
    private transient final VirtualView virtualView;

    public GridObserver(VirtualView virtualView) {
        this.grid = new int[3][4];
        this.virtualView = virtualView;
    }

    public void notifyDevelopmentGridChange(int[][] newDevGrid){
        this.grid = newDevGrid;
        //virtual view is null in some low level observer tests that don't
        //instantiate a game
        if(virtualView != null ) virtualView.updateGridVirtualView();

    }

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
