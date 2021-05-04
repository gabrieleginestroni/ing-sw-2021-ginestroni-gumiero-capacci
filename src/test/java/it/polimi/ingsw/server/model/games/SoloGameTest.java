package it.polimi.ingsw.server.model.games;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.exceptions.emptyDevCardGridSlotSelectedException;
import it.polimi.ingsw.server.virtual_view.LorenzoObserver;
import it.polimi.ingsw.server.virtual_view.VirtualView;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoloGameTest {

    @Test
    public void TestLorenzo() throws emptyDevCardGridSlotSelectedException {
        Player pl = new Player("giagum",null);
        VirtualView vv = new VirtualView();
        SoloGame solo = new SoloGame(pl,vv);
        LorenzoObserver lo = solo.getLorenzoObserver();
        JsonObject LorenzoObserverJSON;
        String lastActionToken;

        solo.removeCardFromGrid(0,0);
        solo.removeCardFromGrid(0,1);
        solo.removeCardFromGrid(0,2);
        solo.removeCardFromGrid(0,3);

        while(!solo.isGameOver())
            solo.drawFromTokenPile();

        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        lastActionToken = new Gson().fromJson(LorenzoObserverJSON.get("lastDrawnActionToken"), String.class);
        assertTrue(lastActionToken.equals("Discarded 2 Blue") ||
                lastActionToken.equals("Discarded 2 Green") ||
                lastActionToken.equals("Discarded 2 Yellow") ||
                lastActionToken.equals("Discarded 2 Purple") ||
                lastActionToken.equals("Added 2 Faith Points") ||
                lastActionToken.equals("Gave Lorenzo 1 Faith Point and shuffled Action Tokens pile"));
    }
}