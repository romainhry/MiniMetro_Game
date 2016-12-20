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
        return new Rectangle(0,0,40,40);
    }

    public static Circle getCircle () {
        return new Circle (20.0) ;
    }

    public static Polygon getTriangle () {
       return new Polygon(0.0, 0.0, 20.0, -34.64, 40.0, 0.0 );
    }

    public static Polyline getEndLine() {
        return  new Polyline(0,0,15,0,15,-25,15,0,30,0);
    }



}
