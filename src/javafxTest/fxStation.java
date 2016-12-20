package javafxTest;

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
            // TO DO
            default: shape = null;
        }
        
    }
}
