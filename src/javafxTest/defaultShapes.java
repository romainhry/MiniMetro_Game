package javafxTest;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.Position;

/**
 * Created by KadirF on 18/12/2016.
 */
public class defaultShapes {

    public static Polygon TRIANGLE = new Polygon(0.0, 0.0, 20.0, -34.64, 40.0, 0.0 );
    public static Circle CIRCLE = new Circle (20.0) ;
    public static Rectangle SQUARE = new Rectangle(0,0,40,40);

    public static void setPosition(Shape s, double x, double y) {
        s.setTranslateX(x);
        s.setTranslateY(y);
        s.setStroke(Color.WHITE);
        s.setStrokeWidth(10);
        s.setFill(Color.GREY);
    }

    public static void setPosition(Shape s, Position p) {
        s.setTranslateX(p.getX());
        s.setTranslateY(p.getY());
        s.setStroke(Color.WHITE);
        s.setStrokeWidth(10);
        s.setFill(Color.GREY);
    }

    public static Rectangle getSquare() {
        Rectangle r = new Rectangle(0,0,40,40);
        return r ;
    }

    public static Polygon getSquare2() {
        return new Polygon(-20,-20,20,-20,20,20,-20,20);
    }

    public static Circle getCircle () {
        return new Circle (20.0) ;
    }

    public static Polygon getTriangle () {
       //return new Polygon(0.0, 0.0, 20.0, -34.64, 40.0, 0.0 );
        return new Polygon(-20,17.335,0,-34.67+17.335,20,17.335);
    }

    public static Polyline getEndLine() {
        return  new Polyline(0,0,15,0,15,-25,15,0,30,0);
    }



}
