public class Node <V,E extends Comparable<E>>{
    private V station; //Information of the zone
    private Edge<E> firstFil = null, firstCol = null;

    public Node(V info){
        this.station = info;
    }

    public V getStation(){
        return station;
    }

    public void setFirstFil(Edge firstFil){
        this.firstFil = firstFil;
    }

    public void setFirstCol(Edge firstCol){
        this.firstCol = firstCol;
    }

    public Edge<E> getFirstFil() {
        return firstFil;
    }

    public Edge<E> getFirstCol(){
        return firstCol;
    }

    public void addCol(Edge<E> elem){
        Edge<E> col = getFirstCol();
        if(col == null)
            setFirstCol(elem);
        else{
            while(col.getNextCol() != null)
                col = col.getNextCol();
            col.setNextCol(elem);
        }
    }

    public void addFil(Edge<E> elem){
        Edge<E> fil = getFirstFil();
        if(fil == null)
            setFirstFil(elem);
        else{
            while(fil.getNextFil() != null)
                fil = fil.getNextFil();
            fil.setNextFil(elem);
        }
    }

    public boolean hasEdgeFil(Integer key){
        Edge<E> aux = getFirstFil();
        if(aux == null)
            return false;
        else{
            while(aux != null){
                if(aux.getKey2().equals(key)){
                    return true;
                }else{
                    aux = aux.getNextFil();
                }
            }
        }
        return false;
    }

    public boolean hasEdgeCol(Integer key){
        Edge<E> aux = getFirstCol();
        if(aux == null)
            return false;
        else{
            while(aux != null){
                if(aux.getKey2().equals(key)){
                    return true;
                }else{
                    aux = aux.getNextCol();
                }
            }
        }
        return false;
    }

    public GenericList<Integer> adjacentRow(){
        Edge<E> rowNode = getFirstFil();
        GenericList<Integer> rowList = new GenericList<>();

        if(rowNode == null)
            return null;
        else{
            while(rowNode != null){
                rowList.addNode(rowNode.getKey2());
                rowNode = rowNode.getNextFil();
            }
        }
        return rowList;
    }

    public GenericList<Integer> adjacentCol(){
        Edge<E> colNode = getFirstCol();
        GenericList<Integer> colList = new GenericList<>();

        if(colNode == null)
            return null;
        else{
            while(colNode != null){
                colList.addNode(colNode.getKey2());
                colNode = colNode.getNextCol();
            }
        }
        return colList;
    }
}
