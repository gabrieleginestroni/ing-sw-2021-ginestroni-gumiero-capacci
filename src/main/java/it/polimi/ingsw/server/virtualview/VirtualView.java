package it.polimi.ingsw.server.virtualview;


import it.polimi.ingsw.server.controller.Player;
import java.util.List;

public class VirtualView {

    private  MarketObserver marketObserver;
    private  LorenzoObserver lorenzoObserver;
    private  GridObserver gridObserver;
    private  List<Player> players;


    public VirtualView() {

        this.marketObserver = null;
        this.lorenzoObserver = null;
        this.gridObserver = null;
        this.players = null;
    }

    public void setMarketObserver(MarketObserver marketObserver){ this.marketObserver = marketObserver ; }
    public void setLorenzoObserver(LorenzoObserver lorenzoObserver){ this.lorenzoObserver = lorenzoObserver; }
    public void setGridObserver(GridObserver gridObserver){ this.gridObserver = gridObserver;}
    public void setPlayers(List<Player> players){ this.players = players;}

    public void updateBoardVirtualView(){}
    public void updateMarketVirtualView(){}
    public void updateLorenzoVirtualView(){}
    public void updateGridVirtualView(){}


}
