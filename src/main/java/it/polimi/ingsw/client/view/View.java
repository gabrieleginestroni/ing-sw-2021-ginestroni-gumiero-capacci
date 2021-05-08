package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.network_handler.NetworkHandler;

import java.util.List;

public abstract class View {
    GridView devGrid;
    BoardView personalBoardView;
    List<BoardView> otherBoardsView;
    LorenzoView lorenzoView;
    MarketView marketView;
    NetworkHandler networkHandler;

    public void showMessage(String str) {
        System.out.println(str);
    }
    public void addNetworkHandler(NetworkHandler networkHandler) { this.networkHandler = networkHandler;}


    public abstract void visitBoardsUpdate(String personalBoard, List<String> otherBoards);
    public abstract void visitDevGridUpdate(String updatedGrid);
    public abstract void visitGameStarted(String str);
    public abstract void visitInitialResource(int quantity);
    public abstract void visitInkwell(String nickname, String updatedBoard);
    public abstract void visitLeaderProposal(int[] proposedLeaderCards);
    public abstract void visitLobbyFull(String str);
    public abstract void visitLobbyNotReady(String str);
    public abstract void visitLoginSuccess(String currentPlayers);
    public abstract void visitLorenzoUpdate(String updatedLorenzo);
    public abstract void visitMarketUpdate(String updatedMarket);
    public abstract void visitRequestLobbySize(String str);



}
