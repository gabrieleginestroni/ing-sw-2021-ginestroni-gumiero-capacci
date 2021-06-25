package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;
import it.polimi.ingsw.server.messages.client_server.*;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.CardRequirement;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.ResourceRequirement;

import java.util.*;

public class CLI extends View{
    private final Scanner scanner;
    private final Gson gson;

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    public CLI(){
        this.scanner = new Scanner(System.in);
        this.gson = new Gson();
    }

    @Override
    public void showMessage(String str) {
        if(str != null)
            System.out.println(str);
    }

    @Override
    public void visitGameAbort() {
        this.showMessage("Insufficient players number to continue the match");
    }

    @Override
    public void visitPlayerDisconnection(String nickname) {
        this.showMessage(nickname + " disconnected!");
    }

    @Override
    public void visitPlayerReconnection(String nickname) {
        this.showMessage(nickname + " reconnected!");
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


    private String[] buildPersonalBoardCLI(BoardView playerView, int lines, int maxWidth) {
        String[] personalMatrix = new String[lines];
        int[] supportMatrix = new int[lines];
        Arrays.fill(personalMatrix, "");
        Arrays.fill(supportMatrix, 0);
        int i = 2;

        try {
            if(playerView.getNickname().equals(this.getNickname()))
                personalMatrix[0] += "Your Board";
            else
                personalMatrix[0] += playerView.getNickname()+"'s Board";

            if(playerView.hasInkwell())
                personalMatrix[0] += " Ӝ"; //16E4

            personalMatrix[1] += "Strongbox";
            //show resource in strongbox
            List<Resource> resources = new ArrayList<>(Arrays.asList(Resource.COIN, Resource.SERVANT, Resource.STONE, Resource.SHIELD));
            for (Resource res : resources) {
                int j;
                String resChar = ConsoleColors.colorMap.get(res.getColor().toUpperCase()) + ConsoleColors.resourceMap.get(res) + ConsoleColors.colorMap.get("RESET");
                String str = "TOT "+resChar+": "+playerView.getStrongBox().get(res.toString())+" ";
                if(playerView.getStrongBox().get(res.toString()) < 10)
                    str += " ";
                personalMatrix[i] += str;
                int strlength = 10;
                for (j = strlength; j < playerView.getStrongBox().get(res.toString()) + strlength && j < maxWidth; j++) {
                    personalMatrix[i] += resChar;
                    if (j == maxWidth - 3) {
                        personalMatrix[i] += "+ ";
                        j += 3;
                    }
                }
                for (int a = j; a < maxWidth; a++)
                    personalMatrix[i] += " ";
                supportMatrix[i] = 1;
                i++;
            }
            i++; //blank line

            personalMatrix[i] += "Warehouse Depots";
            i++;
            //represent warehouse depots, as in the original game
            for(int index = 0; index <= 2; index++){
                String res = playerView.getWarehouseDepotResource().get(index);
                int quantity = playerView.getWarehouseDepotQuantity().get(index);
                for (int j = 0; j < quantity; j++)
                    personalMatrix[i] += ConsoleColors.colorMap.get(Resource.valueOf(res).getColor().toUpperCase())+ConsoleColors.resourceMap.get(Resource.valueOf(res))+ConsoleColors.colorMap.get("RESET");
                //blank - for every unused space in depot
                for(int j = 0; j < index-quantity+1; j++)
                    personalMatrix[i] += ConsoleColors.resourceMap.get(Resource.WHITE);
                //add padding
                for(int j = index+1; j < maxWidth; j++)
                    personalMatrix[i] += " ";
                supportMatrix[i] = 1;
                i++;
            }

            personalMatrix[i] += "Leader Depots";
            i++;
            int offset = i; //offset to get the correct leader depot
            for(String res : playerView.getLeaderDepotResource()){
                int quantity = playerView.getLeaderDepotQuantity().get(i-offset);
                for (int j = 0; j < quantity; j++)
                    personalMatrix[i] += ConsoleColors.colorMap.get(Resource.valueOf(res).getColor().toUpperCase())+ConsoleColors.resourceMap.get(Resource.valueOf(res))+ConsoleColors.colorMap.get("RESET");
                for(int j = 0; j < 2-quantity; j++) {
                    if (res.equals("NULL"))
                        personalMatrix[i] += ConsoleColors.resourceMap.get(Resource.WHITE);
                    else
                        personalMatrix[i] += ConsoleColors.colorMap.get(Resource.valueOf(res).getColor().toUpperCase()) + ConsoleColors.resourceMap.get(Resource.WHITE) + ConsoleColors.colorMap.get("RESET");
                }
                //add padding
                for(int j = 2; j < maxWidth; j++)
                    personalMatrix[i] += " ";
                supportMatrix[i] = 1;
                i++;
            }
            i++;//blank line

            //display Faith Track
            personalMatrix[i] = "  Faith Track  ";
            i++;
            for(int j = 0; j < playerView.getFaithTrackMarker(); j++)
                personalMatrix[i] += ConsoleColors.colorMap.get("RED") + ConsoleColors.resourceMap.get(Resource.FAITH) + ConsoleColors.colorMap.get("RESET");
            for(int j = playerView.getFaithTrackMarker(); j < 24; j++)
                personalMatrix[i] += "░";
            supportMatrix[i] = 1;

            //padding
            for (int h = 24; h < maxWidth; h++)
                personalMatrix[i] += " ";

            i++;

            //display PopeTiles
            boolean[] tiles = playerView.getPopeTiles();
            for(int h = 0; h < 3; h++){
                int space = (h == 0 ? 4 : (h == 1 ? 3 : 2));
                int sectionLengthBefore = (h < 2 ? 1 : 2); //track before tiles
                int sectionLengthAfter = (h == 0 ? 1 : 2); //track after tiles

                personalMatrix[i] += " ".repeat(space);
                personalMatrix[i+1] += " ".repeat(space);
                String status = "";
                String color = "";
                if(tiles[h]) {
                    color = "GREEN";
                    status = "VV";
                }else {
                    color = "RED";
                    status = "XX";
                }

                personalMatrix[i] += ConsoleColors.colorMap.get(color)+"░".repeat(sectionLengthBefore)+ConsoleColors.colorMap.get("RESET");
                personalMatrix[i+1] += ConsoleColors.colorMap.get(color)+"░".repeat(sectionLengthBefore)+ConsoleColors.colorMap.get("RESET");

                personalMatrix[i] += ConsoleColors.colorMap.get(color) + status + ConsoleColors.colorMap.get("RESET");
                personalMatrix[i + 1] += ConsoleColors.colorMap.get(color) + status + ConsoleColors.colorMap.get("RESET");

                personalMatrix[i] += ConsoleColors.colorMap.get(color)+"░".repeat(sectionLengthAfter)+ConsoleColors.colorMap.get("RESET");
                personalMatrix[i+1] += ConsoleColors.colorMap.get(color)+"░".repeat(sectionLengthAfter)+ConsoleColors.colorMap.get("RESET");
            }

            //padding
            for (int h = 24; h < maxWidth; h++) {
                personalMatrix[i] += " ";
                personalMatrix[i+1] += " ";
            }

            supportMatrix[i] = 1;
            supportMatrix[i+1] = 1;
            i += 3; //2 rows tiles + blank line

            //card slot
            personalMatrix[i] = "  Cardslots ";
            i++;
            for(int a = 0; a < 3; a++) {
                int id = playerView.getTopCardSlot(a);
                int maxSize = 11;
                if (id != 0) {
                    DevelopmentCard card = this.getDevelopmentCardByID(id);
                    int iStart = i;
                    int[] max = new int[lines]; //support array to count length of line
                    String cardTypeColor = " " + ConsoleColors.colorMap.get(card.getType().toString()) + "█" + ConsoleColors.colorMap.get("RESET");

                    //first row, card level
                    personalMatrix[i] += cardTypeColor;
                    personalMatrix[i] += "  ";
                    if (card.getLevel() < 3) //add a space for card of level 1 and 2
                        personalMatrix[i] += " ";
                    for (int j = 0; j < card.getLevel(); j++)
                        personalMatrix[i] += ConsoleColors.colorMap.get(card.getType().toString()) + "█" + ConsoleColors.colorMap.get("RESET");
                    if (card.getLevel() == 1) //add a space for card of level 1
                        personalMatrix[i] += " ";
                    personalMatrix[i] += " N." + id;
                    if (id >= 10)
                        max[i] += 10;
                    else
                        max[i] += 9;

                    supportMatrix[i] = 1;

                    //second row, card production, skipping cost
                    i++;
                    int iProdStart = i; //support variable referring to the line where production section start
                    int maxInput = 0; //support variable to set inner length of card
                    for (Map.Entry<Resource, Integer> entry : card.getProductionInput().entrySet()) {
                        personalMatrix[i] += cardTypeColor + " ";
                        for (int j = 0; j < entry.getValue(); j++)
                            personalMatrix[i] += ConsoleColors.colorMap.get(entry.getKey().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(entry.getKey()) + ConsoleColors.colorMap.get("RESET");
                        max[i] += entry.getValue() + 1; //updating line length
                        supportMatrix[i] = 1;
                        i++;
                        if (maxInput < entry.getValue() + 1)
                            maxInput = entry.getValue() + 1;
                    }

                    //3 rows are used for production section, add space to blank lines if production input section is < 3
                    while (i < iProdStart + 3) {
                        personalMatrix[i] += cardTypeColor;
                        for (int j = 0; j < maxInput; j++)
                            personalMatrix[i] += " ";
                        max[i] = maxInput;
                        supportMatrix[i] = 1;
                        i++;
                    }

                    for (Map.Entry<Resource, Integer> entry : card.getProductionOutput().entrySet()) {
                        personalMatrix[iProdStart] += " ==> ";
                        for (int j = 0; j < entry.getValue(); j++)
                            personalMatrix[iProdStart] += ConsoleColors.colorMap.get(entry.getKey().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(entry.getKey()) + ConsoleColors.colorMap.get("RESET");
                        max[iProdStart] += entry.getValue() + 5;
                        iProdStart++;
                    }

                    //last row, victory point
                    personalMatrix[i] += cardTypeColor;
                    for (int j = 0; j < maxSize / 2; j++) //center victory points value
                        personalMatrix[i] += " ";
                    String str = "(" + card.getVictoryPoints() + ")";
                    personalMatrix[i] += str;
                    max[i] = str.length() + maxSize / 2;
                    supportMatrix[i] = 1;

                    //set end of each line
                    for (int j = iStart; j <= i; j++) {
                        for (int h = max[j]; h < maxSize; h++)
                            personalMatrix[j] += " ";
                        personalMatrix[j] += cardTypeColor;
                        max[j] = maxSize;
                    }

                    //if there are multiple cards in the slot
                    int b = 2;
                    while(b <= playerView.getCardSlot()[a].size()){
                        i = iStart;
                        card = this.getDevelopmentCardByID(playerView.getCardSlot()[a].get(playerView.getCardSlot()[a].size()-b));
                        cardTypeColor = " " + ConsoleColors.colorMap.get(card.getType().toString()) + "█" + ConsoleColors.colorMap.get("RESET");
                        if (card.getLevel() < 3) //add a space for card of level 1 and 2
                            personalMatrix[i] += " ";
                        for (int j = 0; j < card.getLevel(); j++)
                            personalMatrix[i] += ConsoleColors.colorMap.get(card.getType().toString()) + "█" + ConsoleColors.colorMap.get("RESET");
                        if (card.getLevel() == 1) //add a space for card of level 1
                            personalMatrix[i] += " ";

                        personalMatrix[i] += card.getId();
                        if(card.getId() < 10)
                            personalMatrix[i] += " ";
                        personalMatrix[i] += cardTypeColor;
                        max[i] += 7;

                        i++;
                        for(int j = 0; j < 3; j++){
                            personalMatrix[i] += " ".repeat(5)+cardTypeColor;
                            max[i] += 7;
                            i++;
                        }

                        personalMatrix[i] += " (" + card.getVictoryPoints()+")";
                        if(card.getVictoryPoints() < 10)
                            personalMatrix[i] += " ";
                        personalMatrix[i] += cardTypeColor;
                        max[i] += 7;
                        b++;
                    }

                    //set spacing
                    for (int j = iStart; j <= i; j++)
                        personalMatrix[j] += " ".repeat(maxWidth-max[j] - 4);

                    i++; //blank row
                }else{
                    for(int j = 0; j < 5; j++) {
                        personalMatrix[i] = "|";
                        for (int h = 0; h < maxSize + 2; h++)
                            personalMatrix[i] += " ";
                        personalMatrix[i] += "|";
                        i++;
                    }
                }
                i++;
            }

            //display hiddenHand
            if(playerView.getHiddenHand() != null && playerView.getHiddenHand().size() > 0){
                personalMatrix[i] += "Hidden Hand";
                i++;
                int iStart = i;
                int index = 0;
                for(int j = 0; j < playerView.getHiddenHand().size(); j++) {
                    i = iStart;
                    String[] matrixNew = buildLeaderCard(this.getLeaderCardByID(playerView.getHiddenHand().get(j)));
                    for (String s : matrixNew) {
                        personalMatrix[i] += s;
                        supportMatrix[i] = 1;
                        i++;
                    }
                    index += 16; //card length
                }
                int iEnd = i;
                for(i = iStart; i < iEnd; i++)
                    for(int j = index; j < maxWidth; j++)
                        personalMatrix[i] += " ";
            }

            if(playerView.getActiveLeaders() != null && playerView.getActiveLeaders().size() > 0){
                personalMatrix[i] += "Active Leaders";
                i++;
                int iStart = i;
                int index = 0;
                for(int j = 0; j < playerView.getActiveLeaders().size(); j++) {
                    i = iStart;
                    String[] matrixNew = buildLeaderCard(this.getLeaderCardByID(playerView.getActiveLeaders().get(j)));
                    for (String s : matrixNew) {
                        personalMatrix[i] += s;
                        supportMatrix[i] = 1;
                        i++;
                    }
                    index += 16; //card length
                }
                int iEnd = i;
                for(i = iStart; i < iEnd; i++)
                    for(int j = index; j < maxWidth; j++)
                        personalMatrix[i] += " ";
            }

            for (int j = 0; j < personalMatrix.length; j++) {
                //if support matrix value is set to 1 it's a line containing color so padding already done
                if(supportMatrix[j] == 0) {
                    personalMatrix[j] += " ".repeat(Math.max(0, maxWidth - personalMatrix[j].length()));
                    personalMatrix[j] = personalMatrix[j].substring(0, maxWidth);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return Arrays.copyOf(personalMatrix, (i < 30 ? 30 : i+1));
    }

    private String[] buildGameCLI(int lines){
        String[] gameMatrix = new String[lines];
        Arrays.fill(gameMatrix, "");
        int i = 0;
        int maxSize = 11; //fixed max size for devGrid

        try {
            if (this.devGrid != null) {
                int[][] cardGrid = this.devGrid.getGrid();
                for (int[] row : cardGrid) {
                    int iStart = i; //line where to start building the card(same for all the cards of same level)
                    for(int id : row){
                        i = iStart;
                        int[] max = new int[lines]; //support array to count length of line
                        Arrays.fill(max, 0);
                        DevelopmentCard card = this.getDevelopmentCardByID(id);
                        if(card != null) {
                            String cardTypeColor = "   " + ConsoleColors.colorMap.get(card.getType().toString()) + "█" + ConsoleColors.colorMap.get("RESET");

                            //first row, card level
                            gameMatrix[i] += cardTypeColor;
                            gameMatrix[i] += "  ";
                            if (card.getLevel() < 3) //add a space for card of level 1 and 2
                                gameMatrix[i] += " ";
                            for (int j = 0; j < card.getLevel(); j++)
                                gameMatrix[i] += ConsoleColors.colorMap.get(card.getType().toString()) + "█" + ConsoleColors.colorMap.get("RESET");
                            if (card.getLevel() == 1) //add a space for card of level 1
                                gameMatrix[i] += " ";
                            //gameMatrix[i] += "  ";
                            gameMatrix[i] += " N." + id; //debug
                            if(id >= 10)
                                max[i]++;
                            max[i] += 9;

                            //second row, card cost
                            i++;
                            gameMatrix[i] += cardTypeColor;
                            for (Map.Entry<Resource, Integer> entry : card.getCost().entrySet()) {
                                gameMatrix[i] += " ";
                                for (int j = 0; j < entry.getValue(); j++)
                                    gameMatrix[i] += ConsoleColors.colorMap.get(entry.getKey().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(entry.getKey()) + ConsoleColors.colorMap.get("RESET");
                                max[i] += entry.getValue() + 1; //updating line length
                            }

                            //third row, card production
                            i++;
                            int iProdStart = i; //support variable referring to the line where production section start
                            int maxInput = 0; //support variable to set inner length of card
                            for (Map.Entry<Resource, Integer> entry : card.getProductionInput().entrySet()) {
                                gameMatrix[i] += cardTypeColor + " ";
                                for (int j = 0; j < entry.getValue(); j++)
                                    gameMatrix[i] += ConsoleColors.colorMap.get(entry.getKey().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(entry.getKey()) + ConsoleColors.colorMap.get("RESET");
                                max[i] += entry.getValue() + 1; //updating line length
                                i++;
                                if (maxInput < entry.getValue() + 1)
                                    maxInput = entry.getValue() + 1;
                            }

                            //3 rows are used for production section, add space to blank lines if production input section is < 3
                            while (i < iProdStart + 3) {
                                gameMatrix[i] += cardTypeColor;
                                for (int j = 0; j < maxInput; j++)
                                    gameMatrix[i] += " ";
                                max[i] = maxInput;
                                i++;
                            }

                            for (Map.Entry<Resource, Integer> entry : card.getProductionOutput().entrySet()) {
                                gameMatrix[iProdStart] += " ==> ";
                                for (int j = 0; j < entry.getValue(); j++)
                                    gameMatrix[iProdStart] += ConsoleColors.colorMap.get(entry.getKey().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(entry.getKey()) + ConsoleColors.colorMap.get("RESET");
                                max[iProdStart] += entry.getValue() + 5;
                                iProdStart++;
                            }

                            //compute max size to set end of each line on same offset, now fixed to maxSize
                            /*
                            for (int j : max)
                                if (maxSize < j)
                                    maxSize = j;
                             */

                            //last row, victory point
                            gameMatrix[i] += cardTypeColor;
                            for (int j = 0; j < maxSize / 2; j++) //center victory points value
                                gameMatrix[i] += " ";
                            String str = "(" + card.getVictoryPoints() + ")";
                            gameMatrix[i] += str;
                            max[i] = str.length() + maxSize / 2;

                            //set end of each line
                            for (int j = iStart; j <= i; j++) {
                                for (int h = max[j]; h < maxSize; h++)
                                    gameMatrix[j] += " ";
                                gameMatrix[j] += cardTypeColor;
                                //System.out.println("RIGA "+j+" =>"+"("+gameMatrix[j].length()+") "+new Gson().toJson(gameMatrix[j]));

                            }
                            i++;
                            //padding for blank row
                            for (int h = 0; h < maxSize + 8; h++)
                                gameMatrix[i] += " ";
                        }else{
                            //blank slot, add blank rows
                            for(i = iStart; i < iStart + 7; i++)
                                for(int j = 0; j < maxSize + 8; j++)
                                    gameMatrix[i] += " ";
                        }
                    }
                    i++;
                }
                //padding for blank row
                for(int j = 0; j < maxSize + 8; j++)
                    gameMatrix[i] += " ";

                for(int j = i; i < j+4; i++) //4 blank lines
                    gameMatrix[i] = " ".repeat(67);

                String[][] market = this.marketView.getMarket();
                int dimMax = 1;
                String dot;
                for (int a = 0; a < market.length; a++) { //every market row

                    //padding start of every row
                    for(int c = 0; c <= 2 * dimMax; c++){
                        if(c < dimMax)
                            gameMatrix[i + c] = " ".repeat(10 + dimMax - c);
                        else
                            gameMatrix[i + c] = " ".repeat(10 + c - dimMax);
                    }

                    for (int b = 0; b < market[a].length; b++) { //every market column
                        dot = ConsoleColors.colorMap.get(market[a][b].toUpperCase()) + "█" + ConsoleColors.colorMap.get("RESET");
                        /*
                        *  Marble example, add 2 █ every line until middle then decrease
                        *    ██
                        *   ████
                        *    ██
                        */
                        for(int c = 0; c <= 2 * dimMax; c++) {
                            if(c < dimMax)
                                gameMatrix[i + c] += dot.repeat(2 * (c + 1)) + " ".repeat(5 + 2 * (dimMax - c));
                            else
                                gameMatrix[i + c] += dot.repeat(2 * (2 * dimMax - c + 1)) + " ".repeat(5 + 2 * (c - dimMax));
                        }
                    }

                    //padding end of every row, add free marble in middle row
                    dot = ConsoleColors.colorMap.get(this.marketView.getFreeMarble().toUpperCase()) + "█" + ConsoleColors.colorMap.get("RESET");
                    for(int c = 0; c <= 2 * dimMax; c++){
                        if(a != 1) {
                            if (c < dimMax)
                                gameMatrix[i + c] += " ".repeat(29 + c);
                            else
                                gameMatrix[i + c] += " ".repeat(29 + (dimMax * 2 - c));
                        }else {
                            if (c < dimMax){
                                gameMatrix[i + c] += " ".repeat(25);
                                gameMatrix[i + c] += dot.repeat(2 * (c + 1)) + " ".repeat(2 * (dimMax - c));
                                gameMatrix[i + c] += " ".repeat(c);
                            }else {
                                gameMatrix[i + c] += " ".repeat(25);
                                gameMatrix[i + c] += dot.repeat(2 * (2 * dimMax - c + 1))+ " ".repeat(2 * (c - dimMax));
                                gameMatrix[i + c] += " ".repeat(dimMax * 2 - c);
                            }
                        }
                    }
                    i += dimMax*2 +1;
                    gameMatrix[i] = " ".repeat(67); //blank line
                    i++;
                }
            }
        }catch (Exception e){
            //OK
            e.printStackTrace();
        }


        //limit line width to maxWidth
        for(int j = 0; j < gameMatrix.length; j++) {
            if(gameMatrix[j].length() > 86)
                gameMatrix[j] += " ".repeat(10);
            else
                gameMatrix[j] += " ".repeat(Math.max(0, 86 - gameMatrix[j].length()));
            //gameMatrix[i] = gameMatrix[i].substring(0, Math.min(gameMatrix[i].length(), maxWidth));
            //System.out.println(j+" ("+gameMatrix[j].length()+") => "+new Gson().toJson(gameMatrix[j]));
        }

        return gameMatrix;
    }

    private String[] buildLeaderCard(LeaderCard leaderCard){
        String[] matrix = new String[6];
        Arrays.fill(matrix, "");
        int maxSize = 12;

        int i = 0;
        int[] max = new int[10]; //support array to count length of line

        //first row card requirements(development card or resource)
        matrix[i] += " █ ";
        max[i] = 1;
        if(leaderCard.getCardRequirements() != null){
            for(CardRequirement requirement : leaderCard.getCardRequirements()){
                if(requirement.getLevel() == 0)
                    for(int j = 0; j < requirement.getQuantity(); j++)
                        matrix[i] += ConsoleColors.colorMap.get(requirement.getColor().toString()) + "█" + ConsoleColors.colorMap.get("RESET");
                else
                    for(int j = 0; j < requirement.getQuantity(); j++)
                        matrix[i] += ConsoleColors.colorMap.get(requirement.getColor().toString()) + requirement.getLevel() + ConsoleColors.colorMap.get("RESET");
                matrix[i] += " ";
                max[i] += requirement.getQuantity()+1;
            }
        }
        if(leaderCard.getResourceRequirements() != null){
            for(ResourceRequirement requirement : leaderCard.getResourceRequirements()){
                for(int j = 0; j < requirement.getQuantity(); j++)
                    matrix[i] += ConsoleColors.colorMap.get(requirement.getResource().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(requirement.getResource()) + ConsoleColors.colorMap.get("RESET");
                matrix[i] += " ";
                max[i] += requirement.getQuantity()+1;
            }
        }
        matrix[i] += " N."+leaderCard.getId();
        max[i] += 4;
        if(leaderCard.getId() > 9) //if cardId is double digit then size is 2 char
            max[i]++;

        //blank line
        i++;
        matrix[i] += " █";

        //victory points
        i++;
        matrix[i] += " █    ";
        matrix[i] += "("+leaderCard.getVictoryPoints()+")";
        max[i] = 7;

        //blank line
        i++;
        matrix[i] += " █";

        //card power
        i++;
        matrix[i] += " █";
        switch (leaderCard.getPower()){
            case "discount" :
                matrix[i] += "   "+ConsoleColors.colorMap.get(leaderCard.getResource().getColor().toUpperCase()) + "-1 "+ConsoleColors.resourceMap.get(leaderCard.getResource()) + ConsoleColors.colorMap.get("RESET");
                max[i] = 7;
                break;
            case "depots" :
                matrix[i] += "   "+ConsoleColors.colorMap.get(leaderCard.getResource().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(leaderCard.getResource()) + " " + ConsoleColors.resourceMap.get(leaderCard.getResource()) + ConsoleColors.colorMap.get("RESET");
                max[i] = 6;
                break;
            case "whiteMarble" :
                matrix[i] += "   "+ ConsoleColors.colorMap.get(Resource.WHITE.getColor().toUpperCase()) + "W" + ConsoleColors.colorMap.get("RESET") + " = "+ConsoleColors.colorMap.get(leaderCard.getResource().getColor().toUpperCase()) +ConsoleColors.resourceMap.get(leaderCard.getResource()) + ConsoleColors.colorMap.get("RESET");
                max[i] = 8;
                break;
            case "production" :
                matrix[i] +=  " ? => "+ConsoleColors.colorMap.get(leaderCard.getResource().getColor().toUpperCase()) + ConsoleColors.resourceMap.get(leaderCard.getResource()) + ConsoleColors.colorMap.get("RESET") + " + " + ConsoleColors.resourceMap.get(Resource.FAITH);
                max[i] = 11;
                break;
        }

        //blank line
        i++;
        matrix[i] += " █";

        //add padding at end of every line
        for(i = 0; i < matrix.length; i++) {
            for(int j = max[i]; j < maxSize; j++)
                matrix[i] += " ";
            matrix[i] += "█ ";
            //System.out.println(new Gson().toJson(matrix[i]));
        }
        return matrix;
    }


    //lorenzo status
    private String[] buildLorenzoCLI(LorenzoView lorenzoView, int lines) {
        String[] matrix = new String[lines];
        Arrays.fill(matrix, " ");
        //14 blank rows to start
        int i = 14;

        //display Faith Track
        matrix[i] = "Lorenzo";
        i++;

        matrix[i] = "      Faith Track: ";
        i++;
        matrix[i] = "    ";
        for(int j = 0; j < lorenzoView.getBlackCrossMarker(); j++)
            matrix[i] += ConsoleColors.colorMap.get("GREY") + ConsoleColors.resourceMap.get(Resource.FAITH) + ConsoleColors.colorMap.get("RESET");

        for(int j = lorenzoView.getBlackCrossMarker(); j < 24; j++)
            matrix[i] += "░";
        i+=3;

        //display last Lorenzo action
        String lastAction = lorenzoView.getLastDrawnActionToken();
        if(lastAction != null) {
            matrix[i] = "      Last Lorenzo action: ";
            i++;
            matrix[i] = "    "+lastAction;
        }
        return matrix;
    }

    //proposing 4 leader card to chose
    private void build4Leaders(int[] proposedLeaderCards){
        String[] matrix = new String[6];
        Arrays.fill(matrix, " ");
        for(int cardId : proposedLeaderCards) {
            String[] matrixNew = buildLeaderCard(this.getLeaderCardByID(cardId));
            for(int i = 0; i < matrixNew.length; i++)
                matrix[i] += matrixNew[i];
        }

        for(int i = 0; i < matrix.length; i++)
            System.out.println(matrix[i]);
    }

    public void buildCLI() {
        int lines = 55; //number of lines;
        int maxWidth = 32; //maxWidth of each line;

        if(this.personalBoardView != null) {
            String[] personalMatrix;
            personalMatrix = buildPersonalBoardCLI(this.personalBoardView, lines, maxWidth);

            int maxLines = personalMatrix.length;
            String[][] otherMatrix = new String[3][];
            int i = 0;

            String[] lorenzoMatrix = new String[maxLines];
            Arrays.fill(lorenzoMatrix, "");

            if (otherBoardsView != null) {
                for (BoardView otherPlayerView : otherBoardsView) {
                    if (otherPlayerView != null) {
                        otherMatrix[i] = buildPersonalBoardCLI(otherPlayerView, lines, maxWidth);
                        if (maxLines < otherMatrix[i].length)
                            maxLines = otherMatrix[i].length;
                        i++;
                    }
                }
            } else if (lorenzoView != null)
                lorenzoMatrix = buildLorenzoCLI(lorenzoView, lines);

            String[] gameMatrix = buildGameCLI(lines);
            //TO CLEAR CONSOLE
            System.out.print("\033[H\033[2J");
            System.out.flush();

            String str;
            for (i = 0; i < maxLines; i++) {
                str = "";
                if (personalMatrix.length > i)
                    str += personalMatrix[i];
                else
                    str += " ".repeat(maxWidth);
                str += gameMatrix[i];
                if(otherBoardsView == null)
                    str += lorenzoMatrix[i];
                else
                    for (int j = 0; j < otherBoardsView.size(); j++) {
                        if (otherMatrix[j].length > i)
                            str += otherMatrix[j][i];
                        else
                            str += " ".repeat(maxWidth);
                    }
                System.out.println(str);
            }
        }
    }

    @Override
    public void visitBoardsUpdate(String personalBoard, List<String> otherBoards) {
        this.personalBoardView = gson.fromJson(personalBoard, BoardView.class);
        //this.showMessage(this.personalBoardView.toString());
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<>();
            otherBoards.forEach(s -> otherBoardsView.add(gson.fromJson(s, BoardView.class)));

            //this.showMessage(this.otherBoardsView.toString());
        }
        buildCLI();
    }

    @Override
    public void visitDevGridUpdate(String updatedGrid) {
        this.devGrid = gson.fromJson(updatedGrid, GridView.class);
        //this.showMessage(this.devGrid.toString());
        buildCLI();
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
        //this.showMessage(lorenzoView.toString());
        buildCLI();
    }

    @Override
    public void visitMarketUpdate(String updatedMarket) {
        this.marketView = gson.fromJson(updatedMarket, MarketView.class);
        //this.showMessage(marketView.toString());
        buildCLI();
    }

    @Override
    public void visitForcedReconnectionUpdate(String personalBoard, List<String> otherBoards, String updatedGrid, String updatedMarket) {
        this.marketView = gson.fromJson(updatedMarket, MarketView.class);
        this.personalBoardView = gson.fromJson(personalBoard, BoardView.class);
        if(otherBoards.size() != 0) {
            this.otherBoardsView = new ArrayList<>();
            otherBoards.forEach(s -> otherBoardsView.add(gson.fromJson(s, BoardView.class)));
        }
        this.devGrid = gson.fromJson(updatedGrid, GridView.class);
        buildCLI();
        this.showMessage("Successfully reconnected to the match ");

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
            build4Leaders(proposedLeaderCards);
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

        if(this.nickname.equals(currentPlayerNickname)){
            showMessage(errorMessage);
            int move = -1;
            if(personalBoardView.getHiddenHand().isEmpty())
                move = 0;
            else {
                boolean success = false;
                while (!success) {
                    this.showMessage("Choose next action (0 -> main action, 1 -> leader action) ");
                    move = Integer.parseInt(scanner.nextLine().trim());
                    if (move >= 0 && move <= 1)
                        success = true;
                }
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
                            throw new invalidClientInputException("Cannot pick " + addedQuantity + " " + entry.getKey() + " from depot " +depot+", only " + indexMap.get(depot) + " available");

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
                    }else {
                        throw new invalidClientInputException("Cannot buy card, only " + availableQuantity + " " + entry.getKey() + ", " + entry.getValue() + " needed");
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
            if(personalBoardView.getHiddenHand().isEmpty())
                move = 0;
            else {
                boolean success = false;
                while (!success) {
                    showMessage("Choose next action (0 -> skip leader action, 1 -> leader action ) ");
                    move = Integer.parseInt(scanner.nextLine().trim());
                    if (move >= 0 && move <= 1)
                        success = true;
                }
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
                        throw new invalidClientInputException("No development card in slot "+productionIndex);
                } else {
                    int card;
                    if(personalBoardView.getActiveLeaders() != null && personalBoardView.getActiveLeaders().size() > productionIndex - 3)
                        card = personalBoardView.getActiveLeaders().get(productionIndex-3);
                    else
                        throw new invalidClientInputException("No active leader selected "+(productionIndex-3));
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
                                throw new invalidClientInputException("Cannot pick " + addedQuantity + " " + entry.getKey() + " from depot " +depot+", only " + indexMap.get(depot) + " available");

                            quantity += addedQuantity; //progressive count of resource to remove

                            //add or insert element in the remove map
                            if (depot != 5)
                                wareHouseMap.merge(depot, addedQuantity, Integer::sum);
                            else
                                strongBoxMap.merge(entry.getKey(), addedQuantity, Integer::sum);
                            indexMap.put(depot, indexMap.get(depot) - addedQuantity);
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
                showMessage("Choose depot to place "+ res +" (-2 to swap depots, -1 to discard, 0 -> 2 for warehouse depots, 3 -> 4 for leader depots) ");
                depotIndex = Integer.parseInt(scanner.nextLine().trim());
                if(depotIndex >= -2 && depotIndex <= 4)
                    success = true;
            }

            this.networkHandler.sendMessage(new ChosenMarketDepotMessage(depotIndex));
        } else
            this.showMessage(currentPlayerNickname + " is placing resources in depots");
    }
}
