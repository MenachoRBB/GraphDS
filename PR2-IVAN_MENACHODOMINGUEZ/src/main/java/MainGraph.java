import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class MainGraph {
    public static void main(String[] args) {
        Graph<Station, Double> graph;

        //Graph initialiaation
        try {
            graph = graphInit();

            System.out.println();
            //Has to return true cause there's an edge between aux1 and aux2
            System.out.println("Exists edge between aux1 and aux2?");
            Station aux1 = graph.getNode(8799).getStation();
            Station aux2 = graph.getNode(10962).getStation();
            System.out.println(graph.existsEdge(aux1, aux2));
            System.out.println();
            System.out.println("What's the distance between aux1 and aux2?");
            System.out.println("Distance: " + graph.edgeValue(aux1, aux2));
            System.out.println();

            //Has to return false
            System.out.println("Exists edge between aux3 and aux4?");
            Station aux3 = graph.getNode(8799).getStation();
            Station aux4 = graph.getNode(9147).getStation();
            System.out.println(graph.existsEdge(aux3, aux4));
            //Has to return exception
            //System.out.println(graph.edgeValue(aux3, aux4));

            //Has to return exception
            //Station aux5 = null;
            //Station aux6 = aux2;
            //System.out.print(graph.existsEdge(aux5, aux6));
            System.out.println();

            //Adjacent nodes
            GenericList<Station> list = new GenericList<>();
            Station aux7 = graph.getNode(8799).getStation();
            System.out.println("Name: " + aux7.getName());
            System.out.println("Adjacent nodes:");
            list = graph.adjacentNodes(aux7);
            for (int i = 0; i < list.getnElem(); i++) {
                System.out.println("Station: " + list.getNode(i + 1).getName());
            }


            //Zone max not guarantee
            GenericList<Integer> list2 = zoneMaxDist(graph, 10410, 5);
            System.out.println("\nName: " + graph.getNode(10410).getStation().getName() + " Num of no arrived: " + list2.getnElem());
            for (Integer k : list2)
                System.out.println(k + " " + graph.getNode(k).getStation().getName());


            GenericList<Integer> list3 = zoneMaxDist(graph, 10410, 20);
            System.out.println("\nName: " + graph.getNode(10410).getStation().getName() + " Num of no arrived: " + list3.getnElem());
            for (Integer k : list3)
                System.out.println(k + " " + graph.getNode(k).getStation().getName());


            GenericList<Integer> list4 = zoneMaxDist(graph, 10410, 50);
            System.out.println("\nName: " + graph.getNode(10410).getStation().getName() + " Num of no arrived: " + list4.getnElem());
            for (Integer k : list4)
                System.out.println(k + " " + graph.getNode(k).getStation().getName());


            GenericList<Integer> list5 = zoneMaxDist(graph, 10410, 1000);
            System.out.println("\nName: " + graph.getNode(10410).getStation().getName() + " Num of no arrived: " + list5.getnElem());
            for (Integer k : list5)
                System.out.println(k + " " + graph.getNode(k).getStation().getName());
            System.out.println();

            /*System.out.println("Show what nodes can visit");
            ArrayList<Integer> auxList = getCanVisit(graph, 10410, 9999999);
            System.out.println("Can visit "+auxList.size()+" nodes");
            for(Integer k : auxList){
                System.out.println(k + " " + graph.getNode(k).getStation().getName());
            }*/


            //Can arrive to the end point
            GenericList<Integer> shortestPath1 = shortestPath(graph,10410,9461, 20);
            System.out.println("Path steps: "+shortestPath1.getnElem());
            System.out.println("Path: ");
            for(Integer i : shortestPath1){
                System.out.println(i + " " + graph.getNode(i).getStation().getName());
            }
            System.out.println();

            //Can't arrive to the end point
            GenericList<Integer> shortestPath2 = shortestPath(graph,10410,9461, 1);
            System.out.println("Path steps: "+shortestPath2.getnElem());
            System.out.println("Path: ");
            for(Integer i : shortestPath2){
                System.out.println(i + " " + graph.getNode(i).getStation().getName());
            }

            //Doesn't work
            //extraPart(graph);
        }catch (PersonalException e){
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    //Function to calculate the distance between 2 zones
    public static double setDistance(double x1, double y1, double x2, double y2) {
        double dif1 = x1 - x2;
        double dif2 = y1 - y2;
        return (Math.sqrt(Math.pow(dif1, 2) + Math.pow(dif2, 2))) * 111.1;  //111.1 because a grade is 111.1 km
    }

    public static Graph<Station, Double> graphInit() throws IOException, ParseException {

        Graph<Station, Double> graph = new Graph<>();

        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("icaen.json");

        JSONArray stations = (JSONArray) jsonParser.parse(reader);

        RechargeZone[] stations2 = new RechargeZone[stations.size()];

        for (int i = 0; i < stations.size(); i++) {
            JSONObject info = (JSONObject) stations.get(i);

            String id_aux = (String) info.get("id");
            int id = Integer.parseInt(id_aux);

            String id_station_aux = (String) info.get("id_estacio");
            int id_station = Integer.parseInt(id_station_aux);

            String name = (String) info.get("nom");

            String date = (String) info.get("data");

            String cons_aux = (String) info.get("consum");
            float cons;
            if (cons_aux.isEmpty())
                cons = 0f;
            else
                cons = Float.parseFloat(cons_aux);


            String street = (String) info.get("carrer");

            String city = (String) info.get("ciutat");

            String state = (String) info.get("estat");

            String time_aux = (String) info.get("temps");
            int time;
            if (time_aux.isEmpty())
                time = 0;
            else
                time = Integer.parseInt(time_aux);


            String pow = (String) info.get("potencia");
            float power;
            if (pow.isEmpty())
                power = 0f;
            else
                power = Float.parseFloat(pow);

            String type = (String) info.get("tipus");

            String lat = (String) info.get("latitud");
            double latitude = Double.parseDouble(lat);

            String len = (String) info.get("longitud");
            double length = Double.parseDouble(len);

            RechargeZone rechargeZone = new RechargeZone(id, id_station, name, date, cons, street, city, state, time, power, type, latitude, length);
            stations2[i] = rechargeZone;
        }

        //Initilitation of the graph
        int pos = 0;
        Station[] st = new Station[400];
        Station station = new Station(stations2[0].getId_station(), stations2[0].getName(), stations2[0].getDate(),
                stations2[0].getStreet(), stations2[0].getCity(), stations2[0].getLatitude(), stations2[0].getLength());
        SameRechargeZone same = new SameRechargeZone(stations2[0].getId(), stations2[0].getCons(), stations2[0].getState(),
                stations2[0].getTime(), stations2[0].getPower(), stations2[0].getType());

        if (st[pos] == null) {
            st[pos] = station;
            st[pos].addRechargeZone(same);
        }

        for (int i = 1; i < stations2.length; i++) {
            station = new Station(stations2[i].getId_station(), stations2[i].getName(), stations2[i].getDate(),
                    stations2[i].getStreet(), stations2[i].getCity(), stations2[i].getLatitude(), stations2[i].getLength());
            same = new SameRechargeZone(stations2[i].getId(), stations2[i].getCons(), stations2[i].getState(),
                    stations2[i].getTime(), stations2[i].getPower(), stations2[i].getType());
            boolean added = false;
            for (int j = 0; j < i && st[j] != null; j++) {
                if (st[j].getLatitude() == station.getLatitude() && st[j].getLength() == station.getLength()) {
                    st[j].addRechargeZone(same);
                    added = true;
                }
            }

            if (added == false) {
                pos++;
                st[pos] = station;
                st[pos].addRechargeZone(same);
            }
        }
        boolean done = false;

        for (int i = 0; i < 400 && done == false; i++) {
            if (st[i] != null) {
                graph.addNode(st[i]);
            } else
                done = true;
        }

        Enumeration<Integer> keysEnum = graph.getKeys();
        Integer[] keys = new Integer[graph.getSize()];

        int pos2 = 0;

        while (keysEnum.hasMoreElements()) {
            keys[pos2] = keysEnum.nextElement();
            pos2++;
        }

        int key = 0;
        double minDistance;
        ArrayList<Integer> keys2 = new ArrayList<>();
        Station station1 = null;
        Station station2 = null;

        for (int i = 0; i < pos2; i++) {
            minDistance = 999999999.9;
            for (int j = i + 1; j < pos2; j++) {
                Node<Station, Double> node1 = graph.getNode(keys[i]);
                Node<Station, Double> node2 = graph.getNode(keys[j]);

                Station station3 = node1.getStation();
                Station station4 = node2.getStation();

                double distance = setDistance(station3.getLatitude(), station3.getLength(), station4.getLatitude(), station4.getLength());
                //Puting in edges
                if (distance < 40)
                    graph.addEdge(station3, station4, distance);
                else {
                    if (distance < minDistance) {
                        minDistance = distance;
                        //key = keys[i];
                        station1 = station3;
                        station2 = station4;
                    }
                }
            }

            if (graph.getNode(keys[i]).getFirstFil() == null && graph.getNode(keys[i]).getFirstCol() == null) {
                graph.addEdge(station1, station2, minDistance);
                //keys2.add(key);
            }

            if (i == pos2 - 1) {
                Node<Station, Double> node2 = graph.getNode(keys[i]);
                if (node2.getFirstCol() == null && node2.getFirstFil() == null) {
                    for (int j = 0; j < pos2; j++) {
                        Node<Station, Double> node1 = graph.getNode(keys[j]);
                        Station s1 = node1.getStation();
                        Station s2 = node2.getStation();

                        double distance = setDistance(node1.getStation().getLatitude(), node1.getStation().getLength(), node2.getStation().getLatitude(), node2.getStation().getLength());
                        if (distance < minDistance) {
                            minDistance = distance;
                            //key = keys[i];
                            station1 = s1;
                            station2 = s2;
                        }
                        if (j == pos2 - 1 && graph.getNode(keys[i]).getFirstFil() == null && graph.getNode(keys[i]).getFirstCol() == null) {
                            graph.addEdge(station1, station2, minDistance);
                            //keys2.add(key);
                        }
                    }
                }
            }
        }
        //Test adyacencias
            /*int i = 0;
            Node<Station, Double> ultimo = graph.getNode(7003);
            System.out.println(ultimo.getStation().getName() + " " +ultimo.getStation().getLatitude() +" "+ultimo.getStation().getLength()+" "+ultimo.getStation().getCity());
            Edge<Double> prim = ultimo.getFirstCol();

            while(prim != null){
                System.out.println(i+" "+prim.getDistance());
                prim = prim.getNextCol();
                i++;
            }
        prim = ultimo.getFirstFil();
        while(prim != null){
            System.out.println(i+" "+prim.getDistance());
            prim = prim.getNextFil();
            i++;
        }*/
        //System.out.println("test");
        return graph;
    }

    public static GenericList<Integer> zoneMaxDist(Graph<Station, Double> graph, Integer source, float autonomy) throws PersonalException {
        GenericList<Integer> order = new GenericList<>();
        order.create();
        ArrayList<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        GenericList<Integer> toReturn = new GenericList<>();
        toReturn.create();

        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            Integer st = queue.poll();
            order.addNode(st);

            GenericList<Station> adjacentNodes = graph.adjacentNodes(graph.getNode(st).getStation());
            if (adjacentNodes != null) {
                for (Station s : adjacentNodes) {
                    Integer dest = graph.getKey(s);
                    if (!visited.contains(dest) && autonomy >= setDistance(s.getLatitude(), s.getLength(), graph.getNode(st).getStation().getLatitude(), graph.getNode(st).getStation().getLength())/*graph.edgeValue(s, graph.getNode(st).getStation())*/) {

                        visited.add(dest);
                        queue.offer(dest);
                    }
                }
            }
            if (visited.isEmpty())
                throw new PersonalException("Empty list");
            else {
                //Take all the keys on the graph and put it in a list
                Enumeration<Integer> enumer = graph.getKeys();
                Integer[] keys = new Integer[graph.getSize()];
                int position = 0;

                //Fill the array with the keys
                while (enumer.hasMoreElements()) {
                    keys[position] = enumer.nextElement();
                    position++;
                }
                //Fill the final list that we are going to return
                GenericList<Integer> end = new GenericList<>();
                for (Integer x : keys) {
                    if (!visited.contains(x) && !x.equals(source)) {
                        end.addNode(x);

                    }
                }
                if (visited.size() == position) {
                    System.out.println("Autonomy is suficient to travel arround Catalunya");
                    end = new GenericList<>();
                }
                toReturn = end;

            }

        }
        return toReturn;
    }

    public static GenericList<Integer> shortestPath(Graph<Station, Double> graph, Integer source, Integer end, int autonomy) throws PersonalException {
        ArrayList<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        GenericList<Integer> toReturn = new GenericList<>();
        toReturn.create();
        ArrayList<Double> distances = new ArrayList<>();
        double finalDistance = 0;
        GenericList<Integer> path = new GenericList<>();
        path.create();
        double distance = 0;
        double shortestDistance = 0;

        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            Integer st = queue.poll();

            GenericList<Station> adjacentNodes = graph.adjacentNodes(graph.getNode(st).getStation());
            if (adjacentNodes != null) {
                for (Station s : adjacentNodes) {
                    Integer dest = graph.getKey(s);
                    if (!visited.contains(dest) && autonomy >= setDistance(s.getLatitude(), s.getLength(), graph.getNode(st).getStation().getLatitude(), graph.getNode(st).getStation().getLength())/*graph.edgeValue(s, graph.getNode(st).getStation())*/) {
                        visited.add(dest);
                        queue.offer(dest);
                    }
                }
            }
            if (visited.isEmpty())
                throw new PersonalException("Empty list");
        }

        if(!visited.contains(end)){
            throw new PersonalException("The source station can't arrive to the end point");
        }else {
            Integer aux = source;
            GenericList<Station> adjcents = graph.adjacentNodes(graph.getNode(source).getStation());
            //Prove that the adjacents generic list is not null
            if (adjcents != null) {
                int i = 1;
                //Prove that the end is adjacent, so we only have to do one step and return the step with the distance
                for (Station s : adjcents) {
                    if (s.equals(graph.getNode(end).getStation())) {
                        finalDistance = finalDistance + graph.edgeValue(graph.getNode(source).getStation(), graph.getNode(end).getStation());
                        path.addNode(end);
                        System.out.println("The distance from source to end is: "+finalDistance);
                        return path;
                    } else {
                        distance = graph.edgeValue(graph.getNode(aux).getStation(), s);
                        //Integer auxStation;
                        double auxDistance = 99999;
                        distances.add(distance);
                        for (Double d : distances) {
                            if (d < auxDistance) {
                                auxDistance = d;
                                shortestDistance = d;
                            }

                        }
                        finalDistance = finalDistance + shortestDistance;
                        if(distance == shortestDistance)
                            path.addNode(aux);
                        i++;
                        aux = graph.getKey(s);
                    }
                }
            }else
                throw new PersonalException("Can't arrive");
        }
        return path;
    }

    public static ArrayList<Integer> getCanVisit(Graph<Station, Double> graph, Integer source, float autonomy) throws PersonalException{
        ArrayList<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        GenericList<Integer> toReturn = new GenericList<>();
        toReturn.create();

        visited.add(source);
        queue.add(source);

        while (!queue.isEmpty()) {
            Integer st = queue.poll();

            GenericList<Station> adjacentNodes = graph.adjacentNodes(graph.getNode(st).getStation());
            if (adjacentNodes != null) {
                for (Station s : adjacentNodes) {
                    Integer dest = graph.getKey(s);
                    if (!visited.contains(dest) && autonomy >= setDistance(s.getLatitude(), s.getLength(), graph.getNode(st).getStation().getLatitude(), graph.getNode(st).getStation().getLength())/*graph.edgeValue(s, graph.getNode(st).getStation())*/) {

                        visited.add(dest);
                        queue.offer(dest);
                    }
                }
            }
            if (visited.isEmpty())
                throw new PersonalException("Empty list");
        }

        return visited;
    }

    public static void extraPart(Graph<Station, Double> graph){
        //TODO
        Enumeration<Integer> keys1 = graph.getKeys();
        Integer[] keys2 = new Integer[graph.getSize()];
        Integer sourceRoute1 = 0, sourceRoute2 = 0, sourceRoute3 = 0, sourceRoute4 = 0, sourceRoute5 = 0;
        Integer endRoute1 = 0, endRoute2 = 0, endRoute3 = 0, endRoute4 = 0, endRoute5 = 0;
        int pos = 0;
        //boolean find = false;

        while(keys1.hasMoreElements()){
            keys2[pos] = keys1.nextElement();
            pos++;
        }
        //System.out.println(graph.getNode(keys2[0]).getStation().getLatitude() == 41.824202);

        try {
        for(int i = 0; i < keys2.length; i++){

                //Route 1
                if (graph.getNode(keys2[i]).getStation().getLatitude() == 41.412473739646 && graph.getNode(keys2[i]).getStation().getLength() == 2.014127862566) {
                    sourceRoute1 = keys2[i];
                    //System.out.println("Found");
                }

                if (graph.getNode(keys2[i]).getStation().getLatitude() == 40.794775 && graph.getNode(keys2[i]).getStation().getLength() == 0.525542) {
                    endRoute1 = keys2[i];
                    //System.out.println("Found");
                }

                //Route 2
                if(graph.getNode(keys2[i]).getStation().getLatitude() == 41.412473739646 && graph.getNode(keys2[i]).getStation().getLength() == 2.014127862566){
                    sourceRoute2 = keys2[i];
                }

                if(graph.getNode(keys2[i]).getStation().getLatitude() == 41.5555823 && graph.getNode(keys2[i]).getStation().getLength() == 2.4005556){
                    endRoute2 = keys2[i];
                }

                //Route 3
                if(graph.getNode(keys2[i]).getStation().getLatitude() == 41.5555823 && graph.getNode(keys2[i]).getStation().getLength() == 2.4005556){
                    sourceRoute3 = keys2[i];
                }

                if(graph.getNode(keys2[i]).getStation().getLatitude() == 41.780674 && graph.getNode(keys2[i]).getStation().getLength() == 3.022077){
                    endRoute3 = keys2[i];
                }

                //Route 4
                if(graph.getNode(keys2[i]).getStation().getLatitude() == 41.5555823 && graph.getNode(keys2[i]).getStation().getLength() == 2.4005556){
                    sourceRoute4 = keys2[i];
                }

                if(graph.getNode(keys2[i]).getStation().getLatitude() == 41.375768 && graph.getNode(keys2[i]).getStation().getLength() == 1.163327){
                    endRoute4 = keys2[i];
                }

                //Route 5
                if(graph.getNode(keys2[i]).getStation().getLatitude() == 40.814151 && graph.getNode(keys2[i]).getStation().getLength() == 0.515161){
                    sourceRoute5 = keys2[i];
                }

                if(graph.getNode(keys2[i]).getStation().getLatitude() == 42.268984 && graph.getNode(keys2[i]).getStation().getLength() == 2.966869){
                    endRoute5 = keys2[i];
                }

            }

            /*GenericList<Integer> route1 = shortestPath(graph, sourceRoute1, endRoute1, 200);
            System.out.println("Steps of the route 1: "+route1.getnElem());*/

            /*GenericList<Integer> route2 = shortestPath(graph, sourceRoute2, endRoute2, 200);
            System.out.println("Steps of the route 2: "+route2.getnElem());*/

            /*GenericList<Integer> route3 = shortestPath(graph, sourceRoute3, endRoute3, 200);
            System.out.println("Steps of the route 3: "+route3.getnElem());*/

            /*GenericList<Integer> route4 = shortestPath(graph, sourceRoute4, endRoute4, 200);
            System.out.println("Steps of the route 4: "+route4.getnElem());*/

            /*GenericList<Integer> route5 = shortestPath(graph, sourceRoute5, endRoute5, 200);
            System.out.println("Steps of the route 5: "+route5.getnElem());*/

        }catch (Exception e){
            System.err.println(e);
        }
    }
}

