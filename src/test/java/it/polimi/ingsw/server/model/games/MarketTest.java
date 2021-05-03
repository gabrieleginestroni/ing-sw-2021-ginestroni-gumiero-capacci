package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.games.Market;
import it.polimi.ingsw.server.virtualview.MarketObserver;
import it.polimi.ingsw.server.virtualview.VirtualView;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MarketTest {
    @Test
    public void TestDoHorizontalMove() {
        int row = 0;
        VirtualView vv = new VirtualView();
        Market m = new Market(new MarketObserver(vv));
        Resource[] old = { m.getLayout()[row][1], m.getLayout()[row][2], m.getLayout()[row][3], m.getFreeMarble() };
        Resource oldMarble = m.getLayout()[row][0];
        Map<Resource, Integer> gain = m.doHorizontalMove(row);
        assertArrayEquals(m.getLayout()[row], old);
        assertEquals(m.getFreeMarble(), oldMarble);
    }
    @Test
    public void TestDoVerticalMove() {
        int col = 0;
        VirtualView vv = new VirtualView();
        Market m = new Market(new MarketObserver(vv));
        Resource[] old = { m.getLayout()[1][col], m.getLayout()[2][col], m.getFreeMarble() };
        Resource oldMarble = m.getLayout()[0][col];
        m.doVerticalMove(col);
        Resource[] cur = { m.getLayout()[0][col], m.getLayout()[1][col], m.getLayout()[2][col] };
        assertArrayEquals(cur, old);
        assertEquals(m.getFreeMarble(), oldMarble);
    }
}