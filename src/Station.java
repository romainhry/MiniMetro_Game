import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Station {
    private ShapeType type = null;
    private Position pos = null;
    private java.util.List<Client> clientList;
    private java.util.List<Station> nodes;
    private Timer fullCapacity = null;
    private int capacity;
    private int[] distances = new int[ShapeType.values().length];

    private Boolean checked = false;
    static ArrayList<Station> stationL = new ArrayList<>();

    Station (ShapeType t, Position p) {
        pos=p;
        type = t;
        nodes = new ArrayList<Station>();
        Arrays.fill(distances,-1);
        distances[t.ordinal()]=0;
    }

    public String toString() {
        String s ="Station "+type+" at "+pos+" ";
        for(ShapeType stype : ShapeType.values()) {
            if(distances[stype.ordinal()]!=-1)
                s+=stype+" : "+distances[stype.ordinal()]+" ";
        }
        return s;
    }

    public void addLink(Station st) {
        nodes.add(st);
        st.nodes.add(this);

        if(st.type!=type) {
            distances[st.type.ordinal()] = 1;
            st.distances[type.ordinal()] = 1;
        }


        for(ShapeType stype : ShapeType.values()) {

            if(  distances[stype.ordinal()] !=-1 && (distances[stype.ordinal()] +1 < st.distances[stype.ordinal()]  || st.distances[stype.ordinal()]==-1) ) {
                st.distances[stype.ordinal()] = distances[stype.ordinal()] +1 ;
                resetChecked();
                computeDistanceAdd(st,stype);
            }

            if( st.distances[stype.ordinal()] != -1 && (st.distances[stype.ordinal()] +1 < distances[stype.ordinal()] || distances[stype.ordinal()]==-1 ) ) {
                distances[stype.ordinal()] = st.distances[stype.ordinal()] +1 ;
                resetChecked();
                computeDistanceAdd(this,stype);
            }
        }



        double start = System.currentTimeMillis();
        resetChecked();
        computeDistanceAdd(this,st.type);


        System.out.println("ComputeAdd : "+(start-System.currentTimeMillis())+" ms");
        start=System.currentTimeMillis();

        resetChecked();
        computeDistanceAdd(st,type);

        System.out.println("ComputeAdd : "+(start-System.currentTimeMillis())+" ms");
    }

    public void removeLink (Station st) {
        nodes.remove(st);
        st.nodes.remove(this);
        //computeDistance(st,this);
    }

    public void computeDistanceAdd(Station s,ShapeType stype) {
        checked=true;
        for(Station st : s.nodes) {
            if(!st.checked && distances[stype.ordinal()] !=-1 && (distances[stype.ordinal()] +1 < st.distances[stype.ordinal()]  || st.distances[stype.ordinal()]==-1) ) {
                st.distances[stype.ordinal()] = s.distances[stype.ordinal()] +1 ;
                computeDistanceAdd(st,stype);
            }
        }
    }

    public void resetChecked() {
        for(Station s: stationL)
            s.checked=false;
    }


    public static void main (String args[]) {
        Station s1 = new Station(ShapeType.CIRCLE,new Position(5,5));
        Station s2 = new Station(ShapeType.TRIANGLE,new Position(5,6));
        Station s3 = new Station(ShapeType.SQUARE,new Position(5,7));
        Station s4 = new Station(ShapeType.PENTAGON,new Position(5,8));



        stationL.add(s1);stationL.add(s2);stationL.add(s3);stationL.add(s4);
        s1.addLink(s2);  s3.addLink(s4); s2.addLink(s3);

        for(Station s : stationL)
            System.out.println(s);
    }
}

