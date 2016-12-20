package javafxTest;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

import static javafxTest.defaultShapes.*;


public class Controller
        implements Initializable {


    @FXML
    private Group group;

    @FXML
    private Pane pane;

    double x,y;
    int config;
    Polyline pline = new Polyline(0,0,0,0,0,0) ;




    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Polyline poly = new Polyline(200,200,200,300,300,400);
        poly.setStroke(Color.AQUA);
        poly.setStrokeWidth(10);


        poly.getPoints() ;


        group.getChildren().add(poly);


        group.getChildren().add(pline);


        Rectangle rect = new Rectangle (0, 0, 50, 25);
        rect.setFill(Color.VIOLET);

        group.getChildren().add(rect);
/*
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), rect);
        tt.setByX(100f);tt.setByY(100f);
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        rect.setRotate(45);
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.err.println("FINISHED ");
                rect.setRotate(0);
            }
        });*/

        /** SHAPES ** */
        Rectangle squar = getSquare();
        setPosition(squar,12.5,5);

//        group.getChildren().add(square);

        Circle circle = getCircle();
        setPosition(circle,300,200);
        //group.getChildren().add(circle);

        Polygon triangle = getTriangle();
        setPosition(triangle,400,400);
        //group.getChildren().add(triangle);

        Polyline p = getEndLine();
        setPosition(p,500,500);
       // group.getChildren().add(p);

        Group train = new Group();
        Rectangle r = new Rectangle(0,0,50,25); r.setStroke(Color.BLUE);

        ;
        Rectangle square = new Rectangle(1,1,10,10); square.setFill(Color.RED);
        Rectangle square2 = new Rectangle(1,13.5,10,10); square2.setFill(Color.RED);
        Circle c1 = new Circle(5); c1.setCenterX(19.75); c1.setCenterY(6.25); c1.setFill(Color.RED);
        Polygon p1 = new Polygon(0.0, 0.0, 5.0, -8.66, 10.0, 0.0 ); p1.setFill(Color.RED);
        p1.setTranslateX(14); p1 .setTranslateY(24);
        p1.setRotate(90);


        train.getChildren().addAll(r,square2,square,c1,p1);
        train.setTranslateX(175); train.setTranslateY(200);

        train.setRotate(90);
        group.getChildren().add(train);

        TranslateTransition trainMoving = new TranslateTransition(Duration.millis(2000),train);
        trainMoving.setByX(0);trainMoving.setByY(95f);
        trainMoving.setCycleCount(1);
        trainMoving.setAutoReverse(false);
        trainMoving.play();

        trainMoving.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TranslateTransition trainMoving2 = new TranslateTransition(Duration.millis(2000),train);
                train.setRotate(45);
                trainMoving2.setByX(100);trainMoving2.setByY(100);
                trainMoving2.setCycleCount(1);
                trainMoving2.setAutoReverse(false);
                trainMoving2.play();
            }
        });




        /*   */


        group.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x2=event.getX(),y2=event.getY();
                double middleX,middleY;

                System.err.println(config);


                if(x==x2)
                    config = 0 ;
                if(y==y2)
                    config = 1 ;
                if(is45degree(x,y,x2,y2))
                    config=2;

                verifiateConfig(x2,y2);



                if(config == 0) {
                    if(y2 > y) {
                        middleX = x ;
                        middleY = y2 - abs(x2 - x) ;
                    }
                    else {
                        middleX = x ;
                        middleY = y2 + abs(x2 - x) ;
                    }
                }
                else if( config == 1 ) {
                    middleY = y;
                    if(x2 > x)
                        middleX =  x2 - abs(y2 - y) ;
                    else
                        middleX =  x2 + abs(y2 - y) ;
                }
                else  {
                    if(sup45degree(x,y,x2,y2)) {
                        middleY =  y2;
                        if(x2>x)
                            middleX = x + abs(y2 - y)  ;
                        else
                            middleX = x  - abs(y2 - y) ;
                    }
                    else {
                        middleX = x2;
                        if(y2>y)
                            middleY = y + abs(x2 - x) ;
                        else
                            middleY = y - abs(x2 - x) ;
                    }
                }

                group.getChildren().remove(pline);
                pline.setStroke(Color.BLUE);
                pline.setStrokeWidth(10);
                pline.getPoints().setAll(x,y,middleX,middleY,x2,y2);


                group.getChildren().add(0,pline);


            }
        });


        group.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.err.println("Clicked");
                x=event.getX();
                y=event.getY();
            }
        });
    }



    private boolean is45degree(double x1, double y1, double x2, double y2) {
        return abs(x1-x2)== abs(y1-y2);
    }
    private  boolean sup45degree(double x1, double y1, double x2, double y2) {
        return abs(x1-x2) > abs(y2-y1);
    }

    private void verifiateConfig(double x2, double y2) {
        if(config == 0 && sup45degree(x,y,x2,y2) || config == 1 &&  abs(x-x2) < abs(y2-y) )
            config = 2;
    }


}