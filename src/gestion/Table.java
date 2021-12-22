package gestion;

import java.util.*;

public class Table implements Comparable<Table>{
    private final int PLACE_PAR_TABLE = 8;

    private static int id;
    private int numTable;
    private int placePrise;

    private List<Individu> participants = new ArrayList<>();


    public Table(){
        numTable=++id;

        for (int i = 0; i < PLACE_PAR_TABLE; i++) {
            participants.add(null);
        }
    }

    public boolean retirerParticipant(int id){
        for (int i = 0; i < PLACE_PAR_TABLE; i++) {
            if (participants.get(i).getId() == id){
                participants.remove(participants.get(i));
            }
        }
        return false;
    }

    @Override
    public int compareTo(Table t) {
        return numTable-t.numTable;
    }

    @Override
    public String toString() {
        String s = "[1: ";
        if (participants.get(0).toString()!=null)
            s+=participants.get(0).toString();
        else
            s+="";

        for (int i = 1; i < PLACE_PAR_TABLE; i++){
            s+= ", " + i+1 + ": ";
            if (participants.get(i).toString()!=null)
                s+=participants.get(i).toString();
            else
                s+="";
        }
        return s + "]";
    }
}
