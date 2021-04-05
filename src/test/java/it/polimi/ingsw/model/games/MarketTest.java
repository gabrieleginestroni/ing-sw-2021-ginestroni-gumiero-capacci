package it.polimi.ingsw.model.games;

import junit.framework.TestCase;

public class MarketTest extends TestCase {

    public void testGetFreeMarble() {
        Market m = new Market();
        assertEquals("white", m.getFreeMarble().getColor());
    }

    public void testGetLayout() {
    }

    public void testDoHorizontalMove() {
        Market m = new Market();
        Marble prec = m.getLayout()[0][1];
        m.doHorizontalMove(0);
        assertEquals(m.getLayout()[0][0], prec);
    }

    public void testDoVerticalMove() {
        Market m = new Market();
        Marble prec = m.getLayout()[1][0];
        m.doVerticalMove(0);
        assertEquals(m.getLayout()[0][0], prec);
    }
}