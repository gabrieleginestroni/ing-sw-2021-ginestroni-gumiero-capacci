package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.util.ArrayList;


public class Board {
    private final CardSlot[] cardSlot;
    private final Warehouse wareHouse;
    private final StrongBox strongBox;
    private final ArrayList<LeaderCard> hand;
    private boolean inkwell;
    private final FaithTrack faithTrack;


    public Board() {
        this.wareHouse = new Warehouse();
        this.cardSlot = new CardSlot[3];
        cardSlot[0] = new CardSlot();
        cardSlot[1] = new CardSlot();
        cardSlot[2] = new CardSlot();
        this.strongBox = new StrongBox();
        this.inkwell = false;
        this.hand = new ArrayList<>();
        this.faithTrack = new FaithTrack();
    }
    public void addLeaderCard(LeaderCard card){
        this.hand.add(card);
    }

    public void addDevelopmentCard(DevelopmentCard card, CardSlot cardSlot) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException {

        cardSlot.addCard(card);

    }

    public int getWarehouseResource(Resource res){

        return wareHouse.getTotalWarehouseQuantity(res);
    }

    public int getStrongBoxResource( Resource res){
        return strongBox.getResource(res);
    }

    public void addLeaderDepot(Resource res){
        this.wareHouse.createLeaderDepot(res);
    }

    public void setInkwell(){
        this.inkwell = true;
    }

    public int getFaithPoints(){
        return this.faithTrack.getFaithMarker();
    }

    public void giveFaithPoints(int steps) throws vaticanReportSection3Exception,
            vaticanReportSection2Exception, vaticanReportSection1Exception {
        this.faithTrack.addFaith(steps); }

    public int getCardNumber(int level, Color color){
       int tot1 = this.cardSlot[0].getCardNumber(level,color);
       int tot2 = this.cardSlot[1].getCardNumber(level,color);
       int tot3 = this.cardSlot[2].getCardNumber(level,color);

       return tot1+tot2+tot3;

    }

    public void addDepotResource(Depot depot,Resource res, int quantity) throws invalidDepotTypeChangeException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        this.wareHouse.addResource(depot,res,quantity);
    }

    public void removeDepotResource(Depot depot, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException {
        this.wareHouse.removeResource(depot,res,quantity);
    }

    public void swapDepot(WarehouseDepot depot1,WarehouseDepot depot2) throws invalidSwapException {
        this.wareHouse.swapDepot(depot1,depot2);
    }

    public void addStrongboxResource(Resource res,int quantity){
        this.strongBox.addResource(res,quantity);

    }

    public void removeStrongboxResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        this.strongBox.removeResource(res,quantity);

    }

}
