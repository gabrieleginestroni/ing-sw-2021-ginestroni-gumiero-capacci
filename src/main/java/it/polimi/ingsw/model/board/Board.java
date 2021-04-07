package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.games.Game;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {


    private final ArrayList<Resource> whiteMarbles;
    private final ArrayList<Resource> discount;

    private final CardSlot[] cardSlot;
    private final Warehouse wareHouse;
    private final StrongBox strongBox;
    private final ArrayList<LeaderCard> hand;
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
        this.faithTrack = new FaithTrack(game);
        this.game = game;



    }

    public void addWhiteMarble(Resource res){
        whiteMarbles.add(res);


    }
    public void addDiscount(Resource res){
        discount.add(res);

    }
    public ArrayList<Resource> getWhiteMarbles() { return whiteMarbles;}


    public ArrayList<Resource> getDiscount() {
        return discount;
    }



    public int getResourceNumber(Resource res){
        return  getStrongBoxResource(res) + getWarehouseResource(res);
    }

    //TODO
    //REMOVE
    public ArrayList<LeaderCard> getHand() {
        return hand;
    }

    public void addLeaderCard(LeaderCard card){
        this.hand.add(card);
        card.setOwner(this);
    }

    public void addDevelopmentCard(DevelopmentCard card, CardSlot cardSlot) throws developmentCardSlotLimitExceededException,
            invalidDevelopmentCardLevelPlacementException {

        cardSlot.addCard(card);
        int totalDevCardNumber = Arrays.stream(this.cardSlot).mapToInt(CardSlot::getGenericCardNumber).sum();
        if(totalDevCardNumber == 7) this.game.gameIsOver();

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

    public void giveFaithPoints(int steps)  {
        this.faithTrack.addFaith(steps); }

    public int getCardNumber(int level, Color color){
        return Arrays.stream(this.cardSlot).mapToInt(s->s.getCardNumber(level,color)).sum();


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

    public void discardLeaderCard(LeaderCard card) throws DiscardActiveCardException {
        if(card.isActive())
            throw new DiscardActiveCardException();
        hand.remove(card);
        card.discardCard();
        giveFaithPoints(1);
    }


    public CardSlot[] getCardSlot(){
        return this.cardSlot;
    }

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
}
