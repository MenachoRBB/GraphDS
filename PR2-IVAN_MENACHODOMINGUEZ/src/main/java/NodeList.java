public class NodeList <T extends Comparable<T>> implements Comparable{
    private T data;
    private NodeList<T> before, next;

    //Void constructor
    public NodeList(){
        data = null;
        before = null;
        next = null;
    }

    //Constructor with data
    public NodeList(T data){
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodeList<T> getBefore() {
        return before;
    }

    public void setBefore(NodeList<T> before) {
        this.before = before;
    }

    public NodeList<T> getNext() {
        return next;
    }

    public void setNext(NodeList<T> next) {
        this.next = next;
    }

    public int compareTo(T data) {
        return this.data.compareTo(data);
    }


    //??
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
