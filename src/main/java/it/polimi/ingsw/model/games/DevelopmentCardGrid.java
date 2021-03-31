package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DevelopmentCardGrid {

    private List<DevelopmentCard>[][] grid;
    private int numberOfGreen; //proposta: usare Map<Color, Integer>?
    private int numberOfBlue;
    private int numberOfYellow;
    private int numberOfPurple;

    public DevelopmentCardGrid(DevelopmentCard[] devCards){
        numberOfGreen = 12;
        numberOfBlue = 12;
        numberOfYellow = 12;
        numberOfPurple = 12;

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

        //for(int i = 0; i < devCards.length; i++){
        //    grid[devCards[shuffle_array[i]].getColor().getColomn()][devCards[shuffle_array[i]].getLevel()-1].add(devCards[shuffle_array[i]]);
        //
        //}
    }

    public void removeCard(int row, int col){
        grid[row][col].remove(grid[row][col].size()-1);
    }

    public void discard2Cards(Color color){

    }
}
