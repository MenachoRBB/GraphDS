public class Edge <E extends Comparable<E>>{
    private E distance;
    private Edge<E> nextFil, nextCol;
    private Integer key1, key2;

    public Edge(E distance, int key1, int key2){
        this.distance = distance;
        this.nextFil = null;
        this.nextCol = null;
        this.key1 = key1;
        this.key2 = key2;
    }

    public E getDistance() {
        return distance;
    }


    public Integer getKey1(){
        return key1;
    }

    public Integer getKey2(){
        return key2;
    }

   public String toString(){
        return "Distance: "+distance+"\nnextFil: "+nextFil+"\nnextCol: "+nextCol+"\nkey1: "+key1+"\nkey2: "+key2;
    }

    public Edge<E> getNextFil() {
        return nextFil;
    }

    public Edge<E> getNextCol() {
        return nextCol;
    }

    public void setDistance(E distance) {
        this.distance = distance;
    }

    public void setNextFil(Edge<E> nextFil) {
        this.nextFil = nextFil;
    }

    public void setNextCol(Edge<E> nextCol) {
        this.nextCol = nextCol;
    }
}
