package javafxTest;

import javafx.scene.Group;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.Position;

/**
 * Created by Anthony on 06/01/2017.
 */
public class fxClock extends Group {

    private Line clockNeedle;
    private Circle clockBorder;
    private Point2D origin;
    private double radius;

    public fxClock(double posX, double posY, double radius){
        super();

        origin=new Point2D(posX,posY);
        this.radius=radius;

        clockBorder = new Circle(origin.getX(),origin.getY(),radius+10);
        clockBorder.setStrokeWidth(2);
        clockBorder.setStroke(Color.web("#42322f",1));
        clockBorder.setFill(null);

        clockNeedle = new Line(origin.getX(),origin.getY(),origin.getX(),origin.getY());
        clockNeedle.setStrokeWidth(2);
        clockNeedle.setStroke(Color.web("#42322f",1));
        moveNeedle(0);

        getChildren().add(clockBorder);
        getChildren().add(clockNeedle);
    }

    public Point2D calcEndPoint(Point2D origin, double radius, double angle){
        double xEnd= origin.getX()+(radius*(Math.cos(Math.toRadians(angle))));
        double yEnd= origin.getY()+(radius*(Math.sin(Math.toRadians(angle))));
        Point2D end=new Point2D(xEnd, yEnd);
        return end;
    }

    public void moveNeedle(int posInSecond){
        int angle=posInSecond*6;
        Point2D posEndPoint=calcEndPoint(origin,radius,angle);
        clockNeedle.setEndX(posEndPoint.getX());
        clockNeedle.setEndY(posEndPoint.getY());
    }
}
