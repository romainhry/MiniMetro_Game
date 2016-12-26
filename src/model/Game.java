package model;

import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafxTest.GameView;

import java.util.*;

/**
 * Created by romainhry on 08/11/2016.
 */
public class Game {
    public GameView view;
    private static int trainSpeed ;
    protected static int vehicleCapacity ;
    private static int stationCapacity ;
    private static int timeSpeed ;
    private static int transportedClientNb;
    private int width = 1200;
    private int high = 600;
    private Timer day;
    private Inventory inventory;
    private List<Train> trainList;
    private List<Client> clientList;
    private List<Line> lineList;
    private List <Station> stationList ;
    private List <Color> linesColor ;

    private boolean clientReady,stationReady;

    public Game(GameView gameView) {
        view = gameView;
        trainList = new ArrayList<>();
        clientList = new ArrayList<>();
        lineList = new ArrayList<>();
        stationList = new ArrayList<>();
        inventory = new Inventory(3,3,0,3,0);
        linesColor  = new ArrayList<>();
        linesColor.add(Color.RED); linesColor.add(Color.BLUE); linesColor.add(Color.YELLOW);
    }

    public Color getColor() {
        Color c = linesColor.get(0);
        linesColor.remove(0);
        return c;
    }

    public void addColor(Color c) {
        linesColor.add(c);
    }

    public Station popRandomStation() {
    	return null;
    private void popRandomStation() {

        Thread threadStation = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((int) (15000 + Math.random() * 20000));  //min 15 s, max 35 s between 2 new station
                        Station st = new Station(
                                ShapeType.values()[(int) (Math.random() * 5)],
                                new Position((int) (Math.random() * width), (int) (Math.random() * high))
                        );
                        stationList.add(st);
                        Platform.runLater(() ->view.put(st));
                        System.out.println("new station");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        threadStation.start();
    }
  
    private void popRandomClient() {
        Thread threadClient = new Thread() {
            public void run() {
                while(true){
                    try {
                        Thread.sleep((int)(0+Math.random()*10000));  //min 0 s, max 10 s of delay between 2 new clients

                        Client clt = new Client(
                                stationList.get((int)(Math.random()*stationList.size())),
                                ShapeType.values()[(int)(Math.random()*5)]
                        );
                        clientList.add(clt);
                        Platform.runLater(() ->view.put(clt));                              //bug
                        System.out.println("new client");
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
            }
        };

        threadClient.start();


    }
    
    public void pop2RandomUpgrade() {
    	
    }
    
    public void pauseGame() {
    	
    }
    
    public void setGameSpeed(int speed) {
    	
    }
    
    public void addTransportedClient() {
    	
    }

    public void viewTest (Station s) {
        stationList.add(s);
        view.put(s);
    }

    public void viewTest (Train t) {
        trainList.add(t);
        view.put(t);
    }

    public void viewTest (Client c) {
        clientList.add(c);
        view.put(c);
    }

    public void computeAllDistances() {
        for( Station st : stationList) {
            Arrays.fill(st.getDistances(),-1);
            resetChecked();
            //st.computeDistances(st,0);
            st.computeDistances();
        }
    }

    public   void resetChecked() {
        for(Station s: stationList)
            s.setChecked(false);
    }

    public void start() {
        popRandomStation();
        popRandomClient();





    }

    
    
}
