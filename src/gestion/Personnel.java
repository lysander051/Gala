package gestion;

public class Personnel extends Individu{
    public Personnel(String nom, String prenom, String numTel, String eMail, int identifiant, Reservation reservation) {
        super(nom, prenom, numTel, eMail, identifiant, reservation);
    }
}
