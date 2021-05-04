package it.polimi.ingsw.server.virtual_view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.exceptions.emptyDevCardGridSlotSelectedException;
import it.polimi.ingsw.server.model.games.SoloGame;
import org.junit.Test;

import static org.junit.Assert.*;

public class GridObserverTest {

    @Test
    public void TestGridEmpty() throws emptyDevCardGridSlotSelectedException {
        Player pl = new Player("giagum",null);
        VirtualView vv = new VirtualView();
        SoloGame solo = new SoloGame(pl,vv);
        GridObserver go = solo.getGridObserver();
        JsonObject GridObserverJSON;
        int[][] grid;

        GridObserverJSON = JsonParser.parseString(go.toJSONString()).getAsJsonObject();
        grid = new Gson().fromJson(GridObserverJSON.get("grid"), int[][].class);
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                assertNotEquals(0, grid[i][j]);

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++) {
                solo.removeCardFromGrid(i, j);
                solo.removeCardFromGrid(i, j);
                solo.removeCardFromGrid(i, j);
                solo.removeCardFromGrid(i, j);

                GridObserverJSON = JsonParser.parseString(go.toJSONString()).getAsJsonObject();
                grid = new Gson().fromJson(GridObserverJSON.get("grid"), int[][].class);
                assertEquals(0, grid[i][j]);

                for(int n = 0; n < 3; n++)
                    for(int m = 0; m < 4; m++)
                        if (n < i) {
                        } else {
                            if(n == i) {
                                if (m <= j) {
                                } else
                                    assertNotEquals(0, grid[n][m]);
                            }else{
                                assertNotEquals(0, grid[n][m]);
                            }
                        }
            }
    }
}