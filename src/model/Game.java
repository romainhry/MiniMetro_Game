package model;

import java.util.List;
import java.util.Timer;

/**
 * Created by romainhry on 08/11/2016.
 */
public class Game {
    private static int trainSpeed ;
    protected static int trainCapacity ;
    private static int stationCapacity ;
    private static int timeSpeed ;
    private static int transportedClientNb;
    private Timer day;
    private Inventory inventory;
    private List<Train> trainList;
    private List<Client> clientList;
    private List<Line> lineList;

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
    
    
    
}
