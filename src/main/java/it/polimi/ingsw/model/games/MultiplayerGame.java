package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.ControllerPlayer;
import it.polimi.ingsw.model.board.Player;
import it.polimi.ingsw.model.cards.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tommaso Capacci
 * Class that represents the multiplayer version of the game.
 */
public class MultiplayerGame extends Game{

    private final int numPlayer;
    private final List<Player> players;

    public MultiplayerGame(List<ControllerPlayer> controllerPlayers){

        numPlayer = controllerPlayers.size();

        players = new ArrayList<>();
        for(ControllerPlayer player : controllerPlayers){
            player.buildPlayer();
            players.add(player.getPlayer());
        }

        gameId = "multiplayer id";
        gameDate = new Date();

        market = new Market();

        Gson gson = new Gson();
        try{
            //Lettura LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
            this.leaderCards = gson.fromJson(reader, LeaderCard[].class);
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
}
