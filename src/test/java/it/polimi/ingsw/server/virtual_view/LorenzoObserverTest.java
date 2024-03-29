package it.polimi.ingsw.server.virtual_view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.model.games.SoloGame;
import org.junit.Test;

import static org.junit.Assert.*;

public class LorenzoObserverTest {

    @Test
    public void TestAddFaithLorenzo(){
        Player pl = new Player("giagum",null);
        VirtualView vv = new VirtualView();
        SoloGame solo = new SoloGame(pl,vv);
        LorenzoObserver lo = solo.getLorenzoObserver();
        JsonObject LorenzoObserverJSON;
        int blackCrossMarker;

        assertEquals(-1, solo.addFaithLorenzo(3));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(3, blackCrossMarker);

        assertEquals(-1, solo.addFaithLorenzo(3));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(6, blackCrossMarker);

        assertEquals(0, solo.addFaithLorenzo(2));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(8, blackCrossMarker);

        assertEquals(-1, solo.addFaithLorenzo(2));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(10, blackCrossMarker);

        assertEquals(-1, solo.addFaithLorenzo(2));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(12, blackCrossMarker);

        assertEquals(1, solo.addFaithLorenzo(4));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(16, blackCrossMarker);

        assertEquals(-1, solo.addFaithLorenzo(2));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(18, blackCrossMarker);

        assertEquals(-1, solo.addFaithLorenzo(2));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(20, blackCrossMarker);

        assertEquals(2, solo.addFaithLorenzo(4));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(24, blackCrossMarker);

        assertEquals(-1, solo.addFaithLorenzo(2));
        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertEquals(24, blackCrossMarker);
    }

    @Test
    public void TestDrawPile(){
        Player pl = new Player("giagum",null);
        VirtualView vv = new VirtualView();
        SoloGame solo = new SoloGame(pl,vv);
        LorenzoObserver lo = solo.getLorenzoObserver();
        JsonObject LorenzoObserverJSON;
        int blackCrossMarker;

        int temp = solo.drawFromTokenPile();
        while(temp != 0)
            temp = solo.drawFromTokenPile();

        LorenzoObserverJSON = JsonParser.parseString(lo.toJSONString()).getAsJsonObject();
        blackCrossMarker = new Gson().fromJson(LorenzoObserverJSON.get("blackCrossMarker"), int.class);
        assertTrue(blackCrossMarker >= 8);
        assertTrue(solo.isSection1Reported());

    }
}