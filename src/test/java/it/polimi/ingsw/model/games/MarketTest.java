package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Resource;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MarketTest {
    @Test
    public void TestDoHorizontalMove() {
        int row = 0;
        Market m = new Market();
        Marble[] old = { m.getLayout()[row][1], m.getLayout()[row][2], m.getLayout()[row][3], m.getFreeMarble() };
        Marble oldMarble = m.getLayout()[row][0];
        Map<Resource, Integer> gain = m.doHorizontalMove(row);
        assertArrayEquals(m.getLayout()[row], old);
        assertEquals(m.getFreeMarble(), oldMarble);
    }
    @Test
    public void TestDoVerticalMove() {
        int col = 0;
        Market m = new Market();
        Marble[] old = { m.getLayout()[1][col], m.getLayout()[2][col], m.getFreeMarble() };
        Marble oldMarble = m.getLayout()[0][col];
        m.doVerticalMove(col);
        Marble[] cur = { m.getLayout()[0][col], m.getLayout()[1][col], m.getLayout()[2][col] };
        assertArrayEquals(cur, old);
        assertEquals(m.getFreeMarble(), oldMarble);

    }

}