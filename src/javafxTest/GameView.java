package javafxTest;

import model.Game;
import model.Station;
import model.Train;

import java.util.HashMap;

/**
 * Created by KadirF on 20/12/2016.
 */
public class GameView {

    HashMap<Station,fxStation> stations;
    HashMap<Train,fxTrain> trains;

    GameView() {
        stations = new HashMap<>();
        trains = new HashMap<>();
    }

    void put(Station s) {
        stations.put(s,new fxStation(s));
    }
    public fxTrain get(Train t) {
        return trains.get(t);
    }

    public fxStation get(Station s) {
        return stations.get(s);
    }
}
