package model;

/**
 * Created by Anthony on 06/01/2017.
 */
public class Clock {
    //private enum weekDay {LUN, MAR, MER, JEU, VEN, SAM, DIM};
    private static final String[] weekDay= {"LUN", "MAR", "MER", "JEU", "VEN", "SAM", "DIM"};

    int currentDay;
    int time;

    public Clock(){
        time=0;
        currentDay=0;
    }
    public void incrementeTime(){
        time++;
        if(time>=24){
            time=0;
            incrementeDay();
        }
    }
    public void incrementeDay(){
        currentDay++;
        if(currentDay>=7)currentDay=0;
    }
    public String getDay(){
        return weekDay[currentDay];
    }
    public int getTime(){
        return time;
    }

}
