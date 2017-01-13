package model;

import javafx.Controller;

import java.util.*;

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
    private boolean isFull;

    private int[] distances = new int[ShapeType.values().length];
    private boolean checked = false;

    static ArrayList<Station> stationL = new ArrayList<>();

    public Station (ShapeType t, Position p) {
        pos=p;
        type = t;
        links = new ArrayList<Station>();
        lines = new ArrayList<>();
        distances[t.ordinal()]=0;
        clientList = new ArrayList<Client>();
        capacity=8;//tmp

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

    public boolean getIsFull(){return  isFull;}

    public void setIsFull(boolean b){ isFull=b;}

    public void startFullTimer () {
        isFull=true;
        System.out.println("start increase");
        Controller.gameView.startIncreaseArc(this);
    }
    public void decreaseFullTimer () {
        isFull=false;
        System.out.println("start decrease");
        Controller.gameView.startDecreaseArc(this);
    }
    
    public void setCapacity(int capacity) {
    	this.capacity=capacity;
    }
    public int getCapacity() {return this.capacity;}

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
}

