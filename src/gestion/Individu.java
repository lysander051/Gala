package gestion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public  abstract class Individu implements Comparable<Individu>, Serializable {
    private String nom;
    private String prenom;
    private String numTel;
    private String eMail;
    private int identifiant;
    private Reservation reservation;


    /**
     * Constructeur d'un individu
     * @param identifiant identifiant de l'individu
     * @param nom nom de l'individu
     * @param prenom pernom de l'individu
     * @param numTel numero telephone de l'individu
     * @param eMail eMail de l'individu
     */
    public Individu(int identifiant,String nom, String prenom, String numTel, String eMail) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.eMail = eMail;
        this.identifiant = identifiant;
    }


    /**
     * Donne l'identifiant de l'individu
     * @return le numéro d'identifiant
     */
    public int getId(){ return identifiant; }


    /**
     * Donne le nombre de place que l'individu a réservé
     * @return unt entier ,0 si l'individu n'a pas encore fait une reservation
     */
    public int getNbReservation(){
        if(reservation==null){
            return 0;
        }
        return reservation.getNbPlace();}


    /**
     * Donne le numéro de la table que l'individu a réservé
     * @return le numéro de la table qu'il a réservé
     */
    public int getNumTableReservation() {
        return this.reservation.getNumTable();
    }


    /**
     * Donne le nom de l'individu
     * @return le nom de l'individu
     */
    public String getNom() {
        return nom;
    }


    /**
     * Donne le montant de la réservation
     * @return la valeur du montant de la réservation
     */
    public double getMontantReservation(){
        return reservation.getMontant();
    }


    /*     ON NE SAIT PAS SI ON EN A BESOIN OU PAS/**
     * Donne la date où l'individu a effectué sa réservation
     * @return la date de la réservation
     */
    /*public LocalDate getDateReservation(){ return this.getDateReservation();}*/


    /**
     * Affecte à l'individu la réservation
     * @param reservation la reservation qu'il effectue
     */
    public void setReservation(Reservation reservation) {this.reservation = reservation;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individu individu = (Individu) o;
        return identifiant == individu.identifiant;
    }


    /**
     * Annule la réservation de l'individu
     */
    public void annulerReservationInd(){
        this.reservation=null;
    }


    /**
     * Donne un hachage sur l'identifiant
     * @return  un entier d'hachage sur l'identifiant
     */
    @Override
    public int hashCode() {return Objects.hash(identifiant);}


    /** Compare 2 individus selon leur identifiants
     * @param  o individu avec lequel on compare un autre
     * @return 0 si ils ont le même identifiants sinon un autre entier non nul s'ils sont 2 différents individus
     */
    @Override
    public int compareTo(Individu o) {return this.identifiant-o.identifiant;}


    @Override
    public String toString() {return  nom+" "+prenom+" "+reservation;}


    public abstract Type typeIndividu();

    /*
    NON UTILISE
    public abstract int getType();


    public int getIdentifiant() { return identifiant; }*/
}
