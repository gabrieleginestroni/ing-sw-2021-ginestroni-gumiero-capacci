package it.polimi.ingsw.model.games;

import it.polimi.ingsw.controller.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoloGameTest {

    @Test
    public void TestLorenzo() throws emptyDevCardGridSlotSelectedException {
        Player pl = new Player("lel", 12, "lul");
        SoloGame solo = new SoloGame(pl);

        solo.removeCardFromGrid(0,0);
        solo.removeCardFromGrid(0,1);
        solo.removeCardFromGrid(0,2);
        solo.removeCardFromGrid(0,3);


        String str = solo.drawFromTokenPile();
        System.out.println(str);
        while(!solo.isGameOver()){
            str = solo.drawFromTokenPile();
            System.out.println(str);
        }
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