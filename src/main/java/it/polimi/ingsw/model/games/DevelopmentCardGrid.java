package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.virtualview.GridObserver;

import java.util.*;

/**
 * @author Tommaso Capacci
 * Class that represents the grid in which the Development Cards will be placed.
 */
public class DevelopmentCardGrid {

    private final GridSlot[][] grid;
    private final GridObserver gridObserver;

    /**
     * This constructor requires an array of all Development Cards because it initializes randomly the grid with all of them.
     * @param devCards array that contains all the Development Card of the game.
     */
    public DevelopmentCardGrid(DevelopmentCard[] devCards, GridObserver gridObserver){
        this.gridObserver = gridObserver;
        grid = new GridSlot[3][4];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                grid[i][j] = new GridSlot();

        List<DevelopmentCard> devCardsList = Arrays.asList(devCards);
        Collections.shuffle(devCardsList);

        for(DevelopmentCard card : devCardsList)
            grid[card.getLevel() - 1][card.getType().getColumn()].add(card);
    }
    /**
     * This constructor requires an array of all Development Cards because it initializes randomly the grid with all of them.
     * @param devCards array that contains all the Development Card of the game.
     */
    public DevelopmentCardGrid(DevelopmentCard[] devCards){
        this.gridObserver = null;
        grid = new GridSlot[3][4];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++)
                grid[i][j] = new GridSlot();

        List<DevelopmentCard> devCardsList = Arrays.asList(devCards);
        Collections.shuffle(devCardsList);

        for(DevelopmentCard card : devCardsList)
            grid[card.getLevel() - 1][card.getType().getColumn()].add(card);
    }

    /**
     * If the selected slot of the grid is not empty removes the last card that contains.
     * @param row The row (which represents also the level of the cards that contains) which the selected slot belongs to.
     * @param col The column (which represents also the color of the cards that contains) which the selected slot belongs to.
     * @throws emptyDevCardGridSlotSelectedException Thrown when the selected slot is already empty.
     */
    public void removeCard(int row, int col) throws emptyDevCardGridSlotSelectedException{
        if (grid[row][col].isEmpty())
            throw new emptyDevCardGridSlotSelectedException();
        else
            grid[row][col].removeLast();
    }

    /**
     * This method is used only in the solo version of the game to apply the effect of the Action Tokens that discard 2 Development Card of a certain color from the grid.
     * @param color The color of the cards to discard.
     */
    public void discard2Cards(Color color){
        int column = color.getColumn();
        int cardToDiscard = 2;

        for(int i = 0; i < 2 && cardToDiscard > 0; i++){
            if(grid[i][column].size() > 1) {
                grid[i][column].removeLast();
                grid[i][column].removeLast();
                cardToDiscard = 0;
            }
            else{
                if(grid[i][column].size() == 1) {
                    grid[i][column].removeLast();
                    cardToDiscard--;
                }
            }
        }
    }

    /**
     * Method only used in the solo version of the game to check if there are any available cards of the specified color.
     * @param color The color of cards that has to be checked.
     * @return "TRUE" only if there are not available cards of that color.
     */
    public boolean thereAreNotRemainingCards(Color color){
        int column = color.getColumn();

        for(int i = 0; i < 2;  i++)
            if(!grid[i][column].isEmpty())
                return false;

        return true;
    }

    /**
     * If the slot placed at the specified coordinates is not empty this method returns the last card inside of it.
     * @param row The row of the slot.
     * @param col The column of the slot.
     * @return The last card of the slot.
     * @throws emptyDevCardGridSlotSelectedException Thrown when the selected slot is already empty.
     */
    public DevelopmentCard getCard(int row, int col) throws emptyDevCardGridSlotSelectedException{
        if(grid[row][col].isEmpty())
            throw new emptyDevCardGridSlotSelectedException();
        else
            return grid[row][col].getLast();
    }
}
