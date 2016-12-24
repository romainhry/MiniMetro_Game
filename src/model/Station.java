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
    private java.util.List<Client> clientList;
    private java.util.List<Station> links;
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
        lines = new ArrayList<>();
        distances[t.ordinal()]=0;
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
    	lines.add(line);
    }
    
    public void removeLine(Line line) {
    	lines.remove(line);
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

    /*
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
    */


    public  void computeDistances() {
        ArrayList <Station> bsearch = new ArrayList<>();
        bsearch.add(this);

        this.distances[type.ordinal()] = 0;

        int distance=1;
        while(!bsearch.isEmpty()) {
            ArrayList <Station> bbsearch =new ArrayList<>();
            for(Station s : bsearch ) {
                for(Station s2 : s.links) {
                    if(!s2.checked) {
                        if (this.distances[s2.type.ordinal()] == -1) {
                            this.distances[s2.type.ordinal()] = distance;
                        }
                        bbsearch.add(s2);
                    }
                }
                s.setChecked(true);
            }
            bsearch=bbsearch;
            ++distance;
        }
    }


    public static void computeAllDistances() {
        for( Station st : stationL) {
            Arrays.fill(st.distances,-1);
            resetChecked();
         //   st.computeDistances(st,0);
            st.computeDistances();
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
        Station s4 = new Station(ShapeType.PENTAGON,new Position(5,8));
        Station s5 = new Station(ShapeType.CROSS,new Position(5,5));
        Station s6 = new Station(ShapeType.TRIANGLE,new Position(5,6));
        Station s7 = new Station(ShapeType.OVAL,new Position(5,7));
        Station s8 = new Station(ShapeType.GEM,new Position(5,8));

        s1.addLink(s2); s3.addLink(s4); s2.addLink(s3); s3.addLink(s5); s3.addLink(s6); s7.addLink(s5); s8.addLink(s1);

        stationL.add(s1);stationL.add(s2);stationL.add(s3);stationL.add(s4);stationL.add(s5);stationL.add(s6);stationL.add(s7);stationL.add(s8);

        double start = System.nanoTime();
        computeAllDistances();
        System.out.println("Computation done in : "+(System.nanoTime()-start)/1000000+" ms");
        for(Station s : stationL)
            System.out.println(s);
    }
}
