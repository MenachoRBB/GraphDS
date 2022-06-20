public class RechargeZone {
    private int id;
    private int id_station;
    private String name;
    private String date;
    private float cons;
    private String street;
    private String city;
    private String state;
    private int time;
    private float power;
    private String type;
    private double latitude;
    private double length;

    public RechargeZone(int id, int id_station, String name, String date, float cons, String street, String city, String state, int time, float power,
                        String type, double latitude, double length){
        this.id = id;
        this.id_station = id_station;
        this.name = name;
        this.date = date;
        this.cons = cons;
        this.street = street;
        this.city = city;
        this.state = state;
        this.time = time;
        this.power = power;
        this.type = type;
        this.latitude = latitude;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLength() {
        return length;
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

    public float getCons() {
        return cons;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getTime() {
        return time;
    }

    public float getPower() {
        return power;
    }

    public String getType() {
        return type;
    }
}
