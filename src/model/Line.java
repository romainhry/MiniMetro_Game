package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Line {
    private Color color;
    private List<Station> stationList;
    private List<Train> trainList;
    private List<Position> path ;

    public Line(Station a, Station b, Color c, double middleX, double middleY) {
        stationList = new ArrayList<>();
        trainList = new ArrayList<>();
        path = new ArrayList<>();
        stationList.add(a); stationList.add(b);
        path.add(a.getPosition());  path.add(new Position(middleX,middleY)); path.add(b.getPosition());
        color = c;

        a.addLine(this); b.addLine(this);
    }

    public Color getColor() {
        return color;
    }

    public List <Station > getStationList () {
        return stationList;
    }

    public List <Position>  getPath () {
        return path;
    }

    public boolean addAllowed(Station s) {
        boolean allowed = stationList.indexOf(s) != -1
                &&  stationList.indexOf(s) != 0
                &&  stationList.indexOf(s) != stationList.size()-1;
        return !allowed;
    }

    public void addStation(Station station,double middleX,double middleY) {
        path.add(new Position(middleX,middleY));
        path.add(station.getPosition());
    	stationList.add(station);
    }

    public void addStation(int index,Station station, double middleX, double middleY) {
        path.add(index,new Position(middleX,middleY));
        path.add(index,station.getPosition());
        stationList.add(index,station);

        /* todo when the station is the loop */
        station.addLine(this);
    }

    public void removeStation(Station station) {

        /* todo when the station is the loop */
        station.removeLine(this);

        int index = stationList.indexOf(station);
        if(index == 0) {
            stationList.remove(station);
            path.remove(0);
            if(path.size()>0)
                path.remove(0);
        }
        else {
            stationList.remove(station);
            path.remove(path.size()-1);
            if(path.size()>0)
                path.remove(path.size()-1);
        }

    }
    
    public void addTrain(Train train) {
    	
    }

    public boolean containsShape(ShapeType s) {
        for(Station  st : stationList) {
            if (st.getType() == s)
                return true ;
        }
        return false;
    }

    public boolean isLoop () {
        return path.get(0).equals(path.get(path.size()-1)) ;
    }

    public String toString() {
        String s = "";
        for (Station st : stationList)
            s+=st+"\n";
        return s;
    }



}
