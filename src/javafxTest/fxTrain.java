package javafxTest;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.Position;
import model.Train;

/**
 * Created by KadirF on 18/12/2016.
 */
public class fxTrain extends Group {

    public fxTrain(Train t) {
        super();
        Position p = t.getLine().getPath().get(0);
        getChildren().add(new Rectangle(p.getX(),p.getY(),50,25));
    }

    public fxTrain () {
        super();
        getChildren().add(new Rectangle(0,0,50,25));
    }

    public void move (Position p) {
        TranslateTransition move = new TranslateTransition(new Duration(10*p.distance(getLayoutX(),getLayoutY())),this);
        move.setFromX(this.getLayoutX()); move.setFromY(this.getLayoutY());
        move.setToX(p.getX()); move.setToY(p.getY());
        move.play();
    }

    public void addClient (Shape s) {
        getChildren().add(s);
    }

    public void removeClient (Shape s) {
        getChildren().add(s);
    }
}
