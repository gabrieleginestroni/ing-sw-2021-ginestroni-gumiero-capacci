package it.polimi.ingsw.server.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.server.exceptions.emptyDevCardGridSlotSelectedException;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.games.DevelopmentCardGrid;
import it.polimi.ingsw.server.virtualview.GridObserver;
import it.polimi.ingsw.server.virtualview.VirtualView;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DevelopmentCardGridTest {

    @Test
    public void TestCards() throws emptyDevCardGridSlotSelectedException {
        DevelopmentCard[] devCards = null;

        try {
            Gson gson = new Gson();
            Reader reader;
            reader = Files.newBufferedReader(Paths.get("src/main/resources/DevelopmentCards.json"));
            devCards = gson.fromJson(reader, DevelopmentCard[].class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VirtualView vv = new VirtualView();
        DevelopmentCardGrid grid = new DevelopmentCardGrid(devCards, new GridObserver(vv));

        for(int i = 0; i <= 2; i++)
            for(int j = 0; j <= 3; j++)
                for(int k = 0; k <= 3; k++) {
                    DevelopmentCard devCard = grid.getCard(i, j);
                    assertEquals(i, devCard.getLevel() - 1);
                    assertEquals(j, devCard.getType().getColumn());
                    grid.removeCard(i, j);
                }

        assertTrue(grid.thereAreNotRemainingCards(Color.GREEN));
        assertTrue(grid.thereAreNotRemainingCards(Color.BLUE));
        assertTrue(grid.thereAreNotRemainingCards(Color.YELLOW));
        assertTrue(grid.thereAreNotRemainingCards(Color.PURPLE));

        try{
            grid.getCard(0,0);
        }catch(emptyDevCardGridSlotSelectedException e){
            assertEquals(1,1);
        }
    }
}