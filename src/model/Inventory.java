package model;

public class Inventory {
    private  int lineNb ;
    private int tunnelNb ;
    private int wagonNb ;
    private int trainNb ;
    private int interchangeNb;

    public Inventory () {

    }

    public Inventory (int line,int tunnel, int wagon, int train,int inter) {
        lineNb = line;
        tunnelNb = tunnel;
        wagonNb = wagon;
        trainNb = train;
        interchangeNb = inter;
    }

    public int getTunnelNb()
    {
        return tunnelNb;
    }

    public void subTunnelNb(int t)
    {
        tunnelNb=tunnelNb-t;
    }

    public void addTunnelNb(int t)
    {
        tunnelNb=tunnelNb+t;
    }



    public int getLineNb()
    {
        return lineNb;
    }

    public void subLineNb()
    {
        --lineNb;
    }

    public void addLineNb()
    {
        ++lineNb;
    }


    public int getWagonNb()
    {
        return wagonNb;
    }

    public void subWagonNb(int t)
    {
        --wagonNb;
    }

    public void addWagonNb()
    {
        ++wagonNb;
    }



    public int getTrainNb()
    {
        return trainNb;
    }

    public void subTrainNb()
    {
        --trainNb;
    }

    public void addTrain()
    {
        ++trainNb;
    }


    public int getInterchangeNb() {
        return interchangeNb;
    }

    public void setInterchangeNb(int interchangeNb) {
        this.interchangeNb = interchangeNb;
    }
}
