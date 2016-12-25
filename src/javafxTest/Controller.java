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
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

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
    Polyline drawing = new Polyline(0,0,0,0,0,0) ;

    boolean stationPressed = false, TPressed = false;
    Station currentStation;
    Shape currentT = null, currentLink ;
    model.Line currentLine;


    Game game ;
    GameView gameView;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        group.getChildren().add(drawing);

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
                group.getChildren().remove(drawing);
                currentT = null;
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

        fxTrain train1 = new fxTrain();
        group.getChildren().add(train1);
        train1.move(new Position(700,200));
        train1.move(new Position(200,0));


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

    private void addTEvent (Shape shape, Station modelSt, model.Line modelLine, Shape link) {
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
                currentLink = link;
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

                        System.err.println("Index removed  : " + currentLine.getStationList().indexOf(modelSt));

                        Position middle ;
                        /* The station which will become a end of it's line */
                        Station nextStation;

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
                        boolean inFirst = gameView.lineLinks.get(currentLine).indexOf(currentLink) == 0;

                        /* Removing the link */
                        Shape nextLink = gameView.getNextLineLink(currentLine,currentLink);
                        int currentLinkIndex = gameView.lineLinks.get(currentLine).indexOf(currentLink);
                        gameView.removeLineLink(currentLine,currentLink);
                        nextStation = gameView.getNextStation(currentLine,nextLink);

                        if(currentLinkIndex == 0 )
                            middle = currentLine.getPath().get(3);
                        else
                            middle = currentLine.getPath().get(currentLine.getPath().size()-4);

                        middleX = middle.getX();
                        middleY = middle.getY();

                        System.err.println("INDEX OF NEW T's Station : " + currentLine.getStationList().indexOf(nextStation));
                        System.err.println("toPut T : "+nextStation);


                        fxEndLine movedEndLine = new fxEndLine(nextStation,middleX,middleY);
                        movedEndLine.setStroke(currentLine.getColor());
                        group.getChildren().add(1,movedEndLine);
                        gameView.setLineEnd(currentLine,movedEndLine,inFirst);

                        addTEvent(movedEndLine,nextStation,currentLine,nextLink);

                        if(!currentLine.isLoop()) {
                            currentLine.removeStation(modelSt);
                        }
                        else {
                            currentLine.removeLoop(modelSt,currentLine.getStationList().indexOf(nextStation)==1);
                        }
                        modelSt.removeLink(nextStation);
                        return;
                    }
                    /* Avoids self Linking*/
                    else if(modelSt == currentStation)
                        return;

                    if(currentLine != null && currentLine.getStationList().size()!=0 && currentLine.isLoop()) {
                        return;
                    }
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

                        fxEndLine endLine = new fxEndLine(modelSt,middleX,middleY);
                        group.getChildren().add(1,endLine);

                        /* this case we create a new Line */
                        if(TPressed == false) {
                            fxEndLine endLine2 = new fxEndLine(currentStation,middleX,middleY);
                            group.getChildren().add(1,endLine2);
                            Color color = game.getColor();
                            model.Line created = new model.Line(currentStation,modelSt,color,middleX,middleY) ;//TO DO LINE
                            addTEvent(endLine2,currentStation,created,temp);
                            endLine.setStroke(color);
                            endLine2.setStroke(color);
                            temp.setStroke(color); temp.setFill(color);
                            currentLine = created;
                            gameView.createLine(currentLine,endLine,endLine2);
                        }
                        addTEvent(endLine,modelSt,currentLine,temp);
                        group.getChildren().add(temp);

                        int currentStationIndex = 0;
                        /* this case we add a station to the current line */
                        if(TPressed) {
                            temp.setFill(currentLine.getColor());
                            temp.setStroke(currentLine.getColor());
                            endLine.setStroke(currentLine.getColor());
                            //
                            currentStationIndex = currentLine.getStationList().indexOf(currentStation);
                            if(currentStationIndex == 0 ) {
                                currentLine.addStation(0,modelSt,middleX,middleY);
                            }
                            else {
                                currentLine.addStation(modelSt,middleX,middleY);
                            }
                        }

                        /* To remove the T shape of a line */
                        if(currentT != null) {
                            group.getChildren().remove(currentT);
                        }
                        System.err.println(currentLine);
                        gameView.addLineLink(currentLine,temp,currentStationIndex==0);

                        if(TPressed) {
                            boolean b = gameView.lineLinks.get(currentLine).indexOf(temp) == 0;
                            gameView.setLineEnd(currentLine,endLine,b);
                        }

                    }
                }
            }
        });

        shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
        group.getChildren().remove(drawing);
        drawing.setStroke(Color.PAPAYAWHIP);
        drawing.setStrokeWidth(10);
        drawing.getPoints().setAll(x, y, middleX, middleY, x2, y2);
        group.getChildren().add(1, drawing);
        /* If the current link is intersecting we make it transparent */
        if(gameView.intersects(drawing))
            drawing.setStroke(Color.TRANSPARENT);
    }


}