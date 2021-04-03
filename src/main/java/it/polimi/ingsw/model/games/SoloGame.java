package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.ControllerPlayer;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.board.Player;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class SoloGame extends Game{
    private final Lorenzo lorenzo;
    private final Player player;
    private final ActionTokensPile actionTokensPile;

    public SoloGame(ControllerPlayer controllerPlayer){

        lorenzo = new Lorenzo();

        controllerPlayer.buildPlayer();
        player = controllerPlayer.getPlayer();

        actionTokensPile = new ActionTokensPile();

        gameId = "solo id";
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

    /**
     * Method that adds Faith Points to the Black Cross indicator.
     * @param points The amount of points that must be added to the Black Cross indicator.
     * @throws vaticanReportActivated Thrown when the addition of this amount of points leads to the activation of a Vatican Report.
     */
    public void addFaithLorenzo(int points) throws vaticanReportActivated{

        lorenzo.addFaithPoints(points);

    }

    /**
     * Method that draws and applies the effect of the next Action Token from the pile used in the specific Game.
     * @throws vaticanReportActivated Thrown when the effects of the drawn Action Token leads to the activation of a Vatican Report.
     */
    public void drawFromTokenPile() throws vaticanReportActivated{
        actionTokensPile.drawPile(this);
    }

    /**
     * Method that shuffles the Action Token pile used in the specific Game.
     */
    public void shuffleTokenPile(){
        actionTokensPile.shufflePile();
    }

    /**
     * Method that propagates the request to discard 2 specific cards of a certain color from the Development Card grid.
     * @param color The color of the cards that have to be discarded.
     */
    public void discard2Cards(Color color){
        this.devCardsGrid.discard2Cards(color);
    }
}
