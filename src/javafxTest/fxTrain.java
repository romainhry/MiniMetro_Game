package javafxTest;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Position;
import model.Train;

/**
 * Created by KadirF on 18/12/2016.
 */
public class fxTrain extends Group {

    public fxTrain( Train t ) {
        super();
        Position p = t.getLine().getPath().get(0);
        getChildren().add(new Rectangle(p.getX(),p.getY(),50,25));
    }

    void addClient (Shape s) {
        getChildren().add(s);
    }

    void removeClient (Shape s) {
        getChildren().add(s);
    }
}
