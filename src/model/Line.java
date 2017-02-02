package model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


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

    public List <Train> getTrainList () {
        return trainList;
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

        for(Train t : trainList) {
            t.setNextPointIndex(t.getNextPointIndex()+2);
        }
    }

    public void addStationFromLink(int index, Station station , double middleX, double middleY, double middleX2 , double middleY2) {
        stationList.add(index,station);
        int indexToAdd = (index-1)*2+1;
        path.remove((index*2)-1);

        path.add(indexToAdd,new Position(middleX2,middleY2));
        path.add(indexToAdd,station.getPosition());
        path.add(indexToAdd,new Position(middleX,middleY));

        for(Train t : trainList) {
            if(t.getNextPointIndex() > indexToAdd) {
                t.setNextPointIndex(t.getNextPointIndex() + 2);
                t.verif();
            }
        }

    }

    public void removeStation(Station station) {
        if(isLoop()) {
            System.err.println("looped cannot choose which to remove");
            return;
        }
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

        for(Train t : trainList) {
            /* prevents the trains to "teleport" by updating index*/
            if( index == 0) {
                t.setNextPointIndex(t.getNextPointIndex()-2);
                System.err.println("SUB 2 INDEX");
            }

            /* prevents array of bounds*/
            if (t.getLine() != null)
                t.verif();

        }

    }

    public void removeLoop(Station loop,boolean first) {
        if(first) {
            stationList.remove(0);
            path.remove(0);
            if(path.size()>0)
                path.remove(0);
        }
        else {
            stationList.remove(stationList.size() - 1);
            path.remove(path.size()-1);
            if(path.size()>0)
                path.remove(path.size()-1);
        }
    }
    
    public void addTrain(Train train) {
        trainList.add(train);
        train.setLine(this);
    }

    public void removeTrain(Train train) {
        trainList.remove(train);
    }

    public boolean containsShape(ShapeType s) {
        for(Station  st : stationList) {
            if (st.getType() == s)
                return true ;
        }
        return false;
    }

    public boolean isLoop () {
        return stationList.get(0) == stationList.get(stationList.size()-1) && stationList.size()!=1 ;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i<stationList.size(); ++i)
            s+=stationList.get(i)+"\n";
        return s;
    }



}
