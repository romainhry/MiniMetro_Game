package model;

import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafxTest.GameView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by romainhry on 08/11/2016.
 */
public class Game {
    public GameView view;
    private static int trainSpeed ;
    protected static int vehicleCapacity = 8 ;
    private static int stationCapacity ;
    private static int timeSpeed ;
    private static int transportedClientNb = 0;
    private int width = 1200;
    private int height = 600;
    private static int maxShapeWidth = 30;
    private static double distanceSpacing = 100;
    private Timer day;
    private static Inventory inventory;
    private List<Train> trainList;
    private List<Client> clientList;
    private List<Line> lineList;
    private List <Station> stationList ;
    private List <Color> linesColor ;
    private Clock clock;
    private static boolean pause =false;
    private static boolean gift = true;

    private boolean clientReady,stationReady;

    public Game(GameView gameView) {
        view = gameView;
        trainList = new ArrayList<>();
        clientList = new ArrayList<>();
        lineList = new ArrayList<>();
        stationList = new ArrayList<>();
        inventory = new Inventory(3,3,0,3,0);
        linesColor  = new ArrayList<>();
        linesColor.add(Color.RED); linesColor.add(Color.BLUE);linesColor.add(Color.ORANGE);
        clock = new Clock();
    }

    public Color getColor() {
        Color c = linesColor.get(0);
        linesColor.remove(0);
        return c;
    }

    public void addColor(Color c) {
        linesColor.add(c);
    }


    private void popRandomStation() {

        Thread threadStation = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Random random = new Random();
                        Thread.sleep( 15000 + random.nextInt(20000));  //min 15 s, max 35 s between 2 new station
                        Position pos ;
                        boolean again = false;
                        do {
                            pos = new Position(maxShapeWidth/2 + random.nextInt((int) (width - maxShapeWidth)), maxShapeWidth/2 + random.nextInt((int) (height - maxShapeWidth))) ;
                            again = (pos.getY()>530 && pos.getX()>460 && pos.getX()<740) || (pos.getY()<60 && pos.getX()>900); // check if pos is in clock or in informations
                        } while (!isSpaced(pos) || view.intersectRiver(pos) || again);

                        Station st = new Station(ShapeType.values()[random.nextInt(ShapeType.values().length)],pos);
                        stationList.add(st);
                        Platform.runLater(() -> addToView(st));
                        //System.out.println("new station");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        threadStation.start();
    }
  
    private void popRandomClient() {

        ArrayList <ShapeType> types = new ArrayList<>(Arrays.asList(ShapeType.values()));

        Thread threadClient = new Thread() {
            public void run() {
                while(true){
                    try {
                        Random random = new Random();
                        Thread.sleep(random.nextInt(10000));  //min 0 s, max 10 s of delay between 2 new clients

                        Station randomStation = stationList.get(random.nextInt(stationList.size()));
                        ShapeType randomType;
                        types.remove(randomStation.getType());
                        randomType = types.get(random.nextInt(types.size()));
                        Client clt = new Client(randomStation,randomType);
                        clientList.add(clt);
                        types.add(randomStation.getType());
                        Platform.runLater(() -> addToView(clt));                              //bug
                        //System.out.println("new client");
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


    private void timeGo() {

        Thread threadTime = new Thread() {
            public void run() {
                while(true){
                    if(!getPause()) {
                        try {
                            System.out.println("here");
                            clock.incrementeTime();
                            view.updateClock(clock.getTime(), clock.getDay());
                            if(clock.getDay()=="LUN" && !gift)
                            {
                                pop2RandomUpgrade();
                                gift=true;
                            }
                            else if (clock.getDay()!="LUN") {
                                gift=false;
                            }

                            sleep(100);
                        }
                        catch(Exception ex) {
                            System.out.println(ex);
                        }
                    }

                }
            }
        };
        threadTime.start();
    }
    
    
    public void pop2RandomUpgrade() {
        Random random = new Random();

        pauseGame();

        view.setGift(random.nextInt(4),random.nextInt(4));


    }
    
    public static void pauseGame() {
    	pause=true;
    }
    public static void resumeGame() {
        pause=false;
    }

    public static boolean getPause() {
        return pause;
    }

    
    public void setGameSpeed(int speed) {
    	
    }
    
    public static void addTransportedClient() {
    	++transportedClientNb;
    }

    public void addToView(Station s) {
        stationList.add(s);
        view.put(s);
    }

    public void addToView(Train t) {
        trainList.add(t);
        view.put(t);
    }

    public void addToView(Client c) {
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

    public static Inventory getInventory() {
        return inventory;
    }

    public   void resetChecked() {
        for(Station s: stationList)
            s.setChecked(false);
    }

    public void start() {
        popRandomStation();
        popRandomClient();
        timeGo();
    }

    public boolean isSpaced(Position p ) {
        for (Station st : stationList) {
            if(st.getPosition().distance(p) < distanceSpacing)
                return false;
        }
        return true;
    }

    
    
}
