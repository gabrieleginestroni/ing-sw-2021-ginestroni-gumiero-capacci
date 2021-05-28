package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;
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

    //get depot where resource is available
    private Map.Entry<Integer, Integer> getDepotResource(List<String> depot, List<Integer> quantity, Resource resource, int i){
        int offset = i;
        for(String s: depot) {
            if (!s.equals("NULL") && Resource.valueOf(s) == resource)
                return new java.util.HashMap.SimpleEntry<>(i, quantity.get(i - offset));
            i++;
        }
        return null;
    }

    //Print where the required resource is available inside depots, updating the current status based
    //on the previous choices made
    private int printIndexMap(Map<Integer, Integer> indexMap){
        String depotName;
        int availableQuantity = 0;
        for(Map.Entry<Integer, Integer> index : indexMap.entrySet()){
            if(index.getKey() < 3)
                depotName = "Warehouse depot";
            else if (index.getKey() < 5)
                depotName = "Leader depot";
            else
                depotName = "Strongbox";
            this.showMessage(depotName+" (" + index.getKey() + ") => " + index.getValue() + " available");
            availableQuantity += index.getValue();
        }
        return availableQuantity;
    }

    //Create map of depots where resource is available
    private Map<Integer, Integer> getIndexMap(Resource resource){
        Map<Integer, Integer> indexMap = new HashMap<>();
        Map.Entry<Integer, Integer> warehouseRes = getDepotResource(super.personalBoardView.getWarehouseDepotResource(), super.personalBoardView.getWarehouseDepotQuantity(), resource, 0);
        if(warehouseRes != null)
            indexMap.put(warehouseRes.getKey(), warehouseRes.getValue());
        Map.Entry<Integer, Integer> leaderRes = getDepotResource(super.personalBoardView.getLeaderDepotResource(), super.personalBoardView.getLeaderDepotQuantity(), resource, 3);
        if(leaderRes != null)
            indexMap.put(leaderRes.getKey(), leaderRes.getValue());
        int strongboxVal = super.personalBoardView.getStrongBox().get(resource.toString());
        if(strongboxVal > 0)
            indexMap.put(5, strongboxVal);
        return indexMap;
    }


    private String[] buildPersonalBoardCLI(int lines, int maxWidth) {
        String[] personalMatrix = new String[lines];
        Arrays.fill(personalMatrix, "");

        try {
            if (this.personalBoardView != null) {
                personalMatrix[0] += "Your Board";
                personalMatrix[1] += "Strongbox";
                int i = 2;
                for(Resource res : Resource.values()) {
                    int j = 0;
                    if(res != Resource.FAITH && res != Resource.WHITE)
                        for (j = 0; j < this.personalBoardView.getStrongBox().get(res.toString()) && j < maxWidth/2; j++) {
                            personalMatrix[i] += ConsoleColors.colorMap.get(res.getColor().toUpperCase())+ConsoleColors.resourceMap.get(res)+ConsoleColors.colorMap.get("RESET");
                            if (j == maxWidth/2-1)
                                personalMatrix[i] += "+";
                        }
                        for(int a = j; a < maxWidth; a++)
                            personalMatrix[i] += " ";
                    i++;
                }
                personalMatrix[i] += "Warehouse Depots";
                i++;
                int offset = i;
                for(int index = 2; index >= 0; index--){
                    String res = this.personalBoardView.getWarehouseDepotResource().get(index);
                    int quantity = this.personalBoardView.getWarehouseDepotQuantity().get(index); //reverse
                    for (int j = 0; j < quantity; j++)
                        personalMatrix[i] += ConsoleColors.colorMap.get(Resource.valueOf(res).getColor().toUpperCase())+ConsoleColors.resourceMap.get(Resource.valueOf(res))+ConsoleColors.colorMap.get("RESET");;
                    for(int j = 0; j < index-quantity+1; j++)
                        personalMatrix[i] += ConsoleColors.resourceMap.get(Resource.WHITE);
                    for(int j = index+1; j < maxWidth; j++)
                        personalMatrix[i] += " ";
                    i++;
                }
                personalMatrix[i] += "Leader Depots";
                i++;
                offset = i;
                for(String res : this.personalBoardView.getLeaderDepotResource()){
                    int quantity = this.personalBoardView.getLeaderDepotQuantity().get(i-offset);
                    for (int j = 0; j < quantity; j++)
                        personalMatrix[i] += ConsoleColors.colorMap.get(Resource.valueOf(res).getColor().toUpperCase())+ConsoleColors.resourceMap.get(Resource.valueOf(res))+ConsoleColors.colorMap.get("RESET");;
                    for(int j = 0; j < 2-quantity; j++)
                        personalMatrix[i] += ConsoleColors.resourceMap.get(Resource.WHITE);
                    i++;
                }
                for (int j = 0; j < personalMatrix.length; j++) {
                    //System.out.println("RIGA "+j+" =>"+"("+personalMatrix[j].length()+") "+new Gson().toJson(personalMatrix[j]));
                    if(j < 2 || j >= 6 && j <= 8 || j == 12) {
                        personalMatrix[j] += " ".repeat(Math.max(0, maxWidth - personalMatrix[j].length()));
                        personalMatrix[j] = personalMatrix[j].substring(0, maxWidth);
                    }
                }
            }
        } catch (Exception e) {
            //OK
            e.printStackTrace();
        }
        return personalMatrix;
    }

    private String[] buildOtherBoardsCLI(int lines, int maxWidth) {
        String[] otherMatrix = new String[lines];
        Arrays.fill(otherMatrix, "");

        try{
            if (this.otherBoardsView != null) {
                for (int i = 0; i < this.otherBoardsView.size(); i++) {
                    String nick = this.otherBoardsView.get(i).getNickname();
                    otherMatrix[5 * i] += "Player " + nick + " ".repeat(Math.max(4, 20 - nick.length()));
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.SHIELD.toString()); j++)
                        otherMatrix[1 + 5 * i] += "\uD83D\uDEE1"; //🛡
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.STONE.toString()); j++)
                        otherMatrix[2 + 5 * i] += "\uD83D\uDC8E"; //💎
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.SERVANT.toString()); j++)
                        otherMatrix[3 + 5 * i] += "\uD83D\uDC68"; //👨
                    for (int j = 0; j < this.otherBoardsView.get(i).getStrongBox().get(Resource.COIN.toString()); j++)
                        otherMatrix[4 + 5 * i] += "\uD83D\uDCB0"; //💰
                }
            }
        }catch(Exception e){
            //OK
        }

        //fix line width to maxWidth
        for(int i = 0; i < otherMatrix.length; i++) {
            otherMatrix[i] += " ".repeat(Math.max(0, maxWidth - otherMatrix[i].length()));
            otherMatrix[i] = otherMatrix[i].substring(0, Math.min(otherMatrix[i].length(), maxWidth));
        }
        return otherMatrix;
    }

    private String[] buildGameCLI(int lines, int maxWidth){
        String[] gameMatrix = new String[lines];
        Arrays.fill(gameMatrix, "");
        int i = 0;

        try {
            if (this.devGrid != null) {
                String[] tmp = this.devGrid.toString().split("\n");
                for (String row : tmp) {
                    gameMatrix[i] = row;
                    i++;
                }
                i++;
                tmp = this.marketView.toString().toUpperCase().replaceAll(" +", " ").split("\n");
                for (String row : tmp) {
                    for(Map.Entry<String, String> entry : ConsoleColors.colorMap.entrySet())
                        row = row.replace(entry.getKey(), entry.getValue()+" █"+ConsoleColors.colorMap.get("RESET"));
                    gameMatrix[i] = row;
                    i++;
                }
            }
        }catch (Exception e){
            //OK
        }

        /*
        //limit line width to maxWidth
        for(i = 0; i < gameMatrix.length; i++) {
            gameMatrix[i] += " ".repeat(Math.max(0, maxWidth - gameMatrix[i].length()));
            gameMatrix[i] = gameMatrix[i].substring(0, Math.min(gameMatrix[i].length(), maxWidth));
        }
        */
        return gameMatrix;
    }



    public void buildCLI() {
        int lines = 20; //number of lines;
        int maxWidth = 50; //number of lines;
        String[] pixelMatrix = new String[lines];
        String[] personalMatrix = buildPersonalBoardCLI(lines, maxWidth);
        String[] otherMatrix = buildOtherBoardsCLI(lines, maxWidth);
        String[] gameMatrix = buildGameCLI(lines, maxWidth);
        Arrays.fill(pixelMatrix, "");
        //TO CLEAR CONSOLE?
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
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
            otherBoards.forEach(s -> otherBoardsView.add(gson.fromJson(s, BoardView.class)));

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
    public void visitInitialResource(int quantity)  {

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
    public void visitRequestLobbySize(String str) throws invalidClientInputException {
        this.showMessage(str);
        int dim = Integer.parseInt(scanner.nextLine().trim());
        if(dim <= 0 || dim >= 5)
            throw new invalidClientInputException("Invalid lobby size, please retry");
        this.networkHandler.sendMessage(new LoginSizeMessage(dim));

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
    public void visitWhiteMarbleProposal(Resource res1, Resource res2) {
        int res = 0;
        boolean success = false;
        while(!success){
            this.showMessage("You have 2 white marbles powers. Please choose what resource you want to get from a white marble (0 -> "+res1+", 1 -> "+res2+")");
            res = Integer.parseInt(scanner.nextLine().trim());
            if(res >= 0 && res <= 1)
                success = true;
        }
        if(res == 0)
            this.networkHandler.sendMessage(new ChosenWhiteMarbleMessage(res1));
        else
            this.networkHandler.sendMessage(new ChosenWhiteMarbleMessage(res2));



    }

    @Override
    public void visitStartTurn(String currentPlayerNickname, String errorMessage) {
        super.currentPlayer = currentPlayerNickname;
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

        } else {
            this.showMessage("The turn of " + currentPlayerNickname + " is starting");
            /* if (scanner.hasNext()) {
                this.showMessage("It's " + this.getCurrentPlayer() + "'s turn, please wait yours.");
                scanner.nextLine();
            } */
        }
    }

    @Override
    public void visitDevCardSale(String currentPlayerNickname) throws invalidClientInputException {
        if(this.nickname.equals(currentPlayerNickname)){
            boolean success = false;
            int row = -1;
            int col = -1;
            while(!success){
                this.showMessage("Type row and column of the card you want to buy, row '-1' for skipping card purchase ");
                row = scanner.nextInt();
                if(row != -1)
                    col = Integer.parseInt(scanner.nextLine().trim());
                else
                    scanner.nextLine();
                if(row == -1 || col == -1) {
                    this.networkHandler.sendMessage(new ChosenDevCardToPurchaseMessage(-1, -1, null, -1));
                    return;
                }
                if(row >= 0 && row <= 2 && col >= 0 && col <= 3 && super.devGrid.getGridId(row, col) != 0)
                    success = true;
            }

            Map<Integer, Map<Resource, Integer>> resToRemove = new HashMap<>();
            Map<Resource, Integer> cardCost = super.getDevelopmentCardByID(super.devGrid.getGridId(row, col)).getCost();

            success = false;
            int cardSlot = -1;
            while(!success) {
                this.showMessage("Choose the cardSlot index where you want to place the card");
                cardSlot = Integer.parseInt(scanner.nextLine().trim());
                if(0 <= cardSlot && cardSlot <= 4)
                    success = true;
            }

            //Applying discounts
            List<String> discounts = super.personalBoardView.getDiscounts();
            for(String s : discounts){
                if(cardCost.get(Resource.valueOf(s)) != null)
                    cardCost.put(Resource.valueOf(s), cardCost.get(Resource.valueOf(s))-1);
            }

            for(Map.Entry<Resource,Integer> entry:cardCost.entrySet()) {
                int quantity = 0;
                int availableQuantity;

                Map<Integer, Integer> indexMap = getIndexMap(entry.getKey());

                //Keeps asking where to pick the same resource if selected resources are not enough
                while (quantity < entry.getValue()) {
                    int remainingToPick = entry.getValue()-quantity;
                    this.showMessage("Pick " + remainingToPick + " " + entry.getKey() + " from your depots");

                     availableQuantity = printIndexMap(indexMap)+quantity;

                    //Ask to pick resources only if we have enough overall resources to buy the card
                    if(availableQuantity >= entry.getValue()) {
                        int depot = scanner.nextInt();
                        int addedQuantity = Integer.parseInt(scanner.nextLine().trim());
                        if(addedQuantity > remainingToPick) {
                            this.showMessage("You picked "+addedQuantity+" "+entry.getKey()+", only "+remainingToPick+" needed, picking "+remainingToPick);
                            addedQuantity = remainingToPick;
                        }

                        if(indexMap.get(depot) < addedQuantity)
                            throw new invalidClientInputException("Cannot pick " + addedQuantity + " " + entry.getKey() + " from depot " +depot+", only " + indexMap.get(depot) + " available");//TODO fix bug loop

                        indexMap.put(depot, indexMap.get(depot) - addedQuantity);
                        quantity += addedQuantity; //progressive count of resource to remove

                        //add or insert element in the remove map
                        Map<Resource, Integer> tmpMap;
                        if(resToRemove.get(depot) != null) {
                            tmpMap = resToRemove.get(depot);
                            if(resToRemove.get(depot).get(entry.getKey()) != null)
                                addedQuantity += resToRemove.get(depot).get(entry.getKey());
                        }else
                            tmpMap = new HashMap<>();
                        tmpMap.put(entry.getKey(), addedQuantity);
                        resToRemove.put(depot, tmpMap);
                        this.showMessage(new Gson().toJson(resToRemove)); //TODO DEBUG
                    }else {
                        throw new invalidClientInputException("Cannot buy card, only " + availableQuantity + " " + entry.getKey() + ", " + entry.getValue() + " needed");//TODO fix bug
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
    public void visitProductionState(String currentPlayerNickname, String errorMessage) throws invalidClientInputException {
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
            int deltaQuantity;
            int availableQuantity = 0;
            Map<Resource, Integer> productionInput;

            if (productionIndex <= 4) {
                if (productionIndex <= 2) {
                    //development card production
                    int card = personalBoardView.getTopCardSlot(productionIndex);
                    if(card != 0)
                        productionInput = this.getDevelopmentCardByID(card).getProductionInput();
                    else
                        throw new invalidClientInputException("No development card in slot "+productionIndex);//TODO fix bug
                } else {
                    int card;
                    if(personalBoardView.getActiveLeaders() != null && personalBoardView.getActiveLeaders().size() > productionIndex - 3)
                        card = personalBoardView.getActiveLeaders().get(productionIndex-3);
                    else
                        throw new invalidClientInputException("No active leader selected "+(productionIndex-3));//TODO fix bug
                    productionInput = new HashMap<>();
                    productionInput.put(this.getLeaderCardByID(card).getResource(), 1);
                }

                for (Map.Entry<Resource, Integer> entry : productionInput.entrySet()) {

                    Map<Integer, Integer> indexMap = getIndexMap(entry.getKey());

                    //Keeps asking where to pick the same resource if selected resources are not enough
                    while (quantity < entry.getValue()) {
                        int remainingToPick = entry.getValue() - quantity;
                        this.showMessage("Pick " + remainingToPick + " " + entry.getKey() + " from your depots");

                        availableQuantity = printIndexMap(indexMap)+quantity;

                        //Fail if we don't have enough overall resources to buy the card
                        if (availableQuantity >= entry.getValue()) {
                            int depot = scanner.nextInt();
                            int addedQuantity = Integer.parseInt(scanner.nextLine().trim());
                            if(addedQuantity > remainingToPick) {
                                this.showMessage("You picked "+addedQuantity+" "+entry.getKey()+", only "+remainingToPick+" needed, picking "+remainingToPick);
                                addedQuantity = remainingToPick;
                            }
                            if(indexMap.get(depot) < addedQuantity)
                                throw new invalidClientInputException("Cannot pick " + addedQuantity + " " + entry.getKey() + " from depot " +depot+", only " + indexMap.get(depot) + " available");//TODO fix bug

                            quantity += addedQuantity; //progressive count of resource to remove

                            //add or insert element in the remove map
                            if (depot != 5)
                                wareHouseMap.merge(depot, addedQuantity, Integer::sum);
                            else
                                strongBoxMap.merge(entry.getKey(), addedQuantity, Integer::sum);
                            indexMap.put(depot, indexMap.get(depot) - addedQuantity);

                            this.showMessage(new Gson().toJson(wareHouseMap)); //TODO DEBUG
                            this.showMessage(new Gson().toJson(strongBoxMap)); //TODO DEBUG
                        } else {
                            throw new invalidClientInputException("Cannot activate production, only " + availableQuantity + " " + entry.getKey() + ", " + entry.getValue() + " needed");
                        }
                    }
                }
            } else if (productionIndex == 5) {
                while (quantity < 2) {
                    this.showMessage("Pick " + (2 - quantity) + " resources from your depots");
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
                            this.showMessage("Warehouse depot (" + i + ") => " + (tmpRes.get(i) - deltaQuantity) + " " + s + " available");
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
                            this.showMessage("Leader depot (" + i + ") => " + (tmpRes.get(i - 3) - deltaQuantity) + " " + s + " available");
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
                            this.showMessage("Strongbox (" + i + ") => " + (super.personalBoardView.getStrongBox().get(r.toString()) - deltaQuantity) + " " + r + " available");
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
                                    this.showMessage(r.toString()+" (" + i + ") => " + (super.personalBoardView.getStrongBox().get(r.toString()) - deltaQuantity) + " " + r + " available");
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

                        this.showMessage(new Gson().toJson(wareHouseMap)); //TODO DEBUG
                        this.showMessage(new Gson().toJson(strongBoxMap)); //TODO DEBUG
                    } else {
                        throw new invalidClientInputException("Cannot activate production, only " + availableQuantity + ", 2 needed");

                    }
                }
            }
            if (productionIndex >= 3 && productionIndex != 6) {
                Resource[] resources = Resource.values();
                String str = "";
                for(int i = 0; i < resources.length-2; i++)
                    str += i+" for "+resources[i]+", ";
                str = str.substring(0, str.length()-2);
                success = false;
                chosenResource = null;
                while(!success) {
                    this.showMessage("Choose resource to produce: " + str);
                    int ind = Integer.parseInt(scanner.nextLine());
                    if(0 <= ind && ind <= resources.length-2) {
                        chosenResource = resources[ind];
                        success = true;
                    }
                }
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
        StringBuilder resMsg = new StringBuilder();
        for(Map.Entry<String,Integer> res: gameResult.entrySet())
           resMsg.append(res.getKey()).append(" ==> ").append(res.getValue()).append(" POINTS\n");
        this.showMessage(resMsg.toString());

        super.gameOver = true;
    }

    @Override
    public void visitMarketState(String currentPlayerNickname, String errorMessage) {
        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int move = -1;
            boolean success = false;
            while(!success){
                showMessage("Choose move (0 -> horizontal, 1 -> vertical ) ");
                move = Integer.parseInt(scanner.nextLine().trim());
                if(move >= 0 && move <= 1)
                    success = true;
            }

            success = false;
            int index = -1;
            int col = 0;
            String str;
            if(move == 1){
                col = 1;
                str = "column";
            }else
                str = "row";

            while(!success){
                showMessage("Choose "+str+" to shift (0 to "+(2+col)+")");
                index = Integer.parseInt(scanner.nextLine().trim());
                if(index >= 0 && index <= (2 + col))
                    success = true;
            }

            this.networkHandler.sendMessage(new ChosenMarketMoveMessage(move, index));

        } else
            this.showMessage(currentPlayerNickname + " is doing a market move");
    }

    @Override
    public void visitSwapState(String currentPlayerNickname, String errorMessage) {
        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int depot1 = -1;
            int depot2 = 0;
            boolean success = false;
            while(!success){
                showMessage("Choose 2 depot to swap (-1 to skip, 0, 1, 2 for warehouse depots) ");
                depot1 = scanner.nextInt();
                if(depot1 != -1)
                    depot2 = Integer.parseInt(scanner.nextLine().trim());
                else
                    scanner.nextLine(); //to clean input buffer
                if(depot1 >= -1 && depot1 <= 2 && depot2 >= 0 && depot2 <= 2 && depot1 != depot2)
                    success = true;
            }
            this.networkHandler.sendMessage(new ChosenSwapDepotMessage(depot1, depot2));
        } else
            this.showMessage(currentPlayerNickname + " is swapping depots");
    }

    @Override
    public void visitResourceManagementState(Resource res, String currentPlayerNickname, String errorMessage) {
        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int depotIndex = -1;
            boolean success = false;
            while(!success){
                showMessage("Choose depot to place "+res+" (-1 to discard, 0, 1, 2 for warehouse depots, 3, 4 for leader depots) ");
                depotIndex = Integer.parseInt(scanner.nextLine().trim());
                if(depotIndex >= -1 && depotIndex <= 4)
                    success = true;
            }

            this.networkHandler.sendMessage(new ChosenMarketDepotMessage(depotIndex));
        } else
            this.showMessage(currentPlayerNickname + " is placing resources in depots");
    }
}
