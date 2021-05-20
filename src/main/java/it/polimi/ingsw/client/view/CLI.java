package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.model.Resource;

import java.util.*;

public class CLI extends View{
    private final Scanner scanner;
    private final Gson gson;

    public CLI(){
        this.scanner = new Scanner(System.in);
        this.gson = new Gson();
    }

    @Override
    public void showMessage(String str) {
        //TODO move to visit method
        //buildCLI();
        if(str != null)
            System.out.println(str);
    }

    public String[] buildPersonalBoardCLI() {
        String[] personalMatrix = new String[12];
        for (int i = 0; i < personalMatrix.length; i++)
            personalMatrix[i] = "";

        try {
            if (this.personalBoardView != null) {
                personalMatrix[0] += "Your Strongbox";
                for (int j = 0; j < this.personalBoardView.getStrongBox().get(Resource.SHIELD.toString()); j++)
                    personalMatrix[1] += "\uD83D\uDEE1"; //🛡
                for (int j = 0; j < this.personalBoardView.getStrongBox().get(Resource.STONE.toString()); j++)
                    personalMatrix[2] += "\uD83D\uDC8E"; //💎
                for (int j = 0; j < this.personalBoardView.getStrongBox().get(Resource.SERVANT.toString()); j++)
                    personalMatrix[3] += "\uD83D\uDC68"; //👨
                for (int j = 0; j < this.personalBoardView.getStrongBox().get(Resource.COIN.toString()); j++)
                    personalMatrix[4] += "\uD83D\uDCB0"; //💰
            }
        } catch (Exception e) {
            //OK
        }

        //limit line width to 20
        for (int i = 0; i < personalMatrix.length; i++){
            personalMatrix[i] += " ".repeat(Math.max(0, 20 - personalMatrix[i].length()));
            personalMatrix[i] = personalMatrix[i].substring(0, Math.min(personalMatrix[i].length(), 20));
            if (personalMatrix[i].length() == 20)
                personalMatrix[i] = personalMatrix[i] + "+";
        }
        return personalMatrix;
    }

    public String[] buildOtherBoardsCLI() {
        String[] otherMatrix = new String[12];
        for(int i = 0; i < otherMatrix.length; i++)
            otherMatrix[i] = "";

        try{
            if (this.otherBoardsView != null) {
                for (int i = 0; i < this.otherBoardsView.size(); i++) {
                    String nick = this.otherBoardsView.get(i).getNickname();
                    otherMatrix[0 * (i+1)] += "Player " + nick + " ".repeat(Math.max(4, 20 - nick.length()));
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.SHIELD.toString()); j++)
                        otherMatrix[1 * (i+1)] += "\uD83D\uDEE1"; //🛡
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.STONE.toString()); j++)
                        otherMatrix[2 * (i+1)] += "\uD83D\uDC8E"; //💎
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.SERVANT.toString()); j++)
                        otherMatrix[3 * (i+1)] += "\uD83D\uDC68"; //👨
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.COIN.toString()); j++)
                        otherMatrix[4 * (i+1)] += "\uD83D\uDCB0"; //💰
                }
            }
        }catch(Exception e){
            //OK
        }

        //fix line width to 20
        for(int i = 0; i < otherMatrix.length; i++) {
            otherMatrix[i] += " ".repeat(Math.max(0, 20 - otherMatrix[i].length()));
            otherMatrix[i] = otherMatrix[i].substring(0, Math.min(otherMatrix[i].length(), 20));
        }
        return otherMatrix;
    }

    public String[] buildGameCLI(){
        String[] gameMatrix = new String[12];
        int i = 0;
        for(i = 0; i < gameMatrix.length; i++)
            gameMatrix[i] = "";

        try {
            if (this.devGrid != null) {
                String[] tmp = this.devGrid.toString().split("\n");
                for (String row : tmp) {
                    gameMatrix[i] = row;
                    i++;
                }
                i++;
                tmp = this.marketView.toString().split("\n");
                for (String row : tmp) {
                    gameMatrix[i] = row;
                    i++;
                }
            }
        }catch (Exception e){
            //OK
        }

        //limit line width to 20
        for(i = 0; i < gameMatrix.length; i++) {
            gameMatrix[i] += " ".repeat(Math.max(0, 20 - gameMatrix[i].length()));
            gameMatrix[i] = gameMatrix[i].substring(0, Math.min(gameMatrix[i].length(), 20));
        }
        return gameMatrix;
    }



    public void buildCLI() {
        String[] pixelMatrix = new String[12];
        String[] personalMatrix = buildPersonalBoardCLI();
        String[] otherMatrix = buildOtherBoardsCLI();
        String[] gameMatrix = buildGameCLI();
        for(int i = 0; i < pixelMatrix.length; i++)
            pixelMatrix[i] = "";

        for (int i = 0; i < pixelMatrix.length; i++) {
            pixelMatrix[i] = personalMatrix[i]+gameMatrix[i]+otherMatrix[i];
            System.out.println(pixelMatrix[i]);
        }
    }

    @Override
    public void visitBoardsUpdate(String personalBoard, List<String> otherBoards) {
        this.personalBoardView = gson.fromJson(personalBoard, BoardView.class);
        this.showMessage(this.personalBoardView.toString());
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<>();
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
                int depot = Integer.parseInt(scanner.nextLine().trim());
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
                int depot = Integer.parseInt(scanner.nextLine().trim());
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
            int ind2 = Integer.parseInt(scanner.nextLine().trim());
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

    @Override
    public void visitNicknameAlreadyUsed(String str,String gameID) {
        this.showMessage(str);

        String nickname = scanner.nextLine();
        while(nickname.length() > 20) {
            showMessage("Nickname must be < 20 chars");
            showMessage("Type nickname:");
            nickname = scanner.nextLine();
        }

        this.setNickname(nickname);

        this.networkHandler.sendMessage(new LoginRequestMessage(gameID,nickname));
    }

    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {
        showMessage(errorMessage);
        if(this.nickname.equals(currentPlayerNickname)){
            int move = -1;
            boolean success = false;
            while(!success){
                this.showMessage("Choose next action (0 -> main action, 1 -> leader action) ");
                move = Integer.parseInt(scanner.nextLine().trim());
                if(move >= 0 && move <= 1)
                    success = true;
            }
            this.networkHandler.sendMessage(new ChosenFirstMoveMessage(move));

        } else
            this.showMessage("The turn of " +currentPlayerNickname + " is starting");


    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) {
        if(this.nickname.equals(currentPlayerNickname)){
            boolean success = false;
            int row = -1;
            int col = -1;
            while(!success){
                this.showMessage("Type row and column of the card you want to buy ");
                row = scanner.nextInt();
                col = Integer.parseInt(scanner.nextLine().trim());
                if(row >= 0 && row <= 2 && col >= 0 && col <= 3 && super.devGrid.getGridId(row, col) != 0)
                    success = true;
            }

            Map<Integer, Map<Resource, Integer>> resToRemove = new HashMap<>();
            Map<Resource, Integer> cardCost = super.getDevelopmentCardByID(super.devGrid.getGridId(row, col)).getCost();


            System.out.println("Choose the cardslot index where you want to place the card");
            int cardSlot = Integer.parseInt(scanner.nextLine().trim());

            //Applying discounts
            List<String> discounts = super.personalBoardView.getDiscounts();
            for(String s : discounts){
                if(cardCost.get(Resource.valueOf(s)) != null)
                    cardCost.put(Resource.valueOf(s), cardCost.get(Resource.valueOf(s))-1);
            }

            for(Map.Entry<Resource,Integer> entry:cardCost.entrySet()) {
                int quantity = 0;
                int availableQuantity = 0;
                int deltaQuantity = 0;
                //Keeps asking where to pick the same resource if selected resources are not enough
                while (quantity < entry.getValue()) {
                    System.out.println("Pick " + (entry.getValue()-quantity) + " " + entry.getKey() + " from your depots");
                    int i = 0;

                    //Prints where the required resource is available inside warehouse depots, updating the current status based
                    //on the previous choices made
                    List<String> tmpDep = super.personalBoardView.getWarehouseDepotResource();
                    List<Integer> tmpRes = super.personalBoardView.getWarehouseDepotQuantity();
                    for (String s : tmpDep) {
                        if (!s.equals("NULL") && Resource.valueOf(s) == entry.getKey()) {
                            if(resToRemove.get(i) != null && resToRemove.get(i).get(entry.getKey()) != null)
                                deltaQuantity = resToRemove.get(i).get(entry.getKey());
                            else
                                deltaQuantity = 0;
                            System.out.println("Warehouse depot (" + i + ") => " + (tmpRes.get(i) - deltaQuantity ) + " " + s + " available");
                            availableQuantity += tmpRes.get(i);
                        }
                        i++;
                    }

                    //Prints where the required resource is available inside leader depots, updating the current status based
                    //on the previous choices made
                    tmpDep = super.personalBoardView.getLeaderDepotResource();
                    tmpRes = super.personalBoardView.getLeaderDepotQuantity();
                    for (String s : tmpDep) {
                        if (!s.equals("NULL") && Resource.valueOf(s) == entry.getKey()){
                            if(resToRemove.get(i) != null && resToRemove.get(i).get(entry.getKey()) != null)
                                deltaQuantity = resToRemove.get(i).get(entry.getKey());
                            else
                                deltaQuantity = 0;
                            System.out.println("Leader depot (" + i + ") => " + (tmpRes.get(i-3) - deltaQuantity) + " " + s + " available");
                            availableQuantity += tmpRes.get(i-3);
                        }
                        i++;
                    }

                    //Prints where the required resource is available inside strongbox, updating the current status based
                    //on the previous choices made
                    if(resToRemove.get(i) != null && resToRemove.get(i).get(entry.getKey()) != null)
                        deltaQuantity = resToRemove.get(i).get(entry.getKey());
                    else
                        deltaQuantity = 0;
                    System.out.println("Strongbox (" + i + ") => " + ( super.personalBoardView.getStrongBox().get(entry.getKey().toString()) - deltaQuantity) + " " + entry.getKey() + " available");
                    availableQuantity += super.personalBoardView.getStrongBox().get(entry.getKey().toString());

                    //Fail if we don't have enough overall resources to buy the card
                    if(availableQuantity >= entry.getValue()) {
                        int depot = scanner.nextInt();
                        int addedQuantity = Integer.parseInt(scanner.nextLine().trim());
                        Map<Resource, Integer> tmpMap;

                        quantity += addedQuantity; //progressive count of resource to remove

                        //add or insert element in the remove map
                        if(resToRemove.get(depot) != null) {
                            tmpMap = resToRemove.get(depot);
                            if(resToRemove.get(depot).get(entry.getKey()) != null)
                                addedQuantity += resToRemove.get(depot).get(entry.getKey());
                        }else
                            tmpMap = new HashMap<>();
                        tmpMap.put(entry.getKey(), addedQuantity);
                        resToRemove.put(depot, tmpMap);
                        System.out.println(new Gson().toJson(resToRemove));
                    }else {
                        System.out.println("Cannot buy card, only " + availableQuantity + " " + entry.getKey() + ", " + entry.getValue() + " needed");
                        break;
                        //add error
                    }
                }
            }
            this.networkHandler.sendMessage(new ChosenDevCardToPurchaseMessage(row, col, resToRemove, cardSlot));

        } else
            this.showMessage(  currentPlayerNickname + " is purchasing a development card");

    }

    @Override
    public void visitMiddleTurn(String currentPlayerNickname,String errorMessage) {

        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int move = -1;
            boolean success = false;
            while(!success){
                showMessage("Choose next action (0 -> skip leader action, 1 -> leader action ) ");
                move = Integer.parseInt(scanner.nextLine().trim());
                if(move >= 0 && move <= 1)
                    success = true;
            }
            this.networkHandler.sendMessage(new ChosenMiddleMoveMessage(move));

        } else
            this.showMessage(currentPlayerNickname + " is in middle turn");

    }

    @Override
    public void visitLeaderAction(String currentPlayerNickname) {
        if(this.nickname.equals(currentPlayerNickname)){
            List<Integer> hiddenHand = super.personalBoardView.getHiddenHand();
            Map<Integer,Integer> actionMap = new HashMap<>();
            int index = 0;
            for(Integer cardId : hiddenHand) {
                showMessage("Type the action you want to do with leader card "+cardId+" (0 -> do nothing, 1 -> activate, 2 -> discard)");
                int move = Integer.parseInt(scanner.nextLine().trim());
                actionMap.put(index,move);
                index++;
            }
            this.networkHandler.sendMessage(new ChosenLeaderActionMessage(actionMap));

        } else
            this.showMessage(currentPlayerNickname + " is executing a leader action");
    }

    @Override
    public void visitMainActionState(String currentPlayerNickname, String errorMessage) {

        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int move = -1;
            boolean success = false;
            while(!success){
                showMessage("Choose next action (0 -> market, 1 -> development card purchase, 2 -> activate production) ");
                move = Integer.parseInt(scanner.nextLine().trim());
                if(move >= 0 && move <= 2)
                    success = true;
            }
            this.networkHandler.sendMessage(new ChosenMainMoveMessage(move));

        } else
            this.showMessage(currentPlayerNickname + " is choosing main action");
    }

    @Override
    public void visitProductionState(String currentPlayerNickname, String errorMessage) {
        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int productionIndex = -1;
            boolean success = false;
            while (!success) {
                showMessage("Choose a production to activate(0, 1, 2 for cardSlot, 3,4 for leader production, 5 for board production, 6 to apply)");
                productionIndex = Integer.parseInt(scanner.nextLine().trim());
                if (productionIndex >= 0 && productionIndex <= 6)
                    success = true;
            }

            Map<Integer, Integer> wareHouseMap = new HashMap<>();
            Map<Resource, Integer> strongBoxMap = new HashMap<>();
            Resource chosenResource;
            int quantity = 0;
            int deltaQuantity = 0;
            int availableQuantity = 0;
            Map<Resource, Integer> productionInput = null;

            if (productionIndex <= 4) {
                if (productionIndex <= 2) {
                    //development card production
                    int card = personalBoardView.getTopCardSlot(productionIndex);
                    if(card != 0)
                        productionInput = this.getDevelopmentCardByID(card).getProductionInput();
                    else
                        System.out.println("No development card in slot "+productionIndex);//TODO fix bug
                } else {
                    int card = 0;
                    if(personalBoardView.getActiveLeaders() != null && personalBoardView.getActiveLeaders().size() > productionIndex - 3)
                        card = personalBoardView.getActiveLeaders().get(productionIndex-3);
                    else
                        System.out.println("No active leader selected "+(productionIndex-3));//TODO fix bug
                    productionInput = new HashMap<>();
                    productionInput.put(this.getLeaderCardByID(card).getResource(), 1);
                }

                //Keeps asking where to pick the same resource if selected resources are not enough
                for (Map.Entry<Resource, Integer> entry : productionInput.entrySet()) {
                    while (quantity < entry.getValue()) {
                        System.out.println("Pick " + (entry.getValue() - quantity) + " " + entry.getKey() + " from your depots");
                        int i = 0;

                        //Prints where the required resource is available inside warehouse depots, updating the current status based
                        //on the previous choices made
                        List<String> tmpDep = super.personalBoardView.getWarehouseDepotResource();
                        List<Integer> tmpRes = super.personalBoardView.getWarehouseDepotQuantity();
                        for (String s : tmpDep) {
                            if (!s.equals("NULL") && Resource.valueOf(s) == entry.getKey()) {
                                if (wareHouseMap.get(i) != null && wareHouseMap.get(i) != null)
                                    deltaQuantity = wareHouseMap.get(i);
                                else
                                    deltaQuantity = 0;
                                System.out.println("Warehouse depot (" + i + ") => " + (tmpRes.get(i) - deltaQuantity) + " " + s + " available");
                                availableQuantity += tmpRes.get(i);
                            }
                            i++;
                        }

                        //Prints where the required resource is available inside leader depots, updating the current status based
                        //on the previous choices made
                        tmpDep = super.personalBoardView.getLeaderDepotResource();
                        tmpRes = super.personalBoardView.getLeaderDepotQuantity();
                        for (String s : tmpDep) {
                            if (!s.equals("NULL") && Resource.valueOf(s) == entry.getKey()) {
                                if (wareHouseMap.get(i) != null && wareHouseMap.get(i) != null)
                                    deltaQuantity = wareHouseMap.get(i);
                                else
                                    deltaQuantity = 0;
                                System.out.println("Leader depot (" + i + ") => " + (tmpRes.get(i - 3) - deltaQuantity) + " " + s + " available");
                                availableQuantity += tmpRes.get(i - 3);
                            }
                            i++;
                        }

                        System.out.println("Strongbox (" + i + ") => " + ( super.personalBoardView.getStrongBox().get(entry.getKey().toString()) - deltaQuantity) + " " + entry.getKey() + " available");
                        availableQuantity += super.personalBoardView.getStrongBox().get(entry.getKey().toString());

                        //Fail if we don't have enough overall resources to buy the card
                        if (availableQuantity >= entry.getValue()) {
                            int depot = scanner.nextInt();
                            int addedQuantity = Integer.parseInt(scanner.nextLine().trim());

                            quantity += addedQuantity; //progressive count of resource to remove

                            //add or insert element in the remove map
                            if (depot != 5) {
                                wareHouseMap.merge(depot, addedQuantity, Integer::sum);
                            } else
                                strongBoxMap.merge(entry.getKey(), addedQuantity, Integer::sum);

                            System.out.println(new Gson().toJson(wareHouseMap));
                            System.out.println(new Gson().toJson(strongBoxMap));
                        } else {
                            System.out.println("Cannot activate production, only " + availableQuantity + " " + entry.getKey() + ", " + entry.getValue() + " needed");
                            break;
                            //add error
                        }
                    }
                }
            } else if (productionIndex == 5) {
                while (quantity < 2) {
                    System.out.println("Pick " + (2 - quantity) + " resources from your depots");
                    int i = 0;

                    //Prints all available resources inside warehouse depots, updating the current status based on the previous choices made
                    List<String> tmpDep = super.personalBoardView.getWarehouseDepotResource();
                    List<Integer> tmpRes = super.personalBoardView.getWarehouseDepotQuantity();
                    for (String s : tmpDep) {
                        if (!s.equals("NULL")) {
                            if (wareHouseMap.get(i) != null && wareHouseMap.get(i) != null)
                                deltaQuantity = wareHouseMap.get(i);
                            else
                                deltaQuantity = 0;
                            System.out.println("Warehouse depot (" + i + ") => " + (tmpRes.get(i) - deltaQuantity) + " " + s + " available");
                            availableQuantity += tmpRes.get(i);
                        }
                        i++;
                    }

                    //Prints all available resources inside leader depots, updating the current status based on the previous choices made
                    tmpDep = super.personalBoardView.getLeaderDepotResource();
                    tmpRes = super.personalBoardView.getLeaderDepotQuantity();
                    for (String s : tmpDep) {
                        if (!s.equals("NULL")) {
                            if (wareHouseMap.get(i) != null && wareHouseMap.get(i) != null)
                                deltaQuantity = wareHouseMap.get(i);
                            else
                                deltaQuantity = 0;
                            System.out.println("Leader depot (" + i + ") => " + (tmpRes.get(i - 3) - deltaQuantity) + " " + s + " available");
                            availableQuantity += tmpRes.get(i - 3);
                        }
                        i++;
                    }

                    //Prints where the required resource is available inside strongbox, updating the current status based
                    //on the previous choices made
                    for (Resource r : Resource.values()) {
                        if (r != Resource.FAITH && r != Resource.WHITE) {
                            if (strongBoxMap.get(r) != null && strongBoxMap.get(r) != null)
                                deltaQuantity = strongBoxMap.get(r);
                            else
                                deltaQuantity = 0;
                            System.out.println("Strongbox (" + i + ") => " + (super.personalBoardView.getStrongBox().get(r.toString()) - deltaQuantity) + " " + r.toString() + " available");
                            availableQuantity += super.personalBoardView.getStrongBox().get(r.toString());
                        }
                    }
                    //Fail if we don't have enough overall resources to buy the card
                    if (availableQuantity >= 2) {
                        int depot = scanner.nextInt();
                        int res = 0;
                        i = 0;
                        if(depot == 5){
                            for (Resource r : Resource.values()) {
                                if (r != Resource.FAITH && r != Resource.WHITE){
                                    if (strongBoxMap.get(r) != null && strongBoxMap.get(r) != null)
                                        deltaQuantity = strongBoxMap.get(r);
                                    else
                                        deltaQuantity = 0;
                                    System.out.println(r.toString()+" (" + i + ") => " + (super.personalBoardView.getStrongBox().get(r.toString()) - deltaQuantity) + " " + r.toString() + " available");
                                    i++;
                                }
                            }
                            res = scanner.nextInt();
                        }
                        int addedQuantity = Integer.parseInt(scanner.nextLine().trim());
                        quantity += addedQuantity; //progressive count of resource to remove

                        //add or insert element in the remove map
                        if (depot != 5) {
                            wareHouseMap.merge(depot, addedQuantity, Integer::sum);
                        } else
                            strongBoxMap.merge(Resource.values()[res], addedQuantity, Integer::sum);

                        System.out.println(new Gson().toJson(wareHouseMap));
                        System.out.println(new Gson().toJson(strongBoxMap));
                    } else {
                        System.out.println("Cannot activate production, only " + availableQuantity + ", 2 needed");
                        break;
                        //add error
                    }
                }
            }
            if (productionIndex >= 3 && productionIndex != 6) {
                System.out.println("Choose resource to produce");
                chosenResource = Resource.valueOf(scanner.nextLine().toUpperCase());
            } else
                chosenResource = null;

            this.networkHandler.sendMessage(new ChosenProductionMessage(productionIndex, wareHouseMap, strongBoxMap, chosenResource));
        } else
            this.showMessage(currentPlayerNickname + " is activating production");

    }

    @Override
    public void visitGameOverState(String winner, Map<String, Integer> gameResult) {

        if(this.nickname.equals(winner))
            this.showMessage("YOU WIN!!!");
        else
            this.showMessage(winner+ " WINS!!!");
        String resMsg = "";
        for(Map.Entry<String,Integer> res: gameResult.entrySet())
           resMsg += res.getKey()+" ==> "+res.getValue()+" POINTS\n";
        this.showMessage(resMsg);
    }
}
