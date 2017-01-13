package javafx;

import javafx.scene.Group;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Anthony on 06/01/2017.
 */
public class fxClock extends Group {

    private Line clockNeedle;



    private Circle clockBorder;
    private Point2D origin;
    private double radius;
    private Text dayName;
    private boolean day = false;
    private int nbDay;



    public fxClock(double posX, double posY, double radius){
        super();

        origin=new Point2D(posX,posY);
        this.radius=radius;

        clockBorder = new Circle(origin.getX(),origin.getY(),radius+5);
        clockBorder.setStrokeWidth(2);
        clockBorder.setStroke(Color.WHITESMOKE);
        clockBorder.setFill(Color.web("#42322f",1));

        clockNeedle = new Line(origin.getX(),origin.getY(),origin.getX(),origin.getY());
        clockNeedle.setStrokeWidth(2);
        clockNeedle.setStroke(Color.WHITESMOKE);


        moveNeedle(0);
        dayName = new Text(posX-80, posY+7,"LUN");
        dayName.setFill(Color.web("#42322f",1));
        dayName.setFont(Font.font(null, FontWeight.SEMI_BOLD,20));

        getChildren().add(clockBorder);
        getChildren().add(clockNeedle);
        getChildren().add(dayName);
    }

    public Point2D calcEndPoint(Point2D origin, double radius, double angle){
        double xEnd= origin.getX()+(radius*(Math.cos(Math.toRadians(angle-90))));
        double yEnd= origin.getY()+(radius*(Math.sin(Math.toRadians(angle-90))));
        Point2D end=new Point2D(xEnd, yEnd);
        return end;
    }

    public void moveNeedle(int pos){
        if(pos>60)pos-=60;
        //int angle=pos*6;
        int angle=pos*30;
        if(pos == 6 || pos == 18)
        {
            day = !day;
        }


        if(day)
        {
            clockBorder.setStroke(Color.web("#42322f",1));
            clockBorder.setFill(Color.WHITESMOKE);

            clockNeedle.setStrokeWidth(2);
            clockNeedle.setStroke(Color.web("#42322f",1));
        }
        else
        {
            clockBorder.setStroke(Color.TRANSPARENT);
            clockBorder.setFill(Color.web("#42322f",1));

            clockNeedle.setStrokeWidth(2);
            clockNeedle.setStroke(Color.WHITESMOKE);
        }
        Point2D posEndPoint=calcEndPoint(origin,radius,angle);
        clockNeedle.setEndX(posEndPoint.getX());
        clockNeedle.setEndY(posEndPoint.getY());
    }
    public void setDay(String dayName){
        if(dayName!=this.dayName.getText())
        {
            this.dayName.setText(dayName);
            ++nbDay;
        }

    }
    public int getNbDay() {
        return nbDay;
    }

    public Circle getClockBorder() {
        return clockBorder;
    }
}
