package javafx;

import javafx.animation.Timeline;
import javafx.scene.shape.*;
import model.Station;

import static javafx.defaultShapes.*;

public class fxStation {
    Shape shape;
    Arc arcTimer;
    Timeline arcTimeline;


    public fxStation(Station s) {
        double x = s.getPosition().getX(), y = s.getPosition().getY();
        arcTimer=new Arc(x, y,33, 33, 0, 0);
        //arcTimer.setType(ArcType.OPEN);
       // arcTimer.setStrokeWidth(10);
        //arcTimer.setStroke(Color.CORAL);
        //arcTimer.setFill(null);

        arcTimeline = new Timeline();
//        arcTimeline.setCycleCount(1);
        arcTimeline.setAutoReverse(true);

        switch(s.getType()) {
            case CIRCLE:
                        Circle c = getCircle(); c.setCenterX(s.getPosition().getX()); c.setCenterY(s.getPosition().getY());
                        shape = c; break;
            case SQUARE:
                        Polygon square = getSquare();
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
            case STAR:
                Polygon star = getStar();
                for(int i  = 0;i<star.getPoints().size();i+=2) {
                    double tempX = star.getPoints().get(i) ;
                    double tempY = star.getPoints().get(i+1);
                    star.getPoints().set(i,x+tempX);
                    star.getPoints().set(i+1,y+tempY);
                }
                shape = star; break;
            case CROSS:
                Polygon cross = getCross();
                for(int i  = 0;i<cross.getPoints().size();i+=2) {
                    double tempX = cross.getPoints().get(i) ;
                    double tempY = cross.getPoints().get(i+1);
                    cross.getPoints().set(i,x+tempX);
                    cross.getPoints().set(i+1,y+tempY);
                }
                shape = cross; break;

            default: shape = null;
        }

    }
}
