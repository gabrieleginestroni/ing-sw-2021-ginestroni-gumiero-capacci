package it.polimi.ingsw.model.games;

import it.polimi.ingsw.controller.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoloGameTest {

    @Test
    public void TestLorenzo() {
        Player pl = new Player("lel", 12, "lul");
        SoloGame solo = new SoloGame(pl);

        ActionTokensPile atp = new ActionTokensPile();
        ActionToken at = atp.drawPile(solo);
        //TODO
    }

    @Test
    public void drawFromTokenPile() {
    }

    @Test
    public void shuffleTokenPile() {
    }

    @Test
    public void discard2Cards() {
    }

    @Test
    public void removeCardFromGrid() {
    }
}