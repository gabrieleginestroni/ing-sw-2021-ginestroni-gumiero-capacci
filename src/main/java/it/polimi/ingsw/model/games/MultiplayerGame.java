package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.model.board.Player;
import it.polimi.ingsw.model.cards.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author Tommaso Capacci
 * Class that represents the multiplayer version of a game.
 */
public class MultiplayerGame extends Game{

    private final int numPlayer;
    private final List<Player> players;

    public MultiplayerGame(int numPlayer, List<Player> players){
        this.numPlayer = numPlayer;
        this.players = players;

        gameId = "prova multiplayer id";
        gameDate = new Date();

        market = new Market();

        Gson gson = new Gson();
        try{
            //Lettura LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
            this.leaderCards = gson.fromJson(reader, LeaderCard[].class);
            for (LeaderCard card : leaderCards) {
                card.createPower();
            }
            //Lettura DevelopmentCards
            reader = Files.newBufferedReader(Paths.get("src/main/resources/DevelopmentCards.json"));
            this.devCards = gson.fromJson(reader, DevelopmentCard[].class);
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }

        devCardsGrid = new DevelopmentCardGrid(devCards);

        gameOver = false;
    }
    /*
    public void giveInkwell(Player p){
        p.setInkwell(true);
    }
    */

}
