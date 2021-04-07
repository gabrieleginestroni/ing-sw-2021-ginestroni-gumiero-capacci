package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.ControllerPlayer;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Tommaso Capacci
 * Class that represents the multiplayer version of the game.
 */
public class MultiplayerGame extends Game{

    private final int numPlayer;
    private final List<Board> boards;

    public MultiplayerGame(List<ControllerPlayer> controllerPlayers){

        numPlayer = controllerPlayers.size();

        boards = new ArrayList<>();
        for(ControllerPlayer player : controllerPlayers){
            player.buildBoard();
            boards.add(player.getBoard());
        }

        gameId = "multiplayer id";
        gameDate = new Date();

        market = new Market();

        LeaderCard[] tempArray = null;
        Gson gson = new Gson();
        try{

            //Reading LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
            tempArray = gson.fromJson(reader, LeaderCard[].class);

            //Reading DevelopmentCards
            reader = Files.newBufferedReader(Paths.get("src/main/resources/DevelopmentCards.json"));
            this.devCards = gson.fromJson(reader, DevelopmentCard[].class);

            reader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }

        int[] shuffleLeader = new int[tempArray.length];
        for (int i = 0; i<shuffleLeader.length; i++)
            shuffleLeader[i] = i;
        for(int max = shuffleLeader.length - 1; max > 0 ; max--){
            int randomNumber = ThreadLocalRandom.current().nextInt(0, max + 1);
            int temp = shuffleLeader[randomNumber];
            shuffleLeader[randomNumber] = shuffleLeader[max];
            shuffleLeader[max] = temp;
        }
        for(int max = shuffleLeader.length - 1; max > 0 ; max--){
            int randomNumber = ThreadLocalRandom.current().nextInt(0, max + 1);
            int temp = shuffleLeader[randomNumber];
            shuffleLeader[randomNumber] = shuffleLeader[max];
            shuffleLeader[max] = temp;
        }
        leaderCards = new ArrayList<>();
        for(int num : shuffleLeader)
            leaderCards.add(tempArray[shuffleLeader[num]]);

        devCardsGrid = new DevelopmentCardGrid(devCards);

        gameOver = false;

        section1Reported = false;
        section2Reported = false;
        section3Reported = false;

    }
}
