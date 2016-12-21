package model;

/**
 * Created by KadirF on 02/12/2016.
 */
public class Inventory {
    private int lineNb ;
    private int tunnelNb ;
    private int wagonNb ;
    private int trainNb ;
    private int interchangeNb;

    public Inventory () {

    }

    public Inventory (int line,int tunnel, int wagon, int train,int inter) {
        lineNb = line;
        tunnelNb = tunnel;
        wagonNb = wagon;
        trainNb = train;
        interchangeNb = inter;
    }
}
