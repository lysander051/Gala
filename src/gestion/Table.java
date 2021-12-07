package gestion;

import java.util.HashSet;
import java.util.Set;

public class Table {
    private final int placeParTable = 8;

    private static int id;
    private int numTable;
    private int placePrise;

    Set<Individu> participants = new HashSet<>();

    public Table(){
        numTable=++id;
    }


}
