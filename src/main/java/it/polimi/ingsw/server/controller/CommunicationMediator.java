package it.polimi.ingsw.server.controller;

public class CommunicationMediator {
    private boolean mainActionDone;
    private boolean leaderActionDone;

    public CommunicationMediator() {
        this.mainActionDone = false;
        this.leaderActionDone = false;
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public boolean isLeaderActionDone() {
        return leaderActionDone;
    }

    public void setMainActionDone() {
        this.mainActionDone = true;
    }

    public void setLeaderActionDone() {
        this.leaderActionDone = true;
    }

    public void refresh(){
        this.mainActionDone = false;
        this.leaderActionDone = false;
    }
}
