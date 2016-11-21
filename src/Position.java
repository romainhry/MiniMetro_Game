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

    public String toString() {
        return "("+x+","+y+")";
    }
}
