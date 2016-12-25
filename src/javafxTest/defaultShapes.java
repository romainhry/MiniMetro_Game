package javafxTest;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.Position;

public class defaultShapes {

    public static Polygon TRIANGLE = new Polygon(0.0, 0.0, 20.0, -34.64, 40.0, 0.0 );
    public static Circle CIRCLE = new Circle (15.0) ;
    public static Rectangle SQUARE = new Rectangle(0,0,18,18);
    public static Polygon STAR = new Polygon(
            2,8,
            -2,23,
            -6,8,
            -21,8,
            -8,0,
            -13,-15,
            -2,-5,
            9,-15,
            4,0,
            17,8,
            2,8
    );
    public static Polygon CROSS = new Polygon(
            0,0,
            -10,0,
            10,0,
            0,0,
            0,10,
            0,-10,
            0,0
    );

    public static void setPosition(Shape s, double x, double y) {
        s.setTranslateX(x);
        s.setTranslateY(y);
    }

    public static void setPosition(Shape s, Position p) {
        s.setTranslateX(p.getX());
        s.setTranslateY(p.getY());
    }



    public static Rectangle getSquare() {
        Rectangle p = new Rectangle(0,0,18,18);
        p.setFill(Color.WHITESMOKE);
        p.setStrokeWidth(6);
        p.setStroke(Color.DARKBLUE);
    }

    public static Polygon getSquare2() {
        return new Polygon(-20,-20,20,-20,20,20,-20,20);
    }

    public static Circle getCircle () {
        Circle p = new Circle (15.0);
        p.setFill(Color.WHITESMOKE);
        p.setStrokeWidth(6);
        p.setStroke(Color.RED);
        return p;
    }

    public static Polygon getTriangle () {
        Polygon p = new Polygon(-20,17.335,0,-34.67+17.335,20,17.335);
        p.setFill(Color.WHITESMOKE);
        p.setStrokeType(StrokeType.INSIDE);
        p.setStrokeWidth(5);
        p.setStroke(Color.GREENYELLOW);
        return p;
    }

    public static Polygon getStar() {
        Polygon p = new Polygon(
                2,8,
                -2,23,
                -6,8,
                -21,8,
                -8,0,
                -13,-15,
                -2,-5,
                9,-15,
                4,0,
                17,8,
                2,8
        );

        p.setFill(Color.WHITESMOKE);
        p.setStrokeType(StrokeType.INSIDE);
        p.setStrokeWidth(4);
        p.setStroke(Color.DARKORANGE);
        return p;
    }


    public static Polygon getCross() {
        Polygon p = new Polygon(
                0,0,
                -10,0,
                10,0,
                0,0,
                0,10,
                0,-10,
                0,0
        );
        p.setFill(Color.BLACK);
        p.setStrokeType(StrokeType.OUTSIDE);
        p.setStrokeWidth(2);
        p.setStroke(Color.BLACK);
        p.setStrokeLineCap(StrokeLineCap.ROUND);
        return p;
    }




}
