package gestion;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private LocalDate dateReservation;
    private int numTable;
    private int nbPlace;
    private double montant;

    public Reservation(LocalDate dateReservation, int numTable, int nbPlace,int tarifPrinc,int tarifInv) {
        this.dateReservation = dateReservation;
        this.numTable = numTable;
        this.nbPlace = nbPlace;
        this.montant=tarifPrinc+((nbPlace-1)*tarifInv);
    }

    public int getNumTable() {
        return numTable;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public LocalDate getDateReservation(){ return dateReservation; }

    @Override
    public String toString() {
        return "Reservation{" +
                "date=" + dateReservation +
                ", numTable=" + numTable +
                ", nbPlace=" + nbPlace +
                ", montant=" + montant +
                '}';
    }



    public double getMontant() {
        return montant;
    }
}
