package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.*;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class MultiplayerGame extends Game{

    public MultiplayerGame(){
        Gson gson = new Gson();
        try{
            //Lettura LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/model/cards/LeaderCards.json"));
            this.leaderCards = gson.fromJson(reader, LeaderCard[].class);
            for(int i = 0; i < leaderCards.length; i++){
                leaderCards[i].createPower();
            }
            //Lettura DevelopmentCards
            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/model/cards/DevelopmentCards.json"));
            this.developmentCards = gson.fromJson(reader, DevelopmentCard[].class);
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        StringBuilder testo = new StringBuilder("Multiplayer Game{");
        for(int i = 0; i < leaderCards.length; i++){
            testo.append(leaderCards[i].toString() + ",\n");
        }

        return testo.toString();
    }

}
