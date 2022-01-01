package gestion;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private LocalDate dateReservation;
    private int numTable;
    private int nbPlace;
    private double montant;


    /** Constructeur d'une réservation
     * @param dateReservation la date où l'on a fait la réservation
     * @param numTable le numéro de la table choisi
     * @param nbPlace le nombre de place réservé
     * @param tarifPrinc le tarif correspondant à l'individu
     * @param tarifInv le tarif pour les accompagnants
     */
    public Reservation(LocalDate dateReservation, int numTable, int nbPlace,int tarifPrinc,int tarifInv) {
        this.dateReservation = dateReservation;
        this.numTable = numTable;
        this.nbPlace = nbPlace;
        this.montant=tarifPrinc+((nbPlace-1)*tarifInv);
    }


    /**
     * Donne le numéro de la table réservé
     * @return le numéro de la table réservé
     */
    public int getNumTable() {
        return numTable;
    }


    /** Donne le nombre de place réservé
     * @return le nombre de place réservé
     */
    public int getNbPlace() {
        return nbPlace;
    }


    /*    ON NE SAIT PAS SI ON L'UTILISE OU PAS/**
     * Donne la date de la réservation
     * @return la date de la réservation
     */
   /* public LocalDate getDateReservation(){ return dateReservation; }*/


    /**
     * Donne le montant de la réservation
     * @return le montant de la réservation
     */
    public double getMontant() {
        return montant;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "date=" + dateReservation +
                ", numTable=" + numTable +
                ", nbPlace=" + nbPlace +
                ", montant=" + montant +
                '}';
    }




}
