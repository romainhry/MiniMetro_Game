package javafxTest;

import javafx.scene.shape.Polyline;
import model.Station;

/**
 * Created by KadirF on 18/12/2016.
 */
public class fxLink extends Polyline {

    fxLink() {
        super();
    }

    public fxLink(double x,double y,double middleX, double middleY, double x2,double y2) {
        super(x,y,middleX,middleY, x2, y2);
        setStrokeWidth(10);
    }

    public fxLink(double x,double y, double x2,double y2) {
        super(x,y, x2, y2);
        setStrokeWidth(10);
    }


    void test() {
        System.out.println("fxLINK");
    }
}
