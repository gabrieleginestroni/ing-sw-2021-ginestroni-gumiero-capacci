package it.polimi.ingsw.client.view;

import java.util.List;

public abstract class View {
    GridView devGrid;
    BoardView personalBoardView;
    List<BoardView> otherBoardsView;
    LorenzoView lorenzoView;
    MarketView marketView;

    public abstract void showMessage(String str);
}
