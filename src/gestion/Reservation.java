package gestion;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {
    private LocalDate dateReservation;
    private int numTable;
    private int nbPlace;
    private double montant;

    public Reservation(LocalDate dateReservation, int numTable, int nbPlace) {
        this.dateReservation = dateReservation;
        this.numTable = numTable;
        this.nbPlace = nbPlace;
    }

    public int getNbPlace() {
        return nbPlace;
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
