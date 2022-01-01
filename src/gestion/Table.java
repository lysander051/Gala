package gestion;

import java.io.Serializable;
import java.util.*;

public class Table implements Comparable<Table>, Serializable {
    private final int PLACE_PAR_TABLE = 8;
    private static int num;
    private int numTable;
    private int placeLibre=PLACE_PAR_TABLE;
    private List<Individu> participants = new ArrayList<>();


    /**
     * Constructeur d'une table
     * le numéro de table s'inscrémente automatiquement
     * la liste des participants est vide
     * le nombre de place par table est de 8 et le nombre de place libre est de 8
     */
    public Table(){
        this.numTable=++num;
    }


    /**
     * Donne le nombre de place libre
     * @return le nombre de place libre
     */
    public int getPlaceLibre() {
        return placeLibre;
    }


    /**
     * Donne le numéro de la table
     * @return le numéro de la table
     */
    public int getNumTable() {
        return numTable;
    }


    /**
     * Ajouter l'individu dans la liste des participants sur la table
     * @param pers individu qu'on ajoute dans la liste
     * @param placeOccupe nombre de place réservé par l'individu
     */
    public void ajoutPersonne(Individu pers,int placeOccupe){
        participants.add(pers);
        System.out.println("PARTICIPANTS   "  +participants);
        setPlaceLibre(placeOccupe);
    }


    /**
     * Modifie le nombre de place libre de la table
     * @param placeOccupe nombre de place réservé
     */
    private void setPlaceLibre(int placeOccupe) {
        this.placeLibre -= placeOccupe;
    }


    /**
     * Vérifie s'il y a assez de place libre sur la table par rapport au nombre de place demandé
     * @param place le nombre de place qu'on souhaite prendre
     * @return true si les places libres sont suffisants false sinon
     */
    public boolean verificationPlaceSuffisant(int place){
        return placeLibre>=place;
    }


    /**
     * Retire l'individu dans la liste des participants de la table et libère le nombre de place prise
     * @param id le numéro de l'individu
     * @param place le nombre de place réservé de l'individu
     */
    public void retirerParticipant(int id,int place){
        int nb=participants.size();
        for (int i = 0; i < nb; i++) {
            if (participants.get(i).getId() == id){
                participants.remove(participants.get(i));
                ajoutPlaceLibre(place);

            }
        }
    }


    /**
     * Rajoute le nombre de place qu'on libère dans le nombre de place libre
     * @param place nombre de place qu'on libère
     */
    private void ajoutPlaceLibre(int place){
        placeLibre+=place;
    }


    /**
     * Compare les tables par rapport à leur numéro
     * @param t la table avec lequel on compare
     * @return 0 si c'est la même table sinon un entier non nul
     */
    @Override
    public int compareTo(Table t) {
        return numTable-t.numTable;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return numTable == table.numTable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numTable);
    }

    @Override
    public String toString() {
        String s = "("+placeLibre+" places restants) [";
        int nbPersonne=participants.size();
        String acc="";
        String sep="";
        for (int i=0; i<nbPersonne; i++){
            if(participants.get(i).getNbReservation()>1){
                acc="+accompagnant";
            }
            else{
                acc="";
            }
            s+=sep+participants.get(i).toString()+acc;
            sep=",";
        }
        return s + "]";
    }










}
