package javafxTest;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import model.Station;

import static javafxTest.defaultShapes.*;

public class fxStation {
    Shape shape;


    public fxStation(Station s) {
        double x = s.getPosition().getX(), y = s.getPosition().getY();
        switch(s.getType()) {
            case CIRCLE:
                        Circle c = getCircle(); c.setCenterX(s.getPosition().getX()); c.setCenterY(s.getPosition().getY());
                        shape = c; break;
            case SQUARE: /* -20 to  place it the center of shape to the wanted point */
                        Polygon square = getSquare2();
                        for(int i  = 0;i<square.getPoints().size();i+=2) {
                            double tempX = square.getPoints().get(i) ;
                            double tempY = square.getPoints().get(i+1);
                            square.getPoints().set(i,x+tempX);
                            square.getPoints().set(i+1,y+tempY);
                        }
                        shape = square; break;
            case TRIANGLE:
                        Polygon p = getTriangle();
                        for(int i  = 0;i<p.getPoints().size();i+=2) {
                            double tempX = p.getPoints().get(i) ;
                            double tempY = p.getPoints().get(i+1);
                            p.getPoints().set(i,x+tempX);
                            p.getPoints().set(i+1,y+tempY);
                        }
                        shape = p; break;
            case STAR: shape = getStar(); break;
            case CROSS: shape = getCross(); break;
            default: shape = null;
        }
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(10);
        shape.setFill(Color.GREY);
    }
}
