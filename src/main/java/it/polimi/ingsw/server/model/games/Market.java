package it.polimi.ingsw.server.model.games;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.virtualview.MarketObserver;

import java.util.*;

/**
 * @author Giacomo Gumiero
 * Class that defines the Marble Grid
 */
public class Market {
    private final Resource[][] layout;
    private Resource freeMarble;
    private final MarketObserver marketObserver;

    /**
     * Pseudo-Random initialize the grid
     */
    public Market(MarketObserver marketObserver) {
        this.marketObserver = marketObserver;
        this.layout = new Resource[3][4];
        //Gson gson = new Gson();
        try {
            //Reading Marbles from JSON
            //Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/Marbles.json"));
            //Resource[] marbles = gson.fromJson(reader, Resource[].class);

            Resource[] marbles = {Resource.COIN, Resource.COIN, Resource.WHITE, Resource.WHITE, Resource.WHITE, Resource.WHITE, Resource.FAITH, Resource.SERVANT, Resource.SERVANT, Resource.STONE, Resource.STONE, Resource.SHIELD, Resource.SHIELD};
            List<Resource> tmpArr = Arrays.asList(marbles);

            Collections.shuffle(tmpArr);
            for (int i = 0; i < marbles.length-1; i++)
                this.layout[i/4][i%4] = tmpArr.get(i);
            this.freeMarble = tmpArr.get(marbles.length-1);
        }catch(Exception e) {
            e.printStackTrace();
        }
        //marketObserver.notifyMarketChange(getColorLayout(), freeMarble.getColor());
    }

    public String[][] getColorLayout(){
        String[][] marbleColor = new String[layout.length][layout[0].length];
        for(int i = 0; i < layout.length; i++)
            for(int j = 0; j < layout[0].length; j++)
                marbleColor[i][j] = layout[i][j].getColor();
        return marbleColor;
    }

    /**
     *
     * @return Marble the marble outside the grid
     */
    public Resource getFreeMarble() {
        return freeMarble;
    }

    /**
     *
     * @return Marble[][] layout
     */
    public Resource[][] getLayout() {
        return layout;
    }

    /**
     *
     * @param row the row to shift(from right to left)
     * @return Map<Resource, Integer> the Resource gained
     */
    public Map<Resource, Integer> doHorizontalMove(int row){
        Map<Resource, Integer> gain = new HashMap<>();
        int cur;
        Resource tmp;
        for(int i = 0; i < layout[row].length; i++){
            if(gain.containsKey(layout[row][i]))
                cur = 1 + gain.get(layout[row][i]);
            else
                cur = 1;
            gain.put(layout[row][i], cur);
        }

        //shift marbles
        tmp = this.freeMarble;
        this.freeMarble = layout[row][0];
        for(int i = 0; i < layout[row].length-1; i++)
            layout[row][i] = layout[row][i+1];
        layout[row][layout[row].length-1] = tmp;

        marketObserver.notifyMarketChange(getColorLayout(), freeMarble.getColor());
        return gain;
    }

    /**
     *
     * @param col the column to shift (from down to up)
     * @return Map<Resource, Integer> the Resource gained
     */
    public Map<Resource, Integer> doVerticalMove(int col){
        Map<Resource, Integer> gain = new HashMap<>();
        int cur;
        Resource tmp;

        for(int i = 0; i < layout.length; i++){
            if(gain.containsKey(layout[i][col]))
                cur = 1 + gain.get(layout[i][col]);
            else
                cur = 1;
            gain.put(layout[i][col], cur);
        }

        //shift marbles
        tmp = this.freeMarble;
        this.freeMarble = layout[0][col];
        for(int i = 0; i < layout.length-1; i++) {
            layout[i][col] = layout[i+1][col];
        }
        layout[layout.length-1][col] = tmp;

        marketObserver.notifyMarketChange(getColorLayout(), freeMarble.getColor());
        return gain;
    }
}