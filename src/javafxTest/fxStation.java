package javafxTest;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.Station;

import static javafxTest.defaultShapes.*;

public class fxStation {
    Shape shape;

    public fxStation(Station s) {
        switch(s.getType()) {
            case CIRCLE: shape = getCircle(); break;
            case SQUARE: shape = getSquare(); break;
            case TRIANGLE: shape = getTriangle() ; break;
            case STAR: shape = getStar(); break;
            case CROSS: shape = getCross(); break;
            default: shape = null;
        }
        shape.setTranslateX(s.getPosition().getX());
        shape.setTranslateY(s.getPosition().getY());
    }
}
