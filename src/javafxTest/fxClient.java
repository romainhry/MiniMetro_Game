package javafxTest;

import javafx.scene.shape.Shape;
import model.Client;
import model.Station;

import static javafxTest.defaultShapes.getCircle;
import static javafxTest.defaultShapes.getSquare;
import static javafxTest.defaultShapes.getTriangle;
import static model.ShapeType.CIRCLE;
import static model.ShapeType.SQUARE;
import static model.ShapeType.TRIANGLE;

/**
 * Created by KadirF on 21/12/2016.
 */
public class fxClient {
    Shape shape;

    public fxClient(Client c) {
        switch(c.getType()) {
            case CIRCLE: shape = getCircle(); break;
            case SQUARE: shape = getSquare(); break;
            case TRIANGLE: shape = getTriangle() ; break;
            default: shape = null;
        }

    }
}
