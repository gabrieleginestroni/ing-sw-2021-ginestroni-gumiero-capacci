package it.polimi.ingsw.model.games;
import com.google.gson.Gson;
import it.polimi.ingsw.model.Resource;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Giacomo Gumiero
 * Class that defines the Marble Grid
 */
public class Market {
    private final Marble[][] layout;
    private Marble freeMarble;

    /**
     * Pseudo-Random initialize the grid
     */
    public Market() {
        this.layout = new Marble[3][4];
        Gson gson = new Gson();
        try {
            //Reading Marbles from JSON
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/Marbles.json"));
            Marble[] marbles = gson.fromJson(reader, Marble[].class);
            List<Marble> tmpArr = Arrays.asList(marbles);
            Collections.shuffle(tmpArr);
            for (int i = 0; i < marbles.length-1; i++)
                this.layout[i/4][i%4] = tmpArr.get(i);
            this.freeMarble = tmpArr.get(marbles.length-1);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Marble the marble outside the grid
     */
    public Marble getFreeMarble() {
        return freeMarble;
    }

    /**
     *
     * @return Marble[][] layout
     */
    public Marble[][] getLayout() {
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
        Marble tmp;
        for(int i = 0; i < layout[row].length; i++){
            if(gain.containsKey(layout[row][i].getResource()))
                cur = 1 + gain.get(layout[row][i].getResource());
            else
                cur = 1;
           gain.put(layout[row][i].getResource(), cur);
        }

        //shift marbles
        tmp = this.freeMarble;
        this.freeMarble = layout[row][0];
        for(int i = 0; i < layout[row].length-1; i++)
            layout[row][i] = layout[row][i+1];
        layout[row][layout[row].length-1] = tmp;
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
        Marble tmp;

        for(int i = 0; i < layout.length; i++){
            if(gain.containsKey(layout[i][col].getResource()))
                cur = 1 + gain.get(layout[i][col].getResource());
            else
                cur = 1;
            gain.put(layout[i][col].getResource(), cur);
        }

        //shift marbles
        tmp = this.freeMarble;
        this.freeMarble = layout[0][col];
        for(int i = 0; i < layout.length-1; i++) {
            layout[i][col] = layout[i+1][col];
        }
        layout[layout.length-1][col] = tmp;
        return gain;
    }
}
