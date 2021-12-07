package gestion;

import java.util.HashSet;
import java.util.Set;

public class Table implements Comparable<Table>{
    private final int PLACE_PAR_TABLE = 8;

    private static int id;
    private int numTable;
    private int placePrise;

    private Set<Individu> participants = new HashSet<>();


    public Table(){
        numTable=++id;
    }

    @Override
    public int compareTo(Table t) {
        return numTable-t.numTable;
    }
}
