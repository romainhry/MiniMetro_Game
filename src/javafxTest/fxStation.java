package javafxTest;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Station;

import static javafxTest.defaultShapes.*;

/**
 * Created by KadirF on 20/12/2016.
 */
public class fxStation {
    Shape shape;

    public fxStation(Station s) {
        switch(s.getType()) {
            case CIRCLE: shape = getCircle(); break;
            case SQUARE: shape = getSquare(); break;
            case TRIANGLE: shape = getTriangle() ; break;
            default: shape = null;
        }
        shape.setTranslateX(s.getPosition().getX());
        shape.setTranslateY(s.getPosition().getY());
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(10);
        shape.setFill(Color.GREY);
    }
}
