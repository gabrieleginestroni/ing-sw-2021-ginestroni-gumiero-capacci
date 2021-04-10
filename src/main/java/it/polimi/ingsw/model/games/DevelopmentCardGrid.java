package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.*;

/**
 * @author Tommaso Capacci
 * Class that represents the grid in which the Development Cards will be placed.
 */
public class DevelopmentCardGrid {

    private final GridSlot[][] grid;
    private final Map<Color, Integer> remainingCards;

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

        grid = new GridSlot[3][4];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                grid[i][j] = new GridSlot();

        List<DevelopmentCard> devCardsList = Arrays.asList(devCards);
        Collections.shuffle(devCardsList);

        for(DevelopmentCard card : devCardsList){
            Color tempColor = card.getType();
            int prevQuantity = remainingCards.get(tempColor);
            int newQuantity = prevQuantity + 1;

            grid[card.getLevel() - 1][card.getType().getColumn()].add(card);

            remainingCards.replace(tempColor, newQuantity);
        }
    }

    /**
     * If the selected slot of the grid is not empty removes the last card that contains.
     * @param row The row (which represents also the level of the cards that contains) which the selected slot belongs to.
     * @param col The column (which represents also the color of the cards that contains) which the selected slot belongs to.
     * @throws emptyDevCardGridSlotSelected Thrown when the selected slot is already empty.
     */
    public void removeCard(int row, int col) throws emptyDevCardGridSlotSelected{
        if (grid[row][col].isEmpty())
            throw new emptyDevCardGridSlotSelected();
        else {
            DevelopmentCard tempCard = grid[row][col].removeLast();
            Color tempColor = tempCard.getType();
            int prevQuantity = remainingCards.get(tempColor);
            int newQuantity = prevQuantity - 1;

            remainingCards.replace(tempColor, newQuantity);

        }
    }

    /**
     * This method is used only in the solo version of the game to apply the effect of the Action Tokens that discard 2 Development Card of a certain color from the grid.
     * @param color The color of the cards to discard.
     */
    public void discard2Cards(Color color){
        int cardQuantity = remainingCards.get(color);

        if (cardQuantity > 2){
            int newQuantity = cardQuantity - 2;

            int lastOccupiedLevel = (12 - cardQuantity)/4;

            if(grid[lastOccupiedLevel][color.getColumn()].size() > 1){
                grid[lastOccupiedLevel][color.getColumn()].removeLast();
                grid[lastOccupiedLevel][color.getColumn()].removeLast();
            }else{
                grid[lastOccupiedLevel][color.getColumn()].removeLast();
                grid[lastOccupiedLevel + 1][color.getColumn()].removeLast();
            }
            remainingCards.replace(color, newQuantity);
        }else{
            grid[2][color.getColumn()].clear();
            remainingCards.replace(color, 0);
        }
    }

    /**
     * Method only used in the solo version of the game to check if there are any available cards of the specified color.
     * @param color The color of cards that has to be checked.
     * @return "TRUE" only if there are not available cards of that color.
     */
    public boolean thereAreNotRemainingCards(Color color){
        return remainingCards.get(color) == 0;
    }

    /**
     * If the slot placed at the specified coordinates is not empty this method returns the last card inside of it.
     * @param row The row of the slot.
     * @param col The column of the slot.
     * @return The last card of the slot.
     * @throws emptyDevCardGridSlotSelected Thrown when the selected slot is already empty.
     */
    public DevelopmentCard getCard(int row, int col) throws emptyDevCardGridSlotSelected{
        if(grid[row][col].isEmpty())
            throw new emptyDevCardGridSlotSelected();
        else
            return grid[row][col].getLast();
    }
}
