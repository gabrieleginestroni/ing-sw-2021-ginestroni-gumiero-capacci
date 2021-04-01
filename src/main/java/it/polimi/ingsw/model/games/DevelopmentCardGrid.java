package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Tommaso Capacci
 * Class that represents the grid in which the Development Cards will be placed.
 */
public class DevelopmentCardGrid {

    private List<DevelopmentCard>[][] grid;
    private Map<Color, Integer> remainingCards;

    /**
     * This constructor requires an array of all Development Cards because it initializes randomly the grid with all of them.
     * @param devCards array that contains all the Development Card of the game.
     */
    public DevelopmentCardGrid(DevelopmentCard[] devCards){

        remainingCards = new HashMap<>();
        remainingCards.put(Color.GREEN, 0);
        remainingCards.put(Color.BLUE, 0);
        remainingCards.put(Color.YELLOW, 0);
        remainingCards.put(Color.PURPLE, 0);

        //grid = new ArrayList<DevelopmentCard>[3][Color.values().length]; PROBLEMAAAA!!!

        int[] shuffle_array = new int[devCards.length];
        int max = devCards.length-1;
        int randomNumber;
        int temp;

        for(int i = 0; i < devCards.length; i++)
            shuffle_array[i] = i;
        for( ; max >= 0 ; max--){
            randomNumber = ThreadLocalRandom.current().nextInt(0, max + 1);
            temp = shuffle_array[randomNumber];
            shuffle_array[randomNumber] = shuffle_array[max];
            shuffle_array[max] = temp;
        }

        for(int i = 0; i < devCards.length; i++){
            Color tempColor = devCards[shuffle_array[i]].getType();
            int prevQuantity = remainingCards.get(tempColor);
            int newQuantity = prevQuantity + 1;

            grid[devCards[shuffle_array[i]].getType().getColumn()][devCards[shuffle_array[i]].getLevel()-1].add(devCards[shuffle_array[i]]);

            remainingCards.replace(tempColor, newQuantity);
        }
    }

    /**
     * If the selected slot of the grid is not empty removes the last card that contains.
     * @param row The row (which represents also the level of the cards that contains) which the selected slot belongs to.
     * @param col The column (which represents also the color of the cards that contains) which the selected slot belongs to.
     * @throws emptyDevCardGridSlotSelected Thrown when the selected slot is already empty.
     * @throws gameEndsException Thrown when the removal of the card leads to the end of the game.
     */
    public void removeCard(int row, int col) throws emptyDevCardGridSlotSelected, gameEndsException{
        if (grid[row][col].isEmpty())
            throw new emptyDevCardGridSlotSelected();
        else {
            DevelopmentCard tempCard = grid[row][col].get(grid[row][col].size() - 1);
            Color tempColor = tempCard.getType();
            int prevQuantity = remainingCards.get(tempColor);
            int newQuantity = prevQuantity - 1;

            grid[row][col].remove(tempCard);

            remainingCards.replace(tempColor, newQuantity);

            if (newQuantity == 0)
                throw new gameEndsException();
        }
    }

    /**
     * This method is used only in the solo game to apply the effect of the action Tokens that discard 2 Development Card of a certain color from the grid.
     * @param color The color of the cards to discard.
     * @throws gameEndsException Thrown when the elimination of the requested type of cards leads to the end of the game.
     */
    public void discard2Cards(Color color) throws gameEndsException{
        int cardQuantity = remainingCards.get(color);

        if (cardQuantity > 2){
            //int lastLevel = cardQuantity%(Color.values().length);RIPRENDERE DA QUI

        }else{
            grid[2][color.getColumn()].clear();
            remainingCards.replace(color, 0);
            throw new gameEndsException();
        }
    }
}
