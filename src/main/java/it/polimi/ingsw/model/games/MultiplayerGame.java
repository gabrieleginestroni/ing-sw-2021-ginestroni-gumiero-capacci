package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Tommaso Capacci
 * Class that represents the multiplayer version of a game.
 */
public class MultiplayerGame extends Game{

    private int numPlayer;
    //private List<Player> players;

    public MultiplayerGame(){
        Gson gson = new Gson();
        try{
            //Lettura LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
            this.leaderCards = gson.fromJson(reader, LeaderCard[].class);
            for(int i = 0; i < leaderCards.length; i++){
                leaderCards[i].createPower();
            }
            //Lettura DevelopmentCards
            reader = Files.newBufferedReader(Paths.get("src/main/resources/DevelopmentCards.json"));
            this.devCards = gson.fromJson(reader, DevelopmentCard[].class);
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    /*
    public void giveInkwell(Player p){
        p.setInkwell(true);
    }
    */

}
