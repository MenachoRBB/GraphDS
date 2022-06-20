import java.util.Iterator;

public class GenericList <T extends Comparable<T>> implements Iterable<T> {
    NodeList<T> first, last;
    private int nElem;

    public GenericList() {
        first = null;
        last = null;
        nElem = 0;
    }

    public void create(){
        first = null;
        last = null;
        nElem = 0;
    }

    public int getnElem() {
        return nElem;
    }

    public void addNode(T data) {
        NodeList<T> aux = new NodeList<>(data);
        if (first == null) {
            first = aux;
            last = aux;
        } else {
            last.setNext(aux);
            aux.setBefore(last);
            last = aux;
            last.setNext(null);
        }
        nElem++;
    }

    public T getNode(int position) {
        int pos = position - 1;
        if (pos < nElem) {
            NodeList<T> aux = first;
            int i = 0;
            while (aux != null) {
                if (i == pos)
                    return aux.getData();
                else {
                    i++;
                    aux = aux.getNext();
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new iterableList<>(first);
    }
}

