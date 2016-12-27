package javafxTest;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.Position;
import model.Train;

import static java.awt.geom.Point2D.distance;

/**
 * Created by KadirF on 18/12/2016.
 */
public class fxTrain extends Group {

    static  final double clientScale = 0.25 ;
    static final double width = 50;
    static final double height = 25 ;

    double trainX,trainY;
    Train train;

    public fxTrain(Train t) {
        super();
        Position p = t.getLine().getPath().get(0);
        trainX = p.getX(); trainY = p.getY();
        getChildren().add(new Rectangle(p.getX()-width/2,p.getY()-(height/2),width,height));
        train = t;
    }

    public fxTrain () {
        super();

        getChildren().add(new Rectangle(250, 300, width, height));
        trainX = 250;
        trainY = 300;
    }

    public void move (Position p) {
        double rotation,x = p.getX(), y = p.getY();
        System.err.println("Moving to "+p +" "+train.getDirection());

        if( x == trainX)
            rotation = 90;
        else if( y == trainY)
            rotation = 0;
        else if(x > trainX && y > trainY)
            rotation = 45 ;
        else if( x > trainX && y < trainY)
            rotation = 135;
        else if( y > trainY)
            rotation = -45;
        else
            rotation = -135;

        setRotate(rotation);
        TranslateTransition move = new TranslateTransition(new Duration(10*distance(trainX,trainY,x,y)),this);
        move.setByX(x-trainX); move.setByY(y-trainY);
        move.play();
        trainX = x;
        trainY = y;
        move.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                train.move();
            }
        });
    }

    public void move (double x, double y) {
        double rotation;

        if( x == trainX)
            rotation = 90;
        else if( y == trainY)
            rotation = 0;
        else if(x > trainX && y > trainY)
            rotation = 45 ;
        else if( x > trainX && y < trainY)
            rotation = 135;
        else if( y > trainY)
            rotation = -45;
        else
            rotation = -135;


        setRotate(rotation);
        TranslateTransition move = new TranslateTransition(new Duration(10*distance(trainX,trainY,x,y)),this);
        move.setByX(trainX-x); move.setByY(trainY-y);
        move.play();
        trainX = x;
        trainY = y;
    }

    public void addClient (Shape s) {
        s.setScaleX(clientScale);
        s.setScaleY(clientScale);
        int index = this.getChildren().size() - 1;
        double dx = trainX+(width/8) + (index%4)*(width/4);
        double dy = trainY+(height/4) + (index/4) *(height/2);
        s.setLayoutX(dx);
        s.setLayoutY(dy);
        s.setFill(Color.WHITE);
        s.setStroke(Color.WHITE);
        getChildren().add(s);
    }

    public void removeClient (Shape s) {
        getChildren().add(s);
    }
}
