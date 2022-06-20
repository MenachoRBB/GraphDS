import java.util.Iterator;

public class iterableList <E extends Comparable<E>> implements Iterator {

    NodeList<E> pointer;

    public iterableList(NodeList<E> node){
        this.pointer = node;
    }

    @Override
    public boolean hasNext() {
        return pointer != null;
    }

    @Override
    public Object next() {
        NodeList<E> aux = pointer;
        this.pointer = pointer.getNext();
        return aux.getData();
    }

}
