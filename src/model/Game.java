package model;

import javafx.scene.paint.Color;
import javafxTest.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

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
    private Timer day;
    private Inventory inventory;
    private List<Train> trainList;
    private List<Client> clientList;
    private List<Line> lineList;
    private List <Station> stationList ;
    private List <Color> linesColor ;

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
    }
  
    public Client popRandomClient() {
    	return null;
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

    public  void computeAllDistances() {
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
    
    
    
}
