package javafxTest;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import model.Game;
import model.Position;
import model.ShapeType;
import model.Station;

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

    double x,y,middleX,middleY,x2,y2;
    int config;
    Polyline pline = new Polyline(0,0,0,0,0,0) ;
    boolean stationPressed = false;
    Station station;
    Game game ;
    GameView gameView;





    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        //group.startFullDrag();

        // River :
        Polyline river = new Polyline(
                150,600,
                150,450,
                500,450,
                500,330,
                530,300,
                700,300,
                800,370,
                900,300,
                1000,300,
                1000,420,
                1150,420,
                1150,300,
                1200,300
        );

        Color colorRiver = new Color((double)200/255,(double)230/255,(double)250/255,0.5);
        river.setStroke(colorRiver);
        river.setStrokeLineJoin(StrokeLineJoin.ROUND);
        river.setStrokeWidth(23);

        Polyline borderRiver = new Polyline(
                150,600,
                150,450,
                500,450,
                500,330,
                530,300,
                700,300,
                800,370,
                900,300,
                1000,300,
                1000,420,
                1150,420,
                1150,300,
                1200,300
        );

        Color colorBorder = new Color((double)100/255,(double)180/255,(double)220/255,0.5);
        borderRiver.setStroke(colorBorder);
        borderRiver.setStrokeLineJoin(StrokeLineJoin.ROUND);
        borderRiver.setStrokeWidth(31);







        Polyline poly = new Polyline(200,200,200,300,300,400);

        poly.setStroke(Color.DARKRED);
        poly.setStrokeWidth(10);


        group.getChildren().add(poly);

        group.getChildren().add(borderRiver);
        group.getChildren().add(river);

        group.getChildren().add(pline);


        /*Rectangle rect = new Rectangle (0, 0, 50, 25);
        rect.setFill(Color.VIOLET);

        group.getChildren().add(rect);*/
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



        /** SHAPES TEST ** */

        Polygon triangle = getTriangle();
        setPosition(triangle,500,100);
        group.getChildren().add(triangle);

        Polygon star = getStar();
        setPosition(star,700,200);
        group.getChildren().add(star);

        Rectangle sq = getSquare();
        setPosition(sq,800,300);
        group.getChildren().add(sq);

        Polygon cross = getCross();
        setPosition(cross,900,400);
        group.getChildren().add(cross);




        Group train = new Group();
        Rectangle r = new Rectangle(0,0,50,25); r.setStroke(Color.BLUE);

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

        group.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(stationPressed) {
                    x2 = event.getX(); y2 = event.getY();
                    displayDrawing();
                }
            }
        });

        group.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
             //   System.err.println("Clicked");
                x=event.getX();
                y=event.getY();
            }
        });

        group.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stationPressed = false;
                group.getChildren().remove(pline);
            }
        });

        /* MODEL VIEW TEST*/

        gameView = new GameView(group);
        game = new Game(gameView);
        Station s1 = new Station(ShapeType.CIRCLE,new Position(200,200));
        Station s2 = new Station(ShapeType.CIRCLE,new Position(400,400));
        Station s3 = new Station(ShapeType.CIRCLE,new Position(600,700));
        Station s4 = new Station(ShapeType.CIRCLE,new Position(800,50));


        game.viewTest(s1);
        game.viewTest(s2);
        game.viewTest(s3);
        game.viewTest(s4);
        addStationEvent(gameView.get(s1),s1);
        addStationEvent(gameView.get(s2),s2);
        addStationEvent(gameView.get(s3),s3);
        addStationEvent(gameView.get(s4),s4);

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

    private void addStationEvent(fxStation viewSt ,Station modelSt) {



        viewSt.shape.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewSt.shape.startFullDrag();
            }
        });

        viewSt.shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stationPressed = true;
                System.err.println("Station Pressed");
                station = modelSt;
            }
        });

        viewSt.shape.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if(stationPressed) {
                    System.err.println("Station LINKED");
                    x2 = modelSt.getPosition().getX();
                    y2 = modelSt.getPosition().getY();
                    displayDrawing();

                }
            }
        });

        /*
        viewSt.shape.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                System.err.println("Station drag Entered");
            }
        });*/

        viewSt.shape.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                System.err.println("Drag released link : "+stationPressed);
                if(stationPressed) {
                    fxLink link = new fxLink(x,y,middleX,middleY,x2,y2);
                    System.err.println("intersects : "+ gameView.intersects(link));
   //                 if(!gameView.intersects(link)) {
                        group.getChildren().add(0, link);
                        modelSt.addLink(station);
                        game.computeAllDistances();
                        gameView.add(link);
                        System.err.println(station + "\n" + modelSt);
                        fxEndLine endLine = new fxEndLine(modelSt,middleX,middleY);
                        group.getChildren().add(0,endLine);
     //               }
                }
            }
        });
    }

    public void displayDrawing () {
        if (x == x2)
            config = 0;
        if (y == y2)
            config = 1;
        if (is45degree(x, y, x2, y2))
            config = 2;

        verifiateConfig(x2, y2);

        if (config == 0) {
            if (y2 > y) {
                middleX = x;
                middleY = y2 - abs(x2 - x);
            } else {
                middleX = x;
                middleY = y2 + abs(x2 - x);
            }
        } else if (config == 1) {
            middleY = y;
            if (x2 > x)
                middleX = x2 - abs(y2 - y);
            else
                middleX = x2 + abs(y2 - y);
        } else {
            if (sup45degree(x, y, x2, y2)) {
                middleY = y2;
                if (x2 > x)
                    middleX = x + abs(y2 - y);
                else
                    middleX = x - abs(y2 - y);
            } else {
                middleX = x2;
                if (y2 > y)
                    middleY = y + abs(x2 - x);
                else
                    middleY = y - abs(x2 - x);
            }
        }
        group.getChildren().remove(pline);
        pline.setStroke(Color.BLUE);
        pline.setStrokeWidth(10);
        pline.getPoints().setAll(x, y, middleX, middleY, x2, y2);
        group.getChildren().add(0, pline);
        if(gameView.intersects(pline))
            pline.setStroke(Color.TRANSPARENT);
    }


}