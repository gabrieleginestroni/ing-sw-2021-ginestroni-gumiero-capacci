package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;

public class Board {
    private final CardSlot[] cardSlot;
    private final Warehouse wareHouse;
    private final StrongBox strongBox;
    private boolean inkwell;


    public Board() {
        this.wareHouse = new Warehouse();
        this.cardSlot = new CardSlot[3];
        this.strongBox = new StrongBox();
        this.inkwell = false;
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

}
