package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.server.messages.client_server.ChosenInitialResourcesMessage;
import it.polimi.ingsw.server.messages.client_server.ChosenLeaderMessage;
import it.polimi.ingsw.server.messages.client_server.LoginRequestMessage;
import it.polimi.ingsw.server.messages.client_server.LoginSizeMessage;
import it.polimi.ingsw.server.messages.server_client.LeaderProposalMessage;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.cards.LeaderCard;

import java.util.*;

public class CLI extends View{
    private Scanner scanner;
    private Gson gson;

    public CLI(){
        this.scanner = new Scanner(System.in);
        this.gson = new Gson();
    }

    @Override
    public void showMessage(String str) {
        System.out.println(str);
    }

    @Override
    public void visitBoardsUpdate(String personalBoard, List<String> otherBoards) {
        this.personalBoardView = gson.fromJson(personalBoard, BoardView.class);
        this.showMessage(this.personalBoardView.toString());
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<BoardView>();
            otherBoards.stream().forEach(s -> {
                otherBoardsView.add(gson.fromJson(s, BoardView.class));
            });
            this.showMessage(this.otherBoardsView.toString());
        }
    }

    @Override
    public void visitDevGridUpdate(String updatedGrid) {
        this.devGrid = gson.fromJson(updatedGrid, GridView.class);
        this.showMessage(this.devGrid.toString());
    }

    @Override
    public void visitInkwell(String nickname) {
        this.showMessage(nickname + " receives inkwell");
        if(this.personalBoardView.getNickname().equals(nickname))
            this.personalBoardView.setInkwell();
        else
            this.otherBoardsView.stream().filter(p -> p.getNickname().equals(nickname)).forEach(BoardView::setInkwell);
    }

    @Override
    public void visitLorenzoUpdate(String updatedLorenzo) {
        this.lorenzoView = gson.fromJson(updatedLorenzo, LorenzoView.class);
        this.showMessage(lorenzoView.toString());
    }

    @Override
    public void visitMarketUpdate(String updatedMarket) {
        this.marketView = gson.fromJson(updatedMarket, MarketView.class);
        this.showMessage(marketView.toString());
    }

    @Override
    public void visitGameStarted(String str) {
        this.showMessage(str);
    }

    @Override
    public void visitInitialResource(int quantity) {

        StringBuilder str = new StringBuilder("(");

        for(int i = 0; i< Resource.values().length-2; i++) {
            str.append(i).append(" for ").append(Resource.values()[i]);
            if(i != Resource.values().length - 3 )
                str.append(", ");
            else
                str.append(")");
        }

        boolean inputCorrect = false;

        if (quantity == 1) {
            while(!inputCorrect) {
                this.showMessage("Choose a resource and a warehouse depot where to store it: " + str );
                int res = scanner.nextInt();
                int depot = scanner.nextInt();
                if(res >= 0 && res <= 3) {
                    if(depot >= 0 && depot <=2) {
                        Map<Integer,Integer> resMap = new HashMap<>();
                        resMap.put(res,depot);
                        networkHandler.sendMessage(new ChosenInitialResourcesMessage(resMap));
                        inputCorrect = true;

                    }
                }
            }
        } else {
            int resCounter = 0;
            Map<Integer, Integer> resMap = new HashMap<>();

            while(!inputCorrect) {
                this.showMessage("(x2) Choose a resource and a warehouse depot where to store it: " + str );
                int res = scanner.nextInt();
                int depot = scanner.nextInt();
                if(res >= 0 && res <= 3) {
                    if(depot >= 0 && depot <=2) {
                        if(resCounter == 0) {
                            resMap.put(res, depot);
                            resCounter++;
                        } else {
                            for(Map.Entry<Integer,Integer> entry:resMap.entrySet()){
                                if(entry.getKey() == res && entry.getValue() == depot && (depot == 1 || depot == 2)){
                                    networkHandler.sendMessage(new ChosenInitialResourcesMessage(resMap));
                                    inputCorrect = true;
                                } else if(entry.getKey() != res && depot != entry.getValue()){
                                    resMap.put(res,depot);
                                    networkHandler.sendMessage(new ChosenInitialResourcesMessage(resMap));
                                    inputCorrect = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void visitLeaderProposal(int[] proposedLeaderCards) {
        int[] chosenLeaderCards = new int[2];
        boolean success = false;
        while(!success){
            this.showMessage("Choose 2 of these 4 Leader Cards: " + Arrays.toString(proposedLeaderCards));
            this.showMessage("Select 0, 1, 2 or 3 : ");
            int ind1 = scanner.nextInt();
            int ind2 = scanner.nextInt();
            if(0 <= ind1 && ind1 <= 3 && 0 <= ind2 && ind2 <= 3 && ind1 != ind2) {
                chosenLeaderCards[0] = ind1;
                chosenLeaderCards[1] = ind2;
                success = true;
            }
        }
        this.networkHandler.sendMessage(new ChosenLeaderMessage(chosenLeaderCards));
    }

    @Override
    public void visitLobbyFull(String str) {
        this.showMessage(str);
        String gameID = scanner.nextLine();

        this.networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));
    }

    @Override
    public void visitLobbyNotReady(String str) {
        this.showMessage(str);
        String gameID = scanner.nextLine();

        this.networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));
    }

    @Override
    public void visitLoginSuccess(String currentPlayers) {
        this.showMessage("Login success. Current players: \n" + currentPlayers);
    }

    @Override
    public void visitRequestLobbySize(String str) {
        this.showMessage(str);
        this.networkHandler.sendMessage(new LoginSizeMessage(scanner.nextInt()));
    }
}
