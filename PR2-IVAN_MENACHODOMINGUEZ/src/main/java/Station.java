import java.util.ArrayList;

public class Station implements Comparable<Station> {
    private int id_station;
    private String name;
    private String date;
    private String street;
    private String city;
    private double latitude;
    private double length;

    private ArrayList<SameRechargeZone> plugs = new ArrayList<>();

    public Station(int id_station, String name, String date, String street, String city, double latitude, double length) {
        this.id_station = id_station;
        this.name = name;
        this.date = date;
        this.street = street;
        this.city = city;
        this.latitude = latitude;
        this.length = length;
    }

    public void addRechargeZone(SameRechargeZone plug){
        plugs.add(plug);
    }

    public int getId_station() {
        return id_station;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLength() {
        return length;
    }

    public ArrayList<SameRechargeZone> getPlugs() {
        return plugs;
    }

    @Override
    public int compareTo(Station station) {
        return 0;
    }
}
