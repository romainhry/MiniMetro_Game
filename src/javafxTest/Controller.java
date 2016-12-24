package javafxTest;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import model.*;

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
    int config,endLineIndex = 2 ;
    Polyline pline = new Polyline(0,0,0,0,0,0) ;

    boolean stationPressed = false, TPressed = false;
    Station currentStation;
    Shape currentT = null, currentLink ;
    model.Line currentLine;


    Game game ;
    GameView gameView;

    /* todo
    *
    *
    * Bug avec currentLine si on change de ligne ?
    * Bug avec les removes dans gameView
    *
    * */





    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        //group.startFullDrag();

        /*
        Polyline poly = new Polyline(200,200,200,300,300,400);
        poly.setStroke(Color.AQUA);
        poly.setStrokeWidth(10);


        poly.getPoints() ;


        group.getChildren().add(poly);
        */

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



        /** SHAPES **/
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
                //System.err.println("Clicked");
                //x=event.getX();
                //y=event.getY();
                /*
                System.err.println("\n\n\n");
                for(Node n : group.getChildren())
                    System.err.println(n);
                */

                /*
                System.err.println("--------------------------------------------");
                for(Shape s : gameView.lineLinks.get(currentLine))
                    System.err.println(s);

                System.err.println("LINKS COUNT "+gameView.lineLinks.get(currentLine).size()+"\n");

                for(Shape s : gameView.links) {
                    System.err.println(s);
                }
                System.err.println("---------------------------------------------");
                */
                System.err.println(currentLine+"\n");


            }
        });

        group.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stationPressed = false;
                TPressed = false;
                group.getChildren().remove(pline);
                currentT = null;
                /*
                TPressed = false;
                currentStation = null;
                currentT = null;
                stationPressed = false;
                currentLink = null;
                currentLine = null;
                */
            }
        });

        /* MODEL VIEW TEST*/

        gameView = new GameView(group);
        game = new Game(gameView);

        Station s1 = new Station(ShapeType.CIRCLE,new Position(200,200));
        Station s2 = new Station(ShapeType.CIRCLE,new Position(400,400));
        Station s3 = new Station(ShapeType.SQUARE,new Position(150,300));
        Station s4 = new Station(ShapeType.TRIANGLE,new Position(300,50));
        Station s5 = new Station(ShapeType.CIRCLE,new Position(500,250));
        Station s6 = new Station(ShapeType.CIRCLE,new Position(400,150));

        game.viewTest(s1);
        game.viewTest(s2);
        game.viewTest(s3);
        game.viewTest(s4);
        game.viewTest(s5);
        game.viewTest(s6);

        addStationEvent(gameView.get(s1).shape,s1);
        addStationEvent(gameView.get(s2).shape,s2);
        addStationEvent(gameView.get(s3).shape,s3);
        addStationEvent(gameView.get(s4).shape,s4);
        addStationEvent(gameView.get(s5).shape,s5);
        addStationEvent(gameView.get(s6).shape,s6);


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

    private void addTEvent (Shape shape, Station modelSt, model.Line modelLine) {
        shape.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shape.startFullDrag();
            }
        });

        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Position pos = modelSt.getPosition();
                x = pos.getX(); y= pos.getY();
                shape.setStroke(Color.TRANSPARENT);
                stationPressed = true; TPressed = true ;
                currentStation = modelSt;
                currentT = shape;
                TPressed = true;
                currentLine =  modelLine;
            }
        });

        shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shape.setStroke(currentLine.getColor());
            }
        });
    }

    private void addStationEvent(Shape shape ,Station modelSt) {
        shape.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shape.startFullDrag();
            }
        });

        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Position pos = modelSt.getPosition();
                x = pos.getX(); y = pos.getY();
                stationPressed = true;
                currentStation = modelSt;
            }
        });

        shape.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if(stationPressed) {
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

        shape.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if(stationPressed) {
                    /* Removes the station from the selected line */
                    if(modelSt == currentStation && TPressed) {
                        group.getChildren().remove(currentT);

                        //group.getChildren().remove(currentLink);
                        System.err.println("IS NULL : " + currentLink);
                        System.err.println("Index on  : " + currentLine.getStationList().indexOf(modelSt));

                        Position middle ;
                        int index = currentLine.getStationList().indexOf(modelSt);
                        Station toPutT,previous ;

                        /* Delete line*/
                        if(currentLine.getStationList().size()==2) {
                            gameView.removeEnds(currentLine);
                            gameView.removeLineLink(currentLine,true);
                            Station a = currentLine.getStationList().get(0);
                            Station b = currentLine.getStationList().get(1);
                            a.removeLink(b);
                            currentLine.removeStation(a);
                            currentLine.removeStation(b);
                            game.addColor(currentLine.getColor());
                            return;
                        }
                        /* Find the position for the moved T*/
                        if(index == 0 ) {
                            toPutT = currentLine.getStationList().get(1);
                            previous = currentLine.getStationList().get(2);
                            middle = currentLine.getPath().get(currentLine.getPath().indexOf(toPutT.getPosition())+1) ;
                        }
                        else {
                            toPutT = currentLine.getStationList().get(index - 1);
                            previous = currentLine.getStationList().get(index - 2);
                            System.err.println("INDEX : "+(currentLine.getPath().indexOf(toPutT.getPosition())-1));
                            middle = currentLine.getPath().get(currentLine.getPath().indexOf(toPutT.getPosition())-1) ;
                        }
                        middleX = middle.getX();
                        middleY = middle.getY();
                        fxEndLine movedEndLine = new fxEndLine(toPutT,middleX,middleY);
                        movedEndLine.setStroke(currentLine.getColor());
                        group.getChildren().add(1,movedEndLine);

                        /* Removing the link */
                        System.err.println("INDEX OF NEW T's Station : " + currentLine.getStationList().indexOf(toPutT));
                        gameView.removeLineLink(currentLine,currentLine.getStationList().indexOf(modelSt)==0);
                        gameView.setLineEnd(currentLine,movedEndLine,currentLine.getStationList().indexOf(modelSt)==0);
                        addTEvent(movedEndLine,toPutT,currentLine);
                        currentLine.removeStation(modelSt);
                        modelSt.removeLink(toPutT);
                        return;
                    }
                    /* Avoids self Linking*/
                    else if(modelSt == currentStation)
                        return;

                    /* Avoids that the middle point of a links be inside the shape*/
                    if(shape.contains(middleX,middleY) ) {
                        middleX=(x2 + x)/2;
                        middleY=(y2 + y)/2;
                    }
                    Polyline link = new Polyline(x,y,middleX,middleY,x2,y2);
                    link.setStrokeWidth(10);
                    /* If the current link isn't intersecting other we can add it */
                    if(!gameView.intersects(link)) {

                        /* Avoids linking 2 station already linked by the same line*/

                        if( TPressed && !currentLine.addAllowed(modelSt) || ( currentLine!=null &&  currentLine.getStationList().size() == 2 && currentLine.getStationList().contains(modelSt) && currentLine.getStationList().contains(currentStation))) {
                            System.err.println("ALREADY linked ");
                            return;
                        }

                        Shape temp =  Shape.subtract(link,shape);
                        temp = Shape.subtract(temp,gameView.get(currentStation).shape);

                        modelSt.addLink(currentStation);

                        game.computeAllDistances();

                        // todo
                        //gameView.add(temp);

                        fxEndLine endLine = new fxEndLine(modelSt,middleX,middleY);
                        group.getChildren().add(1,endLine);
                      //  addTEvent(endLine,modelSt,temp,currentLine); // TO DO LINE

                        if(TPressed == false) {
                            fxEndLine endLine2 = new fxEndLine(currentStation,middleX,middleY);
                            group.getChildren().add(1,endLine2);
                            Color color = game.getColor();
                            model.Line created = new model.Line(currentStation,modelSt,color,middleX,middleY) ;//TO DO LINE
                            addTEvent(endLine2,currentStation,created);
                            endLine.setStroke(color);
                            endLine2.setStroke(color);
                            temp.setStroke(color); temp.setFill(color);
                            currentLine = created;
                            gameView.createLine(currentLine,endLine,endLine2);
                        }
                        addTEvent(endLine,modelSt,currentLine);
                        group.getChildren().add(temp);
                        if(TPressed) {
                            temp.setFill(currentLine.getColor());
                            temp.setStroke(currentLine.getColor());
                            endLine.setStroke(currentLine.getColor());
                            //
                            int index = currentLine.getStationList().indexOf(currentStation);
                            if(index == 0 ) {
                                currentLine.addStation(0,modelSt,middleX,middleY);
                            }
                            else {
                                currentLine.addStation(modelSt,middleX,middleY);
                            }
                            gameView.setLineEnd(currentLine,endLine,currentLine.getStationList().indexOf(modelSt)==0);
                        }

                        /* To remove the T shape of a line */
                        if(currentT != null) {
                            group.getChildren().remove(currentT);
                        }

                        System.err.println(currentLine);
                        gameView.addLineLink(currentLine,temp,currentLine.getStationList().indexOf(modelSt)==0);

                    }
                }
            }
        });

        shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /*
                TPressed = false;
                currentStation = null;
                currentT = null;
                stationPressed = false;
                currentLink = null;
                currentLine = null;*/
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
        pline.setStroke(Color.PAPAYAWHIP);
        pline.setStrokeWidth(10);
        pline.getPoints().setAll(x, y, middleX, middleY, x2, y2);
        group.getChildren().add(1, pline);
        /* If the current link is intersecting we make it transparent */
        if(gameView.intersects(pline))
            pline.setStroke(Color.TRANSPARENT);
    }


}