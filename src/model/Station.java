package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Station {
    private ShapeType type ;
    private Position pos ;
    private List<Client> clientList;
    private List<Station> links;
    private List<Line> lines;
    private Timer fullCapacity ;
    private int capacity;

    private int[] distances = new int[ShapeType.values().length];
    private boolean checked = false;
    //for tests
    static ArrayList<Station> stationL = new ArrayList<>();

    public Station (ShapeType t, Position p) {
        pos=p;
        type = t;
        links = new ArrayList<Station>();
        distances[t.ordinal()]=0;
        clientList = new ArrayList<Client>();

    }

    public ShapeType getType() {
        return type;
    }
    public Position getPosition () {return pos;}

    public List<Client> getClientList () {
        return clientList;
    }

    public int getMinDistance (ShapeType s) {
        return distances [s.ordinal()];
    }


    public int[] getDistances () {
        return distances;
    }

    public void setChecked(boolean c) {
        checked = c;
    }
    
    public void addClient (Client client) {
    	clientList.add(client);
    }
    
    public void removeClient(Client client) {
        clientList.remove(client);
    }
    
    public void addLine(Line line) {
    	
    }
    
    public void removeLine(Line line) {
    	
    }
    
    public void startFullTimer () {
    
    }
    
    public void decreaseFullTimer () {
    	
    }
    
    public void setCapacity(int capacity) {
    	
    }

    public String toString() {
        String s ="model.Station "+type+" at "+pos+" ";
        for(ShapeType stype : ShapeType.values()) {
            if(distances[stype.ordinal()]!=-1)
                s+=stype+" : "+distances[stype.ordinal()]+" ";
        }
        return s;
    }

    public void addLink(Station st) {
        links.add(st);
        st.links.add(this);
    }

    public void removeLink (Station st) {
        links.remove(st);
        st.links.remove(this);
    }


    public  void computeDistances (Station recur,int distance) {
        recur.checked = true;
        if(this.distances[recur.type.ordinal()] == -1 || this.distances[recur.type.ordinal()] > distance ) {
            this.distances[recur.type.ordinal()]=distance;
        }
        for(Station s: recur.links) {
            if(!s.checked)
                computeDistances(s, distance + 1);
        }
    }

    public static void computeAllDistances() {
        for( Station st : stationL) {
            Arrays.fill(st.distances,-1);
            resetChecked();
            st.computeDistances(st,0);
        }
    }

    public  static void resetChecked() {
        for(Station s: stationL)
            s.checked=false;
    }


    public static void main (String args[]) {

        Station s1 = new Station(ShapeType.CIRCLE,new Position(5,5));
        Station s2 = new Station(ShapeType.TRIANGLE,new Position(5,6));
        Station s3 = new Station(ShapeType.SQUARE,new Position(5,7));
        Station s5 = new Station(ShapeType.CROSS,new Position(5,5));
        Station s6 = new Station(ShapeType.TRIANGLE,new Position(5,6));

        s1.addLink(s2); s2.addLink(s3); s3.addLink(s5); s3.addLink(s6);

        stationL.add(s1);stationL.add(s2);stationL.add(s3);stationL.add(s5);stationL.add(s6);

        double start = System.nanoTime();
        computeAllDistances();
        System.out.println("Computation done in : "+(System.nanoTime()-start)/1000000+" ms");
        for(Station s : stationL)
            System.out.println(s);
    }
}

