package gestion;

import java.util.Objects;

public  abstract class Individu implements Comparable<Individu>{
    private String nom;
    private String prenom;
    private String numTel;
    private String eMail;
    private int identifiant;
    private Reservation reservation;


    public Individu(String nom, String prenom, String numTel, String eMail, int identifiant, Reservation reservation) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.eMail = eMail;
        this.identifiant = identifiant;
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individu individu = (Individu) o;
        return identifiant == individu.identifiant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
    }

    @Override
    public int compareTo(Individu o) {

        return this.identifiant-o.identifiant;
    }

}
