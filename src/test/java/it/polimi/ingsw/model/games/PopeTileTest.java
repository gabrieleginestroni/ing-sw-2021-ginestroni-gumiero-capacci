package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.board.PopeTile;
import org.junit.Test;

import static org.junit.Assert.*;

public class PopeTileTest {

    @Test
    public void testGetVictoryPoints() {
        PopeTile pt = new PopeTile(3);

        assertEquals(3, pt.getVictoryPoints());
    }

    @Test
    public void testIsActive() {
        PopeTile pt = new PopeTile(3);

        assertFalse(pt.isActive());
    }

    @Test
    public void testSetActive() {
        PopeTile pt = new PopeTile(3);

        pt.setActive();

        assertTrue(pt.isActive());
    }

}