package model;

import java.util.ArrayList;

/**
 * Created by anthony on 12/20/16.
 */
public class Wagon {
    private java.util.List<Client> clientList;
    private boolean willSwap;
    private Train train;

    Wagon(Train t){
        train =t;
        clientList = new ArrayList<>();
        willSwap = false;
    }

    public boolean isFull() {
        return clientList.size()>=Game.vehicleCapacity;
    }

    public void addClient(Client client) {
        clientList.add(client);
    }
    public void removeClient(Client client) {
        clientList.remove(client);
    }
    public java.util.List<Client> getClientList(){return clientList;}
    public boolean getWillSwap(){return willSwap;}
    public void setWillSwap(boolean value){ willSwap=value;}

    public void changeTrain(Train t) {
        train.getWagonList().remove(this);
        train=t;
        train.getWagonList().add(this);
    }

}
