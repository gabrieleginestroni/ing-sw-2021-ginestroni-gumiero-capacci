package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.controller.Player;
import it.polimi.ingsw.model.games.SoloGame;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class MarketObserverTest {

    public void testAssertHorizontal(String[][] marketOld, String[][] marketNew, String freeMarbleOld, String freeMarbleNew, int rand){
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++){
                if(rand == i && j == 0)
                    assertEquals(marketOld[i][0], freeMarbleNew);
                else if(rand == i && j == 3)
                    assertEquals(freeMarbleOld, marketNew[i][3]);
                else if(rand == i)
                    assertEquals(marketOld[i][j], marketNew[i][j-1]);
                else
                    assertEquals(marketOld[i][j], marketNew[i][j]);
            }
    }

    public void testAssertVertical(String[][] marketOld, String[][] marketNew, String freeMarbleOld, String freeMarbleNew, int rand){
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++){
                if(rand == j && i == 0)
                    assertEquals(marketOld[0][j], freeMarbleNew);
                else if(rand == j && i == 2)
                    assertEquals(freeMarbleOld, marketNew[2][j]);
                else if(rand == j)
                    assertEquals(marketOld[i][j], marketNew[i-1][j]);
                else
                    assertEquals(marketOld[i][j], marketNew[i][j]);
            }
    }
    @Test
    public void testMarketObserver(){
        Player p1 = new Player("giagum",null);
        SoloGame solo = new SoloGame(p1);
        System.out.println(solo.getMarketObserver().toString());

        JsonObject observerJSON = JsonParser.parseString(solo.getMarketObserver().toJSONString()).getAsJsonObject();
        String[][] marketOld;
        String freeMarbleOld;

        String[][] marketNew = new Gson().fromJson(observerJSON.get("market"), String[][].class);
        String freeMarbleNew = new Gson().fromJson(observerJSON.get("freeMarble"), String.class);

        int rand;
        int randMove;
        for(int a = 0; a < 100; a++) {
            marketOld = marketNew;
            freeMarbleOld = freeMarbleNew;

            randMove = ThreadLocalRandom.current().nextInt(0, 2);
            if(randMove == 0) {
                rand = ThreadLocalRandom.current().nextInt(0, 3);
                solo.doHorizontalMoveMarket(rand);
                System.out.println("Horizontal: "+rand);
            }else {
                rand = ThreadLocalRandom.current().nextInt(0, 4);
                solo.doVerticalMoveMarket(rand);
                System.out.println("Vertical: "+rand);
            }

            observerJSON = JsonParser.parseString(solo.getMarketObserver().toJSONString()).getAsJsonObject();
            marketNew = new Gson().fromJson(observerJSON.get("market"), String[][].class);
            freeMarbleNew = new Gson().fromJson(observerJSON.getAsJsonObject().get("freeMarble"), String.class);

            if(randMove == 0)
                testAssertHorizontal(marketOld, marketNew, freeMarbleOld, freeMarbleNew, rand);
            else
                testAssertVertical(marketOld, marketNew, freeMarbleOld, freeMarbleNew, rand);
        }
        System.out.println(solo.getMarketObserver().toString());
    }
}