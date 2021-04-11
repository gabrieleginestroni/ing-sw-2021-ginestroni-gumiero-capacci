package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.games.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


public class Board {


    private final ArrayList<Resource> whiteMarbles;
    private final ArrayList<Resource> discount;

    private final CardSlot[] cardSlot;
    private final Warehouse wareHouse;
    private final StrongBox strongBox;
    private final ArrayList<LeaderCard> hand;
    private final ArrayList<LeaderCard> activeLeaders;
    private boolean inkwell;
    private final FaithTrack faithTrack;
    private final  Game game;

    public Board(Game game) {


        this.discount = new ArrayList<>();
        this.whiteMarbles = new ArrayList<>();

        this.wareHouse = new Warehouse();
        this.cardSlot = new CardSlot[3];
        cardSlot[0] = new CardSlot();
        cardSlot[1] = new CardSlot();
        cardSlot[2] = new CardSlot();
        this.strongBox = new StrongBox();
        this.inkwell = false;
        this.hand = new ArrayList<>();
        this.activeLeaders = new ArrayList<>();
        this.faithTrack = new FaithTrack(game);
        this.game = game;



    }
    public void addLeaderDepot(Resource res){
        this.wareHouse.createLeaderDepot(res);
    }

    public void addWhiteMarble(Resource res){ whiteMarbles.add(res); }

    public void addDiscount(Resource res){ discount.add(res); }

    public ArrayList<Resource> getWhiteMarbles() { return whiteMarbles;}


    public ArrayList<Resource> getDiscount() {
        return discount;
    }

    public int getResourceNumber(Resource res){
        return  getStrongBoxResource(res) + getWarehouseResource(res);
    }

    public int getStrongBoxResource( Resource res){
        return strongBox.getResource(res);
    }

    public int getWarehouseResource(Resource res){ return wareHouse.getTotalWarehouseQuantity(res); }

    public int getCardNumber(int level, Color color){
        return Arrays.stream(this.cardSlot).mapToInt(s->s.getCardNumber(level,color)).sum();
    }

    public int getFaithPoints(){
        return this.faithTrack.getFaithMarker();
    }

    //TODO
    //REMOVE
    public ArrayList<LeaderCard> getHand() {
        return hand;
    }



    public DevelopmentCard getTopCard(int cardSlotIndex) throws IndexOutOfBoundsException {
        return this.cardSlot[cardSlotIndex].getTopCard();
    }

    public void addDevelopmentCard(DevelopmentCard card, int cardSlotIndex) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException{


        this.cardSlot[cardSlotIndex].addCard(card);
        int totalDevCardNumber = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getGenericCardNumber).sum();
        if(totalDevCardNumber == 7) this.game.gameIsOver();

    }



    public void setInkwell(){
        this.inkwell = true;
    }



    public void giveFaithPoints(int steps)  {
        this.faithTrack.addFaith(steps); }



    public void addWarehouseDepotResource(int warehouseDepotIndex,Resource res, int quantity) throws addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException {
        this.wareHouse.addWarehouseDepotResource(warehouseDepotIndex,res,quantity);
    }

    public void removeWarehouseDepotResource(int warehouseDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException {
        this.wareHouse.removeWarehouseDepotResource(warehouseDepotIndex,res,quantity);
    }


    public void addLeaderDepotResource(int leaderDepotIndex,Resource res, int quantity) throws addResourceLimitExceededException,
            invalidResourceTypeException, IndexOutOfBoundsException {
        this.wareHouse.addLeaderDepotResource(leaderDepotIndex,res,quantity);
    }

    public void removeLeaderDepotResource(int leaderDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException, IndexOutOfBoundsException {
        this.wareHouse.removeLeaderDepotResource(leaderDepotIndex,res,quantity);
    }

    public void swapDepot(int warehouseDepot1Index,int warehouseDepot2Index) throws invalidSwapException {
        this.wareHouse.swapDepot(warehouseDepot1Index,warehouseDepot2Index);
    }

    public void addStrongboxResource(Resource res,int quantity){
        this.strongBox.addResource(res,quantity);

    }

    public void removeStrongboxResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        this.strongBox.removeResource(res,quantity);

    }


    //TODO
    //REMOVE
    public CardSlot[] getCardSlot(){
        return this.cardSlot;
    }

    //TODO
    //REMOVE
    public Warehouse getWareHouse() {
        return wareHouse;
    }

    public int computeVictoryPoints() {

        int faithTot = this.faithTrack.getVictoryPoints();
        int resTot = (this.wareHouse.getGenericResourceNumber() + this.strongBox.getGenericResourceNumber() ) /5;
        int leaderTot = this.hand.stream().filter(LeaderCard::isActive).mapToInt(LeaderCard::getVictoryPoints).sum();
        int devTot = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getVictoryPoints).sum();

        return faithTot + resTot + leaderTot + devTot;
    }

    public void computeActivationPopeTile(int index){
        this.faithTrack.computeActivationPopeTile(index);
    }

    public void addLeaderCard(LeaderCard card){
        this.hand.add(card);
        card.setOwner(this);
    }

    public void activateLeaderCard(int cardIndex) throws IndexOutOfBoundsException {

        LeaderCard leaderToActivate = this.hand.get(cardIndex);
        this.hand.remove(leaderToActivate);
        activeLeaders.add(leaderToActivate);
        leaderToActivate.activateCard();

    }



    public void discardLeaderCard(int cardIndex) throws IndexOutOfBoundsException {

        LeaderCard leaderToDiscard =  this.hand.get(cardIndex);
        this.hand.remove(leaderToDiscard);
        leaderToDiscard.discardCard();
        giveFaithPoints(1);
    }
}


