package javafxTest;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KadirF on 20/12/2016.
 */
public class GameView {

    HashMap<Station,fxStation> stations;
    HashMap<Train,fxTrain> trains;
    HashMap<Client,fxClient> clients;
    HashMap<model.Line,ArrayList<Shape>> lineLinks;
    HashMap<model.Line,Shape[]> lineEnds;
    List<Shape> links = new ArrayList<>();
    Shape river;
    private Group group;
    private Controller controller;
    private fxClock clock;

    public GameView(Group g,Controller c) {
        stations = new HashMap<>();
        trains = new HashMap<>();
        lineLinks = new HashMap<>();
        lineEnds = new HashMap<>();
        clients = new HashMap<>();
        group = g;
        controller = c;
        clock = new fxClock(1100,40,16);
        group.getChildren().add(clock);
    }

    public void updateClock (int hour, String dayName) {
        clock.moveNeedle(hour);
        clock.setDay(dayName);
    }

    public void addRiver(Shape r)
    {
        river = r;
    }

    public void createLine(Line l,Shape end1, Shape end2) {
        ArrayList<Shape> list = new ArrayList<>();
        lineLinks.put(l,list);

        Shape[] ends = new Shape[2];
        ends[0] = end2; ends[1] = end1;
        lineEnds.put(l,ends);
    }

    public void setLineEnd (Line l, Shape end,boolean inFirst) {
        Shape [] ends  = lineEnds.get(l);
        if(inFirst)
            ends[0] = end;
        else
            ends[1] = end;
    }

    public void removeEnds(Line l) {
        Shape [] ends = lineEnds.get(l);
        System.err.println("REMOVING ENDS "+group.getChildren().remove(ends[0])+" " +group.getChildren().remove(ends[1]));
    }

    public Shape getLineLink(Line l,boolean inFirst) {
        if(inFirst)
            return lineLinks.get(l).get(0);
        else
            return lineLinks.get(l).get(lineLinks.get(l).size()-1);
    }

    public Shape getNextLineLink(Line l, Shape currentLink) {
        ArrayList<Shape> list = lineLinks.get(l);
        System.err.println("Index of current Link " + list.indexOf(currentLink));
        if(list.indexOf(currentLink)==0)
            return list.get(1);
        else
            return list.get(list.size()-2);
    }
    public Station getNextStation(Line l, Shape nextLink) {
        ArrayList<Shape> list = lineLinks.get(l);
        boolean loop = l.isLoop();
        System.err.println("IS LOOP "+loop+"\nIndex of next Link : "+list.indexOf(nextLink));
        if(list.indexOf(nextLink)==0)
            return l.getStationList().get(1);
        else
            return l.getStationList().get(l.getStationList().size()-2);
    }

    public void addLineLink(Line l,Shape link,boolean inFirst) {
        ArrayList<Shape> list = lineLinks.get(l);
        if(inFirst) {
            list.add(0, link);
            System.err.println("ADD IN FIRST");
        }
        else {
            list.add(link);
            System.err.println("ADD IN LAST");
        }
        links.add(link);
        System.err.println("Index of created LINK : "+list.indexOf(link));

    }

    public void removeLineLink(Line l, boolean inFirst) {
        Shape s;
        if(inFirst) {
            s = lineLinks.get(l).remove(0);
            System.err.println("REMOVING IN FIRST");
        }
        else {
            s = lineLinks.get(l).remove(lineLinks.get(l).size() - 1);
            System.err.println("REMOVING IN LAST");
        }

        group.getChildren().remove(s);
        links.remove(s);

        if(lineLinks.get(l).size()==0)
            lineLinks.remove(l);
    }

    public void removeLineLink(Line l, Shape link) {
        lineLinks.get(l).remove(link);
        group.getChildren().remove(link);
        links.remove(link);
        if(lineLinks.get(l).size()==0)
            lineLinks.remove(l);
    }

    public void addNode(Node n) {
        group.getChildren().add(n);
        System.err.println("ADDED NODE");
    }

    public void put(Station s) {
        stations.put(s,new fxStation(s));
        group.getChildren().add(stations.get(s).shape);
        controller.addStationEvent(stations.get(s).shape,s);
    }

    public void put(Train t) {
        trains.put(t,new fxTrain(t));
        group.getChildren().add(1,trains.get(t));
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

    public void remove(Client c) {
        fxClient fxc = get(c);
        System.err.println("REMOVING");
        group.getChildren().remove(fxc.shape);
        clients.remove(c);
    }

    public void addClientToTrain(Train tr, Client client) {
        fxTrain fxTr = get(tr);
        Shape s = fxTr.addClient(client);
        fxClient fxclient = new fxClient(s);
        clients.put(client,fxclient);
    }

    public void removeClientFromTrain(Train tr,Client client) {
        fxTrain fxTr = get(tr);
        fxClient fxclient = get(client);
        fxTr.removeClient(fxclient.shape);
    }

    public void removeTrain(Train tr) {
        fxTrain fxtr = get(tr);
        group.getChildren().remove(fxtr);
        trains.remove(fxtr);
        fxtr = null;
    }


    public boolean intersects (Shape f) {
        for(Shape l : links) {
            Shape intersect = Shape.intersect(f, l);
            if(intersect.getBoundsInLocal().getWidth() != -1) {
                System.err.println("INTERSECTS !! ");
                return true;
            }
        }
        return false;
    }

    public boolean intersectRiver (Shape f) {
        Shape intersect = Shape.intersect(f, river);
        if(intersect.getBoundsInLocal().getWidth() != -1) {
            System.err.println("INTERSECTS RIVER !! ");
            return true;
        }
        return false;
    }

    public void move(Train train) {
        fxTrain fxTrain = get(train);
        fxTrain.move(train.getLine().getPath().get(train.getNextPointIndex()));
    }
}
