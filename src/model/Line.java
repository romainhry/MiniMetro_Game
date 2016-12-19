package model;

import java.util.List;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Line {
    private String color;
    private List<Station> stationList;
    private List<Train> trainList;
    private List<Position> path ;

    public List <Station > getStationList () {
        return stationList;
    }

    public List <Position>  getPath () {
        return path;
    }

    public void addStation(Station station) {
    	
    }

    public void removeStation(Station Station) {

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
    



}
