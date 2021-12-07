package gestion;

import java.time.LocalDate;

public class Reservation {
    private LocalDate dateReservation;
    private int numTable;
    private int nbPlace;
    private double montant;

    public Reservation(LocalDate dateReservation, int numTable, int nbPlace, double montant) {
        this.dateReservation = dateReservation;
        this.numTable = numTable;
        this.nbPlace = nbPlace;
        this.montant = montant;
    }


}
