package gestion;

import java.io.Serializable;
import java.util.*;

public class Table implements Comparable<Table>, Serializable {
    private final int PLACE_PAR_TABLE = 8;
    private static int num;

    private int numTable;
    private int placeLibre=PLACE_PAR_TABLE;

    private List<Individu> participants = new ArrayList<>();


    public Table(){
        this.numTable=++num;
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
        String s = "("+placeLibre+" places restants) [";
        int nbPersonne=participants.size();
        for (int i=0; i<(nbPersonne-1); i++){
            s+=participants.get(i).toString();
        }
        if (0 < nbPersonne)
            s+=participants.get(nbPersonne).toString();
        return s + "]";
    }

    public int getNumTable() {
        return numTable;
    }

    public int getPlaceLibre() {
        return placeLibre;
    }

    public void ajoutPersonne(Individu pers,int placeOccupe){
        participants.add(pers);
        System.out.println("PARTICIPANTS   "  +participants);
        setPlaceLibre(placeOccupe);
    }

    private void setPlaceLibre(int placeOccupe) {
        this.placeLibre -= placeOccupe;
    }

    public boolean verificationPlaceSuffisant(int place){
        return placeLibre>=place;
    }
}
