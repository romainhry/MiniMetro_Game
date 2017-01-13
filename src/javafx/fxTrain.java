package javafx;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.Client;
import model.Position;
import model.Train;

import static java.awt.geom.Point2D.distance;
import static javafx.defaultShapes.*;
import static javafx.defaultShapes.getCross;
import static model.Position.angle;

/**
 * Created by KadirF on 18/12/2016.
 */
public class fxTrain extends Group {

    static  final double clientScale = 0.25 ;
    static final double width = 50;
    static final double height = 25 ;
    TranslateTransition move;

    double trainX,trainY;
    Rectangle r;
    Train train;


    public fxTrain(Train t) {
        super();
        Position p = t.getLine().getPath().get(0);
        trainX = p.getX(); trainY = p.getY();
        r = new Rectangle(p.getX()-width/2,p.getY()-(height/2),width,height);
        r.setFill(t.getLine().getColor());
        getChildren().add(r);
        train = t;
    }

    public fxTrain () {
        super();

        getChildren().add(new Rectangle(250, 300, width, height));
        trainX = 250+width/2;
        trainY = 300+height/2;
    }

    public void move (Position p, int speed) {
        if(speed==0) {
            System.err.println("SPEED == 0 : TRAIN STOP");
            return;
        }
        double rotation = angle(p,new Position(trainX,trainY)),x = p.getX(), y = p.getY();
        setRotate(rotation);
        /* +100 to avoid bug with a duration equal to 0  with the translate transition */
        double millis = 10*distance(trainX,trainY,x,y)+100;
        move = new TranslateTransition(new Duration(millis),this);
        move.setByX(x-trainX); move.setByY(y-trainY);
        move.play();
        trainX = x;
        trainY = y;


        int nextPointIndex = train.getNextPointIndex();



        move.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nextPointIndex%2 == 0) {
                    train.stopAtStation();
                }
                else {
                    train.move();
                }
            }
        });

    }



    public void addClient (Shape s) {

        s.setScaleX(clientScale);
        s.setScaleY(clientScale);


        int index = this.getChildren().size() - 1;

        double tempX = trainX, tempY = trainY;


        Rectangle r = (Rectangle) getChildren().get(0);

        double dx = r.getX()+(width/8) + (index%4)*(width/4);
        double dy = r.getY()+(height/4) + (index/4) *(height/2);



        s.setLayoutX(dx);
        s.setLayoutY(dy);


        s.setFill(Color.WHITE);
        s.setStroke(Color.WHITE);

        getChildren().add(s);
        //Controller.group2.getChildren().add(s);
        System.err.println(s);
        trainX = tempX;
        trainY = tempY;
    }

    public Shape addClient (Client cl) {
        Shape shape;



        int index = this.getChildren().size() - 1;
        Rectangle r = (Rectangle) getChildren().get(0);

        double dx = r.getX()+(width/8) + (index%4)*(width/4);
        double dy = r.getY()+(height/4) + (index/4) *(height/2);

        switch(cl.getType()) {
            case CIRCLE:
                Circle c = getCircle(); c.setCenterX(dx); c.setCenterY(dy);
                shape = c; break;
            case SQUARE:
                Polygon square = getSquare();
                for(int i  = 0;i<square.getPoints().size();i+=2) {
                    double tempX = square.getPoints().get(i) ;
                    double tempY = square.getPoints().get(i+1);
                    square.getPoints().set(i,dx+tempX);
                    square.getPoints().set(i+1,dy+tempY);
                }
                shape = square; break;
            case TRIANGLE:
                Polygon p = getTriangle();
                for(int i  = 0;i<p.getPoints().size();i+=2) {
                    double tempX = p.getPoints().get(i) ;
                    double tempY = p.getPoints().get(i+1);
                    p.getPoints().set(i,dx+tempX);
                    p.getPoints().set(i+1,dy+tempY);
                }
                shape = p; break;
            case STAR:
                Polygon star = getStar();
                for(int i  = 0;i<star.getPoints().size();i+=2) {
                    double tempX = star.getPoints().get(i) ;
                    double tempY = star.getPoints().get(i+1);
                    star.getPoints().set(i,dx+tempX);
                    star.getPoints().set(i+1,dy+tempY);
                }
                shape = star; break;
            case CROSS:
                Polygon cross = getCross();
                for(int i  = 0;i<cross.getPoints().size();i+=2) {
                    double tempX = cross.getPoints().get(i) ;
                    double tempY = cross.getPoints().get(i+1);
                    cross.getPoints().set(i,dx+tempX);
                    cross.getPoints().set(i+1,dy+tempY);
                }
                shape = cross; break;

            default: shape = null;
        }

        shape.setScaleX(clientScale);
        shape.setScaleY(clientScale);


        shape.setFill(Color.WHITE);
        shape.setStroke(Color.WHITE);
        getChildren().add(shape);
        return shape;
        //Controller.group2.getChildren().add(s);


    }



    public void removeClient (Shape s) {
        System.err.println("REMOVAL sucess ? : "+getChildren().remove(s));
    }

    public void pause() {
        move.pause();
    }

    public void resume() {

        move.play();
    }
}
