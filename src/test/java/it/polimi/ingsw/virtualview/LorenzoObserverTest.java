package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.controller.Player;
import it.polimi.ingsw.model.games.SoloGame;
import org.junit.Test;

import static org.junit.Assert.*;

public class LorenzoObserverTest {

    @Test
    public void TestLorenzoObserver(){
        Player pl = new Player("lel", 80, "lul");
        SoloGame solo = new SoloGame(pl);
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
}