package it.polimi.ingsw.model.games;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.ControllerPlayer;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SoloGame extends Game{
    private final Lorenzo lorenzo;
    private final ControllerPlayer controllerPlayer;
    private final ActionTokensPile actionTokensPile;

    public SoloGame(ControllerPlayer controllerPlayer){

        lorenzo = new Lorenzo(this);

        this.controllerPlayer = controllerPlayer;
        this.controllerPlayer.buildBoard(this);

        actionTokensPile = new ActionTokensPile();

        gameId = "solo id";
        gameDate = new Date();

        market = new Market();

        LeaderCard[] tempArray = null;
        Gson gson = new Gson();
        try{

            //Lettura LeaderCards
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
            tempArray = gson.fromJson(reader, LeaderCard[].class);

            //Lettura DevelopmentCards
            reader = Files.newBufferedReader(Paths.get("src/main/resources/DevelopmentCards.json"));
            this.devCards = gson.fromJson(reader, DevelopmentCard[].class);

            reader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }

        int[] shuffleLeader = null;
        if(tempArray!=null)
            shuffleLeader = new int[tempArray.length];
        if(shuffleLeader!=null) {
            for (int i = 0; i < shuffleLeader.length; i++)
                shuffleLeader[i] = i;
            for (int max = shuffleLeader.length - 1; max > 0; max--) {
                int randomNumber = ThreadLocalRandom.current().nextInt(0, max + 1);
                int temp = shuffleLeader[randomNumber];
                shuffleLeader[randomNumber] = shuffleLeader[max];
                shuffleLeader[max] = temp;
            }
            for (int max = shuffleLeader.length - 1; max > 0; max--) {
                int randomNumber = ThreadLocalRandom.current().nextInt(0, max + 1);
                int temp = shuffleLeader[randomNumber];
                shuffleLeader[randomNumber] = shuffleLeader[max];
                shuffleLeader[max] = temp;
            }
            leaderCards = new ArrayList<>();
            for (int num : shuffleLeader)
                leaderCards.add(tempArray[shuffleLeader[num]]);
        }

        devCardsGrid = new DevelopmentCardGrid(devCards);

        gameOver = false;

        section1Reported = false;
        section2Reported = false;
        section3Reported = false;

    }

    /**
     * Method that adds Faith Points to the Black Cross indicator.
     * @param points The amount of points that must be added to the Black Cross indicator.
     */
    public void addFaithLorenzo(int points){

        lorenzo.addFaithPoints(points);

    }

    /**
     * Method that draws and applies the effect of the next Action Token from the pile used in the specific Game.
     */
    public void drawFromTokenPile(){
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
        if(devCardsGrid.thereAreNotRemainingCards(color))
            gameIsOver();
    }

    @Override
    public void removeCardFromGrid(int row, int col) throws emptyDevCardGridSlotSelected {
        super.removeCardFromGrid(row, col);

        Optional<Color> c = Arrays.stream(Color.values()).filter(s -> s.getColumn()==col).findFirst();

        if(c.isPresent() && devCardsGrid.thereAreNotRemainingCards(c.get()))
            gameIsOver();
    }
}
