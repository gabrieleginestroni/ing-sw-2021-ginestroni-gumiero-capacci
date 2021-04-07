package it.polimi.ingsw.model.board;

import java.util.Arrays;
import java.util.Optional;

public class FaithTrack {
    private int faithMarker;
    private final FaithTrackSection[] sections;
    private static final boolean[] vaticanReport={false,false,false};


    public FaithTrack() {
        this.faithMarker = 0;
        this.sections = new FaithTrackSection[3];
        this.sections[0]= new FaithTrackSection(2,5,8);
        this.sections[1]= new FaithTrackSection(3,12,16);
        this.sections[2]= new FaithTrackSection(4,19,24);


    }

    public void addFaith(int steps) throws vaticanReportSection1Exception,
            vaticanReportSection3Exception, vaticanReportSection2Exception {

        this.faithMarker += steps;
        checkVaticanReport();
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    private void checkVaticanReport() throws vaticanReportSection3Exception,
            vaticanReportSection2Exception, vaticanReportSection1Exception {

        if (this.faithMarker >= sections[0].getLastTileNumber() && !vaticanReport[0])
            throw new vaticanReportSection1Exception(sections[0].getFirstTileNumber());
            else if(this.faithMarker >= sections[1].getLastTileNumber() && !vaticanReport[1])
                throw new vaticanReportSection2Exception(sections[1].getFirstTileNumber());
                else if(this.faithMarker >= sections[2].getLastTileNumber() && !vaticanReport[2])
                        throw new vaticanReportSection3Exception(sections[2].getFirstTileNumber());

    }

    public static void vaticanReportSet(int index){
        vaticanReport[index] = true;
    }

    public void computeActivationPopeTile(int index){
        if(this.faithMarker>=this.sections[index].getFirstTileNumber()){
            this.sections[index].activatePopeTile();
        }
    }


    public int getVictoryPoints(){
        int faithMarkerTot=0;

        int popeTileTot = Arrays.stream(sections).mapToInt(FaithTrackSection::getVictoryPoints).sum();

        if (this.faithMarker >= 3 && this.faithMarker <=5 ) {
            faithMarkerTot=1;
        } else if (this.faithMarker >= 6 && this.faithMarker <=8 ){
            faithMarkerTot=2;
        } else if (this.faithMarker >= 9 && this.faithMarker <=11 ){
            faithMarkerTot=4;
        } else if (this.faithMarker >= 12 && this.faithMarker <=14 ){
            faithMarkerTot=6;
        } else if(this.faithMarker >= 15 && this.faithMarker <=17 ){
            faithMarkerTot=9;
        } else if(this.faithMarker >= 18 && this.faithMarker <=20 ){
            faithMarkerTot=12;
        } else if(this.faithMarker >= 21 && this.faithMarker <=23 ){
            faithMarkerTot=16;
        } else if (this.faithMarker == 24) {
            faithMarkerTot=20;
        }

        return popeTileTot + faithMarkerTot;

    }
}
