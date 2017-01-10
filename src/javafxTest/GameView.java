package javafxTest;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import model.Line;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafxTest.defaultShapes.getSquare;

/**
 * Created by KadirF on 20/12/2016.
 */
public class GameView {

    HashMap<Station,fxStation> stations;
    HashMap<Train,fxTrain> trains;
    HashMap<Client,fxClient> clients;
    HashMap<model.Line,ArrayList<Shape>> lineLinks;
    HashMap<model.Line,Shape[]> lineEnds;
    List<Shape> links = new ArrayList<>();
    Shape river;
    private Group group;
    private Controller controller;
    private fxClock clock;
    private fxInformations info;
    private boolean anim =false;
    private Circle point;
    private ImageView imageClient;
    private Text nbClient;
    private boolean pause=false;
    private boolean gift=true;


    public GameView(Group g,Controller c) {
        stations = new HashMap<>();
        trains = new HashMap<>();
        lineLinks = new HashMap<>();
        lineEnds = new HashMap<>();
        clients = new HashMap<>();
        group = g;
        controller = c;
        clock = new fxClock(1150,35,16);


        info = c.getInfo();
        info.setVisible(false);

        group.getChildren().add(clock);
        group.getChildren().add(info);

        imageClient = new ImageView(new Image("file:src/img/man.png",40,40,false,false));
        imageClient.setX(980);
        imageClient.setY(13);
        nbClient = new Text(960, 43,"0");
        nbClient.setFill(Color.CHOCOLATE);
        nbClient.setFont(Font.font(null, FontWeight.BOLD,25));

        group.getChildren().add(nbClient);
        group.getChildren().add(imageClient);


        point = new Circle(600,575,4);

        point.setStroke(Color.GRAY);

        point.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!anim) {
                    seeInfo();
                    anim = true;
                }
            }
        });

        group.getChildren().add(point);
    }
    public void startIncreaseArc(Station st){
        Arc arcTimer=stations.get(st).arcTimer;
        arcTimer.setStroke(Color.CORAL);

        stations.get(st).arcTimeline.stop();
        stations.get(st).arcTimeline.getKeyFrames().clear();

        KeyValue kv = new KeyValue(arcTimer.lengthProperty(),360);
        KeyFrame kf = new KeyFrame(Duration.millis(50000), kv);

        stations.get(st).arcTimeline.getKeyFrames().add(kf);
        stations.get(st).arcTimeline.play();

        stations.get(st).arcTimeline.setOnFinished(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("End game");
            }
        });
    }
    public void startDecreaseArc(Station st){
        Arc arcTimer=stations.get(st).arcTimer;
        arcTimer.setStroke(Color.GOLD);

        stations.get(st).arcTimeline.stop();
        stations.get(st).arcTimeline.getKeyFrames().clear();

        KeyValue kv = new KeyValue(arcTimer.lengthProperty(),0);
        KeyFrame kf = new KeyFrame(Duration.millis(5000), kv);
        stations.get(st).arcTimeline.getKeyFrames().add(kf);


        stations.get(st).arcTimeline.playFromStart();
    }
    public void updateClock (int hour, String dayName) {
            clock.moveNeedle(hour);
            clock.setDay(dayName);
    }

    public void setGift(int gift1, int gift2) {
        Platform.runLater(new Runnable() {
            public void run() {
                Stage stage = new Stage();
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nouvelle semaine");
                    alert.setHeaderText("Bienvenue dans une nouvelle semaine ! Vous avez reçu une nouvelle locomotive.")  ;
                    alert.setContentText("Comment voulez-vous depenser le reste de votre budget ?");

                    alert.setGraphic(new ImageView(new Image("file:src/img/train.png",40,40,false,false)));

                    ButtonType buttonTypeOne = new ButtonType("Nouvelle ligne");
                    ButtonType buttonTypeTwo = new ButtonType("Nouveau wagon");
                    ButtonType buttonTypeThree = new ButtonType("Nouveau train");
                    ButtonType buttonTypeFour = new ButtonType("Nouveau tunnel");

                    ArrayList<ButtonType> list = new ArrayList<ButtonType>();
                    list.add(buttonTypeOne);
                    list.add(buttonTypeTwo);
                    list.add(buttonTypeThree);
                    list.add(buttonTypeFour);


                    alert.getButtonTypes().setAll(list.get(gift1),list.get(gift2));

                    Game.getInventory().addTrain();
                    updateTrainNb(Game.getInventory().getTrain());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne) {
                        Game.getInventory().addLine();
                        Controller.game.addGiftColor();
                        updateLineNb(Game.getInventory().getLine());
                    } else if (result.get() == buttonTypeTwo) {
                        Game.getInventory().addWagon();
                        updateWagonNb(Game.getInventory().getWagon());
                    } else if (result.get() == buttonTypeThree) {
                        Game.getInventory().addTrain();
                        updateTrainNb(Game.getInventory().getTrain());
                    } else if (result.get() == buttonTypeFour) {
                        Game.getInventory().addTunnelNb(1);
                        updateTunnelNb(Game.getInventory().getTunnelNb());
                    }

                    Game.resumeGame();
                    seeInfo();
                    anim = true;
                    System.out.println(!Game.getPause());

                } catch (Exception e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        });
    }

    public void updateTunnelNb (int tunnel) {
        info.setNbTunnel(tunnel);
    }

    public void updateTrainNb (int train) {
        info.setNbTrain(train);
    }

    public void updateWagonNb (int wagon) {
        info.setNbWagon(wagon);
    }

    public void updateLineNb (int rail) {
        info.setNbLine(rail);
    }

    public void addRiver(Shape r)
    {
        river = r;
    }

    public void seeInfo()
    {
        info.setVisible(true);
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(1000), info);
        translateTransition.setFromY(0);
        translateTransition.setToY(-70);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(1000), point);
        translateTransition2.setFromY(0);
        translateTransition2.setToY(-70);
        translateTransition2.setCycleCount(1);
        translateTransition2.setAutoReverse(true);
        translateTransition2.play();

        ParallelTransition para = new ParallelTransition();
        para.getChildren().addAll(
                translateTransition2,
                translateTransition
        );
        para.setCycleCount(1);
        para.play();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                hideInfo();
            }
        },
        5000);
    }

    public void hideInfo()
    {
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(1000), info);
        translateTransition.setFromY(-70);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        translateTransition.play();

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(1000), point);
        translateTransition2.setFromY(-70);
        translateTransition2.setToY(0);
        translateTransition2.setCycleCount(1);
        translateTransition2.setAutoReverse(true);
        translateTransition2.play();

        ParallelTransition para = new ParallelTransition();
        para.getChildren().addAll(
                translateTransition2,
                translateTransition
        );
        para.setCycleCount(1);
        para.play();
        para.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                info.setVisible(false);
                anim=false;
            }
        });
    }

    public void updateNbClient() {
        int newI = Integer.parseInt(nbClient.getText())+1;
        if(newI>=10)
        {
            nbClient.setX(948);
        }
        if(newI>=100)
        {
            nbClient.setX(936);
        }
        if(newI>=1000)
        {
            nbClient.setX(924);
        }
        nbClient.setText(Integer.toString(newI));
    }

    public Shape getTunnelShape(Shape line) {
        return Shape.intersect(river,line);
    }

    public void createLine(Line l,Shape end1, Shape end2) {
        ArrayList<Shape> list = new ArrayList<>();
        lineLinks.put(l,list);

        Shape[] ends = new Shape[2];
        ends[0] = end2; ends[1] = end1;
        lineEnds.put(l,ends);
    }

    public void setLineEnd (Line l, Shape end,boolean inFirst) {
        Shape [] ends  = lineEnds.get(l);
        if(inFirst)
            ends[0] = end;
        else
            ends[1] = end;
    }

    public void removeEnds(Line l) {
        Shape [] ends = lineEnds.get(l);
        System.err.println("REMOVING ENDS "+group.getChildren().remove(ends[0])+" " +group.getChildren().remove(ends[1]));
    }

    public Shape getLineLink(Line l,boolean inFirst) {
        if(inFirst)
            return lineLinks.get(l).get(0);
        else
            return lineLinks.get(l).get(lineLinks.get(l).size()-1);
    }

    public Shape getNextLineLink(Line l, Shape currentLink) {
        ArrayList<Shape> list = lineLinks.get(l);
        System.err.println("Index of current Link " + list.indexOf(currentLink));
        if(list.indexOf(currentLink)==0)
            return list.get(1);
        else
            return list.get(list.size()-2);
    }
    public Station getNextStation(Line l, Shape nextLink) {
        ArrayList<Shape> list = lineLinks.get(l);
        boolean loop = l.isLoop();
        System.err.println("IS LOOP "+loop+"\nIndex of next Link : "+list.indexOf(nextLink));
        if(list.indexOf(nextLink)==0)
            return l.getStationList().get(1);
        else
            return l.getStationList().get(l.getStationList().size()-2);
    }

    public void addLineLink(Line l,Shape link,boolean inFirst) {
        ArrayList<Shape> list = lineLinks.get(l);
        if(inFirst) {
            list.add(0, link);
            System.err.println("ADD IN FIRST");
        }
        else {
            list.add(link);
            System.err.println("ADD IN LAST");
        }
        links.add(link);
        System.err.println("Index of created LINK : "+list.indexOf(link));

    }

    public void removeLineLink(Line l, boolean inFirst) {
        Shape s;
        if(inFirst) {
            s = lineLinks.get(l).remove(0);
            System.err.println("REMOVING IN FIRST");
        }
        else {
            s = lineLinks.get(l).remove(lineLinks.get(l).size() - 1);
            System.err.println("REMOVING IN LAST");
        }

        group.getChildren().remove(s);
        links.remove(s);

        if(lineLinks.get(l).size()==0)
            lineLinks.remove(l);
    }

    public void removeLineLink(Line l, Shape link) {
        lineLinks.get(l).remove(link);
        group.getChildren().remove(link);
        links.remove(link);
        if(lineLinks.get(l).size()==0)
            lineLinks.remove(l);
    }

    public void addNode(Node n) {
        group.getChildren().add(n);
        System.err.println("ADDED NODE");
    }

    public void put(Station s) {
        stations.put(s,new fxStation(s));
        group.getChildren().add(stations.get(s).shape);
        group.getChildren().add(stations.get(s).arcTimer);
        controller.addStationEvent(stations.get(s).shape,s);
    }

    public void put(Train t) {
        trains.put(t,new fxTrain(t));
        group.getChildren().add(1,trains.get(t));
        controller.addTrainEvent(trains.get(t).r,t);

    }

    public void put(Client c) {
        clients.put(c,new fxClient(c));
        group.getChildren().add(clients.get(c).shape);
    }

    public fxTrain get(Train t) {
        return trains.get(t);
    }

    public fxStation get(Station s) {
        return stations.get(s);
    }

    public fxClient get(Client c) { return clients.get(c);}

    public  void remove(Client c) {
        fxClient fxc = get(c);
        group.getChildren().remove(fxc.shape);
        clients.remove(c);

        Station st = c.getStation();
        for(int i =0; i< st.getClientList().size(); ++i) {
            fxClient fxCl = get(st.getClientList().get(i));
            fxCl.updatePos(st,i+1);
        }
    }

    public void addClientToTrain(Train tr, Client client) {
        fxTrain fxTr = get(tr);
        Shape s = fxTr.addClient(client);
        fxClient fxclient = new fxClient(s);
        clients.put(client,fxclient);
    }

    public void removeClientFromTrain(Train tr,Client client) {
        fxTrain fxTr = get(tr);
        fxClient fxclient = get(client);
        fxTr.removeClient(fxclient.shape);
    }

    public void removeTrain(Train tr) {
        fxTrain fxtr = get(tr);
        group.getChildren().remove(fxtr);
        trains.remove(fxtr);
        fxtr = null;
    }


    public boolean intersects (Shape f) {
        for(Shape l : links) {
            Shape intersect = Shape.intersect(f, l);
            if(intersect.getBoundsInLocal().getWidth() != -1) {
                System.err.println("INTERSECTS !! ");
                return true;
            }
        }
        return false;
    }

    public boolean intersectRiver (Shape f) {
        Shape intersect = Shape.intersect(f, river);
        if(intersect.getBoundsInLocal().getWidth() != -1) {
            System.err.println("INTERSECTS RIVER !! ");
            return true;
        }
        return false;
    }

    public boolean intersectRiver (Position p) {
        Polygon square = getSquare();
        for(int i  = 0;i<square.getPoints().size();i+=2) {
            double tempX = square.getPoints().get(i) ;
            double tempY = square.getPoints().get(i+1);
            square.getPoints().set(i,p.getX()+tempX);
            square.getPoints().set(i+1,p.getY()+tempY);
        }
        Shape intersect = Shape.intersect(square, river);
        if(intersect.getBoundsInLocal().getWidth() != -1) {
            System.err.println("INTERSECTS RIVER !! ");
            return true;
        }
        return false;
    }

    public void move(Train train) {
        fxTrain fxTrain = get(train);
        fxTrain.move(train.getLine().getPath().get(train.getNextPointIndex()));
    }
}
