package model;

import java.util.ArrayList;

/**
 * Created by anthony on 12/20/16.
 */
public class Wagon {
    private java.util.List<Client> clientList;
    private boolean willSwap;

    Wagon(){
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
    public java.util.List<Client> getClientList{return clientList;}
    public boolean getWillSwap{return willSwap;}
    public boolean setWillSwap(boolean value){willSwap=value;}

    public void swapWagon(Train added) {
        willSwap=true;
        // ...
    }

}
