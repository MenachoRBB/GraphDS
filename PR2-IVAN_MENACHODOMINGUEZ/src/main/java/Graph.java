import java.util.*;

public class Graph <V extends Comparable<V>,E extends Comparable<E>>{
    private Hashtable<Integer, Node<V,E>> table = null;

    public Graph(){
        table = new Hashtable<>(400, 0.75f);
    }

    //Add edge to the graph
    public void addEdge(V node1, V node2, double distance) throws PersonalException{
        int id = hashFunct(node1.toString());
        Node<V,E> nodeOne = table.get(id);

        //colisions of first node
        while(nodeOne.getStation() != node1){
            id++;
            nodeOne = table.get(id);
        }

        int id2 = hashFunct(node2.toString());
        Node<V,E> nodeTwo = table.get(id2);

        //collisions of second node
        while(nodeTwo.getStation() != node2){
            id2++;
            nodeTwo = table.get(id2);
        }
        Edge<E> edge = new Edge(distance, id, id2);
        //arreglar
        nodeOne.addCol(edge);
        nodeTwo.addFil(edge);

        if(!existsEdge(node1, node2))
            throw new PersonalException("Edge does not exist");
    }

    //Chek that there's an edge between 2 nodes passed by parameter
    public boolean existsEdge(V v1, V v2)throws PersonalException{
        //Hashing
        int key1 = hashFunct(v1.toString());
        int key2 = hashFunct(v2.toString());

        //Comparing with null for throw exception
        if(v1.equals(null) || v2.equals(null)){
            throw new PersonalException("Nodes aren't correct");
        }

        //Getting nodes
        while(!table.get(key1).getStation().equals(v1))
            key1++;
        while(!table.get(key2).getStation().equals(v2))
            key2++;

        //Compare if they have the other
        if(table.get(key1).hasEdgeCol(key2) || table.get(key1).hasEdgeFil(key2))
            return true;
        else if(table.get(key2).hasEdgeFil(key1) || table.get(key2).hasEdgeCol(key1))
            return true;
        else
            return false;
    }

    //Check and return the value of an edge by passing by parameter 2 nodes
    public E edgeValue(V v1, V v2)throws PersonalException{
        if(!existsEdge(v1, v2))
            throw new PersonalException("Edge doesn't exist");
        Node<V,E> node1 = getNode(getKey(v1));
        Node<V,E> node2 = getNode(getKey(v2));

        Edge<E> rowNode1 = node1.getFirstFil();
        Edge<E> colNode1 = node2.getFirstCol();
        Edge<E> finalEdge = null;
        boolean find = false;

        if(rowNode1 != null){
            while (rowNode1 != null && !rowNode1.getKey2().equals(getKey(node2.getStation())))
                rowNode1 = rowNode1.getNextFil();
            if(rowNode1 != null && !rowNode1.getKey2().equals(getKey(node2.getStation()))){
                find = true;
                finalEdge = rowNode1;
            }
        }
        if(colNode1 != null && !find){
            while (colNode1 != null && !colNode1.getKey1().equals(getKey(node2.getStation())))
                colNode1 = colNode1.getNextCol();
            if(colNode1 != null && colNode1.getKey1().equals(getKey(node2.getStation())))
                finalEdge = colNode1;
        }

        return finalEdge.getDistance();
    }

    //Return's a list with the adjacent nodes to the node passed by parameter
    public GenericList<V> adjacentNodes(V v)throws PersonalException{
        Integer key = hashFunct(v.toString());

        while (!table.get(key).getStation().equals(v))
            key++;

        Node<V,E> rowNode = table.get(key);
        if(rowNode == null)
            throw new PersonalException("Null node");

        GenericList<Integer> rowList = new GenericList<>();
        rowList.create();
        rowList = rowNode.adjacentCol();
        GenericList<Integer> colList = new GenericList<>();
        colList.create();
        colList = rowNode.adjacentRow();

        GenericList<V> stationsList = new GenericList<>();
        stationsList.create();
        if(rowList != null){
            for(int i = 0; i < rowList.getnElem(); i++){
                Node<V,E> aux = table.get(rowList.getNode(i+1));
                stationsList.addNode(aux.getStation());
            }
        }

        if(colList != null){
            for(int i = 0; i < colList.getnElem(); i++){
                Node<V,E> aux2 = table.get(colList.getNode(i+1));
                stationsList.addNode(aux2.getStation());
            }
        }

        if(stationsList == null)
            throw new PersonalException("Empty list");

        return stationsList;
    }

    public void addNode(V v1){
        int id = hashFunct(v1.toString());
        Node<V,E> node = new Node<>(v1);
        while(table.containsKey(id))
            id++;
        table.putIfAbsent(id, node);
    }

    public Enumeration<Integer> getKeys(){
        return table.keys();
    }

    public int getSize(){
        return table.size();
    }

    public Node<V,E> getNode(int key){
        return table.get(key);
    }

    public int hashFunct(String aux){
        int toReturn = 0;
        for(int i = 0; i < aux.length(); i++) {
            toReturn = toReturn + aux.charAt(i)*i;
        }
        return toReturn;
    }

    public String toString(){
        return table.toString();
    }
    public Integer getKey(V node){
        Integer key = hashFunct(node.toString());
        while (!table.get(key).getStation().equals(node))
            key++;
        return key;
    }

}
