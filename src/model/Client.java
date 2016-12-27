package model;

import javafxTest.Controller;

import static model.Game.addTransportedClient;

/**
 * Created by romainhry on 07/11/2016.
 */
public class Client {
    private Station station = null;
    private ShapeType destinationType = null;

    public Client(Station st, ShapeType type) {
        station = st;
        destinationType = type;
        st.addClient(this);
    }

    public ShapeType getType() {
        return destinationType;
    }

    public Station getStation() {
        return station;
    }

    public boolean tryBoarding(Train train) {
        if(train.isFull()) {
            System.err.println("Train full");
            return false;
        }
        /* boards if the the line contains the type of the client or if the client will get closer to his type */
        if (train.getLine().containsShape(destinationType)
            || train.nextStation().getMinDistance(destinationType) < station.getMinDistance(destinationType)) {
            station.removeClient(this);
            train.addClient(this);
            station = null;
            System.err.println("ENTERING TRAIN "+this);
            return true;
        }
        return false;
    	
    }

    public boolean willGetOff(Train train) {
        /* the client gets off the train if the current station is his type or if the line doesn't bring him closer to a station of his type */
        if (train.currentStation().getType() == destinationType
            || (!train.getLine().containsShape(destinationType)
            && train.nextStation().getMinDistance(destinationType) >= train.currentStation().getMinDistance(destinationType)) ) {
            train.removeClient(this);
            station = train.currentStation();
            Controller.gameView.removeClientFromTrain(train,this);
            if(station.getType() == this.getType()) {
                addTransportedClient();
                System.err.println("Transported client ");
            }
            else {
                station.addClient(this);
                Controller.gameView.put(this);
            }
            return true;
        }
        return false;
    }



    public String toString() {
        return "Client: "+destinationType;
    }
}
