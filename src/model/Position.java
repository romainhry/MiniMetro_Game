package model;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Position {
    private int x ;
    private int y ;

    Position(int x,int y) {
        this.x=x;
        this.y=y;
    }
    
    public double distance (Position p) {
    	return 0.0;
    }
    
    public double distance (int x, int y) {
    	return 0.0;
    }

    public String toString() {
        return "("+x+","+y+")";
    }
}
