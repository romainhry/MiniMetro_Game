/**
 * Created by romainhry on 07/11/2016.
 */
public class Station {
    private String type = null;
    private Position pos = null;
    private java.util.List<Client> clientList;
    private java.util.List<Station> children;
    private Timer fullCapacity = null;
    private int capacity;
}
