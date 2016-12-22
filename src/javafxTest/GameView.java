package javafxTest;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import model.Client;
import model.Game;
import model.Station;
import model.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KadirF on 20/12/2016.
 */
public class GameView {

    HashMap<Station,fxStation> stations;
    HashMap<Train,fxTrain> trains;
    HashMap<Client,fxClient> clients;
    List<Shape> links = new ArrayList<>();

    private Group group;

    public GameView(Group g) {
        stations = new HashMap<>();
        trains = new HashMap<>();
        group = g;
    }

    public void addNode(Node n) {
        group.getChildren().add(n);
    }

    public void put(Station s) {
        stations.put(s,new fxStation(s));
        group.getChildren().add(stations.get(s).shape);
    }

    public void put(Train t) {
        trains.put(t,new fxTrain(t));
        group.getChildren().add(trains.get(t));
    }

    public void put(Client c) {
        clients.put(c,new fxClient(c));
        group.getChildren().add(clients.get(c).shape);

    }

    public fxTrain get(Train t) {
        return trains.get(t);
    }

    public fxStation get(Station s) {
        return stations.get(s);
    }

    public fxClient get(Client c) { return clients.get(c);}

    public void add(Shape l) { links.add(l);}

    public boolean intersects (Shape f) {
        for(Shape l : links) {
            Shape intersect = Shape.intersect(f, l);
            if(intersect.getBoundsInLocal().getWidth() != -1)
                return true;
        }
        return false;
    }
}
