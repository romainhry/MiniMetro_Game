package javafxTest;

import javafx.scene.shape.Shape;
import model.Client;
import model.Station;

import static javafxTest.defaultShapes.*;

public class fxClient {
    Shape shape;

    public fxClient(Client c) {
        switch(c.getType()) {
            case CIRCLE: shape = getCircle(); break;
            case SQUARE: shape = getSquare(); break;
            case TRIANGLE: shape = getTriangle() ; break;
            case STAR: shape = getStar(); break;
            case CROSS: shape = getCross(); break;
            default: shape = null;
        }

    }
}
