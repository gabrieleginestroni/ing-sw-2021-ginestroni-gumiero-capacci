package it.polimi.ingsw.server.model.games;

import it.polimi.ingsw.server.exceptions.emptyDevCardGridSlotSelectedException;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.virtual_view.GridObserver;

import java.util.*;

/**
 * @author Tommaso Capacci
 * Class that represents the grid in which the Development Cards will be placed.
 */
public class DevelopmentCardGrid {

    private final GridSlot[][] grid;
    private final GridObserver gridObserver;

    /**
     * This constructor requires an array of all Development Cards (because it initializes randomly the grid with all of them) and the observer which the grid has to be attached to.
     * The cards of level 1 will be placed in the 3rd row, meanwhile the card of level 3 will be placed in the 1st row. All the cards will be placed in the right column, in order of colors.
     * @param devCards The array that contains all the Development Card of the game.
     * @param gridObserver The GridObserver that will observe the status of the grid that is going to be built.
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
            grid[3 - card.getLevel()][card.getType().getColumn()].add(card);

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

        gridObserver.notifyDevelopmentGridChange(getGridStatus());
    }

    /**
     * This method is used only in the solo version of the game to apply the effect of the Action Tokens that discard 2 Development Card of a certain color from the grid.
     * @param color The color of the cards to discard.
     */
    public void discard2Cards(Color color){
        int column = color.getColumn();
        int cardToDiscard = 2;

        for(int i = 2; i >= 0 && cardToDiscard > 0; i--){
            for(int j = 0; j < grid[i][column].size() && cardToDiscard > 0; j++){
                grid[i][column].removeLast();
                cardToDiscard--;
            }
        }

        gridObserver.notifyDevelopmentGridChange(getGridStatus());
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

    /**
     * This method returns a matrix which contains the CardID of the top Development Card for each GridSlot.
     * @return A matrix of integers that represent the ID of the top card for that slot, 0 if is empty.
     */
    public int[][] getGridStatus(){
        int[][] tempStatus = new int[3][4];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++){
                if(grid[i][j].isEmpty())
                    tempStatus[i][j] = 0;
                else
                    tempStatus[i][j] = grid[i][j].getLast().getId();
            }
        return tempStatus;
    }

    /**
     * Returns TRUE only if the specified column does not contain cards.
     * @param col The index of the column to check.
     * @return TRUE if the column is empty, FALSE otherwise.
     */
    public boolean isColumnEmpty(int col){
        for(int i = 0; i <= 2; i++)
            if(!grid[i][col].isEmpty())
                return false;
        return true;
    }
}
