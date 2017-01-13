package javafx;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.Position;

public class defaultShapes {

    /* Defines the scale for the littles shapes*/
    public static double littlesScale = 0.5;

    /* defines the length of the side of a square in which all shapes must be contained*/
    public static double maxLength = 30 ;
    
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



    public static Polygon getSquare() {
        Polygon p = new Polygon(-15,-15,15,-15,15,15,-15,15);
        p.setFill(Color.WHITESMOKE);
        p.setStrokeWidth(6);
        p.setStroke(Color.DARKBLUE);
        return p;
    }

    public static Circle getCircle () {
        Circle p = new Circle (15.0);
        p.setFill(Color.WHITESMOKE);
        p.setStrokeWidth(6);
        p.setStroke(Color.RED);
        return p;
    }

    public static Polygon getTriangle () {
        Polygon p = new Polygon(-20,17.335,0,-17.335,20,17.335);
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
        p.setRotate(180);

        double dx=2,dy=-5;

        for(int i  = 0;i<p.getPoints().size();i+=2) {
            double tempX = p.getPoints().get(i) ;
            double tempY = p.getPoints().get(i+1);
            p.getPoints().set(i,dx+tempX);
            p.getPoints().set(i+1,dy+tempY);
        }

        return p;
    }


    public static Polygon getCross() {
        Polygon p = new Polygon(
                0,0,
                -15,0,
                15,0,
                0,0,
                0,15,
                0,-15,
                0,0
        );
        p.setFill(Color.BLACK);
        p.setStrokeType(StrokeType.OUTSIDE);
        p.setStrokeWidth(5);
        p.setStroke(Color.BLACK);
        p.setStrokeLineCap(StrokeLineCap.ROUND);
        return p;
    }


    //Shape for client

    public static Polygon getLittleSquare() {
        Polygon r = getSquare();
        r.setScaleX(littlesScale) ;
        r.setScaleY(littlesScale);
        return r;
    }

    public static Circle getLittleCircle () {
        Circle c = getCircle();
        c.setScaleX(littlesScale);
        c.setScaleY(littlesScale);
        return c;
    }

    public static Polygon getLittleTriangle () {
        Polygon p = getTriangle();
        p.setScaleX(littlesScale);
        p.setScaleY(littlesScale);
        return p;
    }

    public static Polygon getLittleStar() {
        Polygon p = getStar();
        p.setScaleX(littlesScale);
        p.setScaleY(littlesScale);
        p.setRotate(180);
        return p;
    }

    public static Polygon getLittleCross() {
        Polygon p = getCross();
        p.setScaleX(littlesScale);
        p.setScaleY(littlesScale);
        return p;
    }
}
