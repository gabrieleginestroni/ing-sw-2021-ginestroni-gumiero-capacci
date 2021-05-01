package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;

/**
 * @author Gabriele Ginestroni
 * Class that represents an observer of the game's development card grid.
 * It always cointains a coincise snapshot of the grid's top cards.
 */
public class GridObserver {
    private int[][] grid;
    private transient final VirtualView virtualView;

    /**
     * Creates an empty grid observer
     * @param virtualView Virtual view of the game which the grid is part of
     */
    public GridObserver(VirtualView virtualView) {
        this.grid = new int[3][4];
        this.virtualView = virtualView;
    }

    /**
     * Changes the grid status and notifies to the virtual view a change of the grid status
     * @param newDevGrid New development card grid
     */
    public void notifyDevelopmentGridChange(int[][] newDevGrid){
        this.grid = newDevGrid;
        //virtual view could be null in some low level observer tests that don't
        //instantiate a game
        if(virtualView != null ) virtualView.updateGridVirtualView();

    }

    public String toJSONString(){
        return new Gson().toJson(this);
    }
}
