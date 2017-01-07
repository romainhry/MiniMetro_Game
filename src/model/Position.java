package model;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Position {
    private double x ;
    private double y ;

    public Position(double x,double y) {
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean equals(Position p) {
        return x == p.x && y == p.y;
    }
    
    public double distance (Position p) {
        double dx = p.x - x, dy = p.y - y;
    	return Math.sqrt((dx * dx) + (dy * dy));
    }
    
    public double distance (double x, double y) {
        double dx = x - this.x, dy = y - this.y;
        return Math.sqrt(dx*dx+dy*dy);
    }

    public static double angle(double x1, double y1, double x2, double y2) {
        double rotation;
        if( x1 == x2)
            rotation = 90;
        else if( y1 == y2)
            rotation = 0;
        else if(x1 > x2 && y1 > y2)
            rotation = 45 ;
        else if( x1 > x2 && y1 < y2)
            rotation = 135;
        else if( y1 > y2)
            rotation = -45;
        else
            rotation = -135;
        return rotation;
    }

    public static double angle(Position a, Position b) {
        return angle(a.getX(),a.getY(),b.getX(),b.getY());
    }

    public String toString() {
        return "("+x+","+y+")";
    }
}
