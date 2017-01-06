package javafxTest;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

import static javafxTest.defaultShapes.*;


public class Controller implements Initializable {

    @FXML
    private Group group;

    @FXML
    private Pane pane;

    double x,y,middleX,middleY,x2,y2;
    int config;
    Polyline drawing = new Polyline(0,0,0,0,0,0) ;

    boolean stationPressed = false, TPressed = false , canRemove = false, isDrawing , canConstruct = true;
    Station currentStation;
    Shape currentT = null, currentLink ;
    model.Line currentLine;


    Game game ;
    public static GameView gameView;

    private fxClock clock;

    static Group group2;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        group2 = group;
        group.getChildren().add(drawing);


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


        group.getChildren().add(borderRiver);
        group.getChildren().add(river);



        group.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(stationPressed  && canConstruct) {
                    if(!isDrawing) {
                        x2 = event.getX();
                        y2 = event.getY();
                    }
                    displayDrawing();
                }
            }
        });
        group.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        group.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stationPressed = false;
                TPressed = false;
                group.getChildren().remove(drawing);
                currentT = null;
                currentLine = null;
                canRemove = true;
                isDrawing = false;
                canConstruct = true;
            }
        });

        /* MODEL VIEW TEST*/

        gameView = new GameView(group,this);
        game = new Game(gameView);

        Station s1 = new Station(ShapeType.CIRCLE,new Position(200,200));
        Station s2 = new Station(ShapeType.STAR,new Position(400,400));
        Station s3 = new Station(ShapeType.SQUARE,new Position(150,300));
        Station s4 = new Station(ShapeType.TRIANGLE,new Position(300,50));
        Station s5 = new Station(ShapeType.CROSS,new Position(500,250));
        Station s6 = new Station(ShapeType.CIRCLE,new Position(400,150));

        game.addToView(s1);
        game.addToView(s2);
        game.addToView(s3);
        game.addToView(s4);
        game.addToView(s5);
        game.addToView(s6);

        gameView.addRiver(borderRiver);



        game.start();


    }



    private boolean is45degree(double x1, double y1, double x2, double y2) {
        return abs(x1-x2)== abs(y1-y2);
    }
    private boolean sup45degree(double x1, double y1, double x2, double y2) {
        return abs(x1-x2) > abs(y2-y1);
    }

    private void verifiateConfig(double x2, double y2) {
        if(config == 0 && sup45degree(x,y,x2,y2) || config == 1 &&  abs(x-x2) < abs(y2-y) )
            config = 2;
    }

    public void addTEvent (Shape shape, Station modelSt, model.Line modelLine, Shape link) {
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






    public void addStationEvent(Shape shape ,Station modelSt) {
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
/*
        shape.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if(stationPressed) {
                    x2 = modelSt.getPosition().getX();
                    y2 = modelSt.getPosition().getY();
                    displayDrawing();
                }
            }
        }); */



        shape.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                if(stationPressed) {



                    /* Removes the station from the selected line */
                    if(modelSt == currentStation && TPressed && canRemove) {

                        if(!currentLine.getStationList().contains(modelSt))
                            return;

                        canConstruct = false;

                        group.getChildren().remove(currentT);


                        Position middle ;
                        /* The station which will become a end of it's line */
                        Station nextStation;

                        /* Delete line*/
                        if(currentLine.getStationList().size()==2) {
                            gameView.removeEnds(currentLine);
                            gameView.removeLineLink(currentLine,true);

                            ArrayList<Client> toDeposit = new ArrayList<Client>();

                            Station a = currentLine.getStationList().get(0);
                            Station b = currentLine.getStationList().get(1);

                            for(Train tr : currentLine.getTrainList()) {
                                gameView.removeTrain(tr);
                                toDeposit.addAll(tr.getClientList());
                                tr.setLine(null);
                            }
                            a.removeLink(b);
                            currentLine.removeStation(a);
                            currentLine.removeStation(b);
                            game.addColor(currentLine.getColor());

                            for(Client cl : toDeposit) {
                                if(cl.getType() != a.getType()) {
                                    cl.setStation(a);
                                    a.addClient(cl);
                                    game.addToView(cl);
                                }
                            }

                            return;
                        }
                        boolean inFirst = gameView.lineLinks.get(currentLine).indexOf(currentLink) == 0;

                        /* Removing the link */
                        Shape nextLink = gameView.getNextLineLink(currentLine,currentLink);
                        if(gameView.intersectRiver(currentLink))
                        {
                            game.getInventory().addTunnelNb(1);
                        }
                        int currentLinkIndex = gameView.lineLinks.get(currentLine).indexOf(currentLink);
                        gameView.removeLineLink(currentLine,currentLink);
                        nextStation = gameView.getNextStation(currentLine,nextLink);

                        if(currentLinkIndex == 0 )
                            middle = currentLine.getPath().get(3);
                        else
                            middle = currentLine.getPath().get(currentLine.getPath().size()-4);

                        middleX = middle.getX();
                        middleY = middle.getY();



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

                        /* FULL DRAG*/
                        currentStation = nextStation;
                        currentT = movedEndLine;
                        currentLink = nextLink;
                        return;
                    }
                    /* Avoids self Linking*/
                    else if(modelSt == currentStation) {
                        return;
                    }

                    if(canConstruct == false)
                        return;

                    isDrawing = true;
                    x2 = modelSt.getPosition().getX();
                    y2 = modelSt.getPosition().getY();
                    displayDrawing();

                    /*cant link when loops*/
                    if(currentLine != null && currentLine.getStationList().size()!=0 && currentLine.isLoop()) {
                        return;
                    }
                    /* Avoids that the middle point of a links be inside the shape*/
                    if(shape.contains(middleX,middleY) ) {
                        middleX=(x2 + x)/2;
                        middleY=(y2 + y)/2;
                    }
                    Polyline tempLink = new Polyline(x,y,middleX,middleY,x2,y2);
                    tempLink.setStrokeWidth(10);


                    /* If the current link isn't intersecting other we can add it */
                    if(!gameView.intersects(tempLink)) {
                        //check river intersection
                        boolean riverCrossing = gameView.intersectRiver(tempLink);
                        if ( game.getInventory().getTunnelNb()>0 || !riverCrossing) {
                            if(riverCrossing)
                            {
                                game.getInventory().subTunnelNb(1);
                            }
                            /* Avoids linking 2 station already linked by the same line*/
                            if (TPressed && !currentLine.addAllowed(modelSt) || (currentLine != null && currentLine.getStationList().size() == 2 && currentLine.getStationList().contains(modelSt) && currentLine.getStationList().contains(currentStation))) {
                                return;
                            }

                            fxStation toSubstract = new fxStation(new Station(ShapeType.SQUARE, modelSt.getPosition()));
                            //      Shape link =  Shape.subtract(tempLink,shape);
                            Shape link = Shape.subtract(tempLink, toSubstract.shape);
                            toSubstract = new fxStation(new Station(ShapeType.SQUARE, currentStation.getPosition()));
                            //        link = Shape.subtract(link,gameView.get(currentStation).shape);
                            link = Shape.subtract(link, toSubstract.shape);

                            modelSt.addLink(currentStation);
                            game.computeAllDistances();

                            fxEndLine endLine = new fxEndLine(modelSt, middleX, middleY);
                            group.getChildren().add(1, endLine);

                            Train train = null;

                            /* this case we create a new Line */
                            if (TPressed == false) {
                                fxEndLine endLine2 = new fxEndLine(currentStation, middleX, middleY);
                                group.getChildren().add(1, endLine2);
                                Color color = game.getColor();
                                model.Line created = new model.Line(currentStation, modelSt, color, middleX, middleY);//TO DO LINE
                                addTEvent(endLine2, currentStation, created, link);
                                endLine.setStroke(color);
                                endLine2.setStroke(color);
                                link.setStroke(color);
                                link.setFill(color);
                                currentLine = created;
                                gameView.createLine(currentLine, endLine, endLine2);

                                train = new Train(0, created, true);
                                currentLine.addTrain(train);
                                gameView.put(train);


                            }
                            addTEvent(endLine, modelSt, currentLine, link);

                            group.getChildren().add(1, link);

                            int currentStationIndex = 0;
                            /* this case we add a station to the current line */
                            if (TPressed) {
                                link.setFill(currentLine.getColor());
                                link.setStroke(currentLine.getColor());
                                endLine.setStroke(currentLine.getColor());
                                //
                                currentStationIndex = currentLine.getStationList().indexOf(currentStation);

                                boolean wasLoop = currentLine.isLoop();

                                if (currentStationIndex == 0) {
                                    currentLine.addStation(0, modelSt, middleX, middleY);
                                } else {
                                    currentLine.addStation(modelSt, middleX, middleY);
                                }

                                /* line becomes a loop */
                                if (!wasLoop && currentLine.isLoop()) {
                                    Station toRemoveLink;
                                    if (currentLine.getTrainList().get(0).getDirection()) {
                                        toRemoveLink = currentLine.getStationList().get(currentLine.getStationList().size() - 2);
                                    } else {
                                        toRemoveLink = currentLine.getStationList().get(1);
                                    }
                                    modelSt.removeLink(toRemoveLink);
                                    game.computeAllDistances();
                                }
                            }

                            /* To remove the T shape of a line */
                            if (currentT != null) {
                                group.getChildren().remove(currentT);
                            }
                            System.err.println(currentLine);
                            gameView.addLineLink(currentLine, link, currentStationIndex == 0);

                            if (TPressed) {
                                boolean b = gameView.lineLinks.get(currentLine).indexOf(link) == 0;
                                gameView.setLineEnd(currentLine, endLine, b);
                            }

                            if (train != null)
                                train.move();

                            /* FULL DRAG*/
                            currentT = endLine;
                            currentStation = modelSt;
                            x = modelSt.getPosition().getX();
                            y = modelSt.getPosition().getY();
                            currentLink = link;
                            TPressed = true;
                            canRemove = false;

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


        shape.setOnMouseDragExited(event -> {
            isDrawing = false;
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
        /* If the current link is intersecting other line or river without tunnel available, we make it transparent */
        if ( gameView.intersects(drawing) || (game.getInventory().getTunnelNb()==0 && gameView.intersectRiver(drawing))) {
            drawing.setStroke(Color.TRANSPARENT);
        }
    }


}