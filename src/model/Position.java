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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position p) {
        return x == p.x && y == p.y;
    }
    
    public double distance (Position p) {
        int dx = p.x - x, dy = p.y - y;
    	return Math.sqrt((dx * dx) + (dy * dy));
    }
    
    public double distance (int x, int y) {
        int dx = x - this.x, dy = y - this.y;
        return Math.sqrt(dx*dx+dy*dy);
    }

    public String toString() {
        return "("+x+","+y+")";
    }
}
