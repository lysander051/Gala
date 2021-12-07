package gestion;

import java.util.HashSet;
import java.util.Set;

public class Table implements Comparable{
    private final int placeParTable = 8;

    private static int id;
    private int numTable;
    private int placePrise;

    private Set<Individu> participants = new HashSet<>();


    public Table(){
        numTable=++id;
    }

    @Override
    public int compareTo(Object o) {
        Table t = (Table)o;
        return numTable-t.numTable;
    }
}
