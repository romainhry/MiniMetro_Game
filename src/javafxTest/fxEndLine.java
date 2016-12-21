package javafxTest;

import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import model.Station;

import static javafxTest.defaultShapes.getEndLine;

/**
 * Created by KadirF on 18/12/2016.
 */
public class fxEndLine extends Polyline {

    public fxEndLine() {
        super(15,-25,15,0,0,0,30,0);
        setStrokeWidth(10);
        setTranslateX(-15); setTranslateY(25);
    }

    public fxEndLine(Station modelSt,double middleX,double middleY) {
        super(0,0,0,25,-20,25,15,25);
        setStrokeWidth(10);
        double dx = modelSt.getPosition().getX();
        double dy = modelSt.getPosition().getY();

        int x = modelSt.getPosition().getX(), y=modelSt.getPosition().getY();

        if(middleY == y) {
            if(middleX > x) {
                setRotate(90);
                setTranslateX(dx-30);
                setTranslateY(dy-10);
            }
            else {
                setRotate(-90);
                setTranslateX(dx+30);
                setTranslateY(dy-10);
            }
        }
        else if(middleX == x) {
            if(middleY > y) {
                setRotate(180);
                setTranslateX(dx);
                setTranslateY(dy-45);
            }
            else {
                setRotate(0);
                setTranslateX(dx);
                setTranslateY(dy+20);
            }
        }
        else {
            if(middleX > x && middleY > y) {
                setRotate(135);
                setTranslateX(dx-25);
                setTranslateY(dy-34);
            }
            if(middleX > x && middleY < y) {
                setRotate(45);
                setTranslateX(dx-25);
                setTranslateY(dy+12);
            }
            if(middleX < x && middleY > y) {
                setRotate(-135);
                setTranslateX(dx+25);
                setTranslateY(dy-34);
            }
            if(middleX < x && middleY < y) {
                setRotate(-45);
                setTranslateX(dx+25);
                setTranslateY(dy+12);
            }
        }

    }
}
